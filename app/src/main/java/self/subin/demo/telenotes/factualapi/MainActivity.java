package self.subin.demo.telenotes.factualapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;
import com.google.common.collect.Lists;
import com.linearlistview.LinearListView;
import com.mypopsy.widget.FloatingSearchView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import self.subin.demo.telenotes.factualapi.custom.CustomFadingBar;
import self.subin.demo.telenotes.factualapi.custom.RestaurantListAdapter;
import self.subin.demo.telenotes.factualapi.factual.Restaurant;
import self.subin.demo.telenotes.factualapi.volley.GsonRequest;
import self.subin.demo.telenotes.factualapi.volley.VolleySingleton;
import self.subin.demo.telenotes.factualapi.volley.photoID.Photos;
import self.subin.demo.telenotes.factualapi.volley.placeID.Places;
import self.subin.demo.telenotes.factualapi.volley.sizes.IndivSize;
import self.subin.demo.telenotes.factualapi.volley.sizes.Sizes;

public class MainActivity extends AppCompatActivity implements CallBackList {

    private LinearListView lst_restaurant;
    private ImageView imgv_header;
    private ImageView imgv_dining_image;
    private FloatingSearchView floatingSearchView;
    private RestaurantListAdapter restaurantListAdapter;
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();
    private Bitmap bitmap;
    private TextView txtv_no_results;
    private CustomFadingBar customFadingBar;

    private Factual factual = new Factual("ZxbdjL3I0NfQ6aJKZjOlach06L2O54lHYkRGUrib", "vOCms6OgtCl8cAQdXGekJDvIFggMJOy9yMyUFmti");

    private String str = "";
    private int initial_offset = 2;
    private long fetch_count = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customFadingBar = new CustomFadingBar()
                .actionBarBackground(R.drawable.ab_background)
                .headerLayout(R.layout.header)
                .contentLayout(R.layout.activity_main)
                .headerOverlayLayout(R.layout.header_overlay);
        setContentView(customFadingBar.createView(this));
        customFadingBar.initActionBar(this);

        _init();
        _wiredEvents();
        _customizeView();
    }

    private void _init() {
        imgv_header = (ImageView) findViewById(R.id.image_header);
        imgv_dining_image = (ImageView) findViewById(R.id.imgv_dining_image);
        txtv_no_results = (TextView) findViewById(R.id.txtv_no_results);
        floatingSearchView = (FloatingSearchView) findViewById(R.id.search);

        lst_restaurant = (LinearListView) findViewById(R.id.vertical_list);

        restaurantListAdapter = new RestaurantListAdapter(this, restaurants);
    }

    private void _wiredEvents() {
        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSearchAction(CharSequence charSequence) {

                if (isNetworkConnected()) {

                    if (!floatingSearchView.getText().toString().trim().equals("")) {
                        if (!str.trim().equals(floatingSearchView.getText().toString().trim())) {
                            initial_offset = 0;
                            str = floatingSearchView.getText().toString();
                            str = str.replaceAll("^\\s+|\\s+$", "");
                            getPlaceID(str);
                            restaurants = new ArrayList<Restaurant>();
                        }
                        Query query = new Query().offset(initial_offset).limit(fetch_count)
                                .field("locality").equal(str);
                        FactualRetrievalTask task = new FactualRetrievalTask();
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
                    } else {
                        Toast.makeText(MainActivity.this, "Please type in some city name", Toast.LENGTH_SHORT).show();
                    }

                    floatingSearchView.clearFocus();
                }else{
                    createNetErrorDialog();
                }
            }
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void createNetErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(i);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void _customizeView() {
        lst_restaurant.setAdapter(restaurantListAdapter);

        lst_restaurant.setDividerDrawable(new ColorDrawable(Color.CYAN));
        lst_restaurant.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE
                | LinearLayout.SHOW_DIVIDER_BEGINNING);
    }

    private void getPlaceID(String placeName) {

        placeName = placeName.replaceAll(" ", "+");
        String placeIDAPI = "https://api.flickr.com/services/rest/?method=flickr.places.find&api_key=d6bbf9a446e8388619a8c0d6a130bfa9&query=" + placeName + "&format=json&nojsoncallback=1";
        GsonRequest placesGsonRequest = new GsonRequest<Places>(placeIDAPI, Places.class, new Response.Listener<Places>() {
            @Override
            public void onResponse(Places response) {
                if(response.getPlaces()!=null && response.getPlaces().getPlace()!=null && response.getPlaces().getPlace().size() > 0) {
                    String result = response.getPlaces().getPlace().get(0).getPlace_id();
                    getPhotoID(result);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(placesGsonRequest);
    }

    private void getPhotoID(String placeID) {
        String photoIDAPI = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=d6bbf9a446e8388619a8c0d6a130bfa9&tags=skyline%2Cnight&tag_mode=all&place_id=" + placeID + "&format=json&nojsoncallback=1";
        GsonRequest placesGsonRequest = new GsonRequest<Photos>(photoIDAPI, Photos.class, new Response.Listener<Photos>() {
            @Override
            public void onResponse(Photos response) {
                if(response.getPhotos()!=null) {
                    if(response.getPhotos()!=null && response.getPhotos().getPhoto()!=null && response.getPhotos().getPhoto().size()>0) {
                        String result = response.getPhotos().getPhoto().get(0).getId();
                        getPhotoURL(result);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(placesGsonRequest);
    }

    private void getPhotoURL(String photoID) {
        String photoURLAPI = "https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=d6bbf9a446e8388619a8c0d6a130bfa9&photo_id=" + photoID + "&format=json&nojsoncallback=1";
        GsonRequest photoSizeRequest = new GsonRequest<Sizes>(photoURLAPI, Sizes.class, new Response.Listener<Sizes>() {
            @Override
            public void onResponse(Sizes response) {
                List<IndivSize> indivSizes = response.getSizes().getSize();
                for (IndivSize indivSize : indivSizes) {
                    if (indivSize.getLabel().equals("Large")) {
                        setBackgroundImage(indivSize.getSource());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(photoSizeRequest);
    }

    private void setBackgroundImage(String imageURL) {
        new LoadImage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageURL);
    }

    @Override
    public void receivecallback(String message) {
        if(isNetworkConnected()) {
            if (initial_offset < 500) {
                Query query = new Query().offset(initial_offset).limit(fetch_count)
                        .field("locality").equal(str);
                initial_offset += 50;
                FactualRetrievalTask task = new FactualRetrievalTask();
                task.execute(query);
            } else {
                Toast.makeText(MainActivity.this, "Burst Limit exceeded", Toast.LENGTH_SHORT).show();
            }
        }else{
            createNetErrorDialog();
        }
    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if (image != null) {
                Drawable d = new BitmapDrawable(getResources(), image);
                imgv_header.setImageDrawable(d);
            } else {

                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class FactualRetrievalTask extends AsyncTask<Query, Integer, List<ReadResponse>> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Getting Data");
            dialog.show();
        }

        @Override
        protected List<ReadResponse> doInBackground(Query... params) {
            List<ReadResponse> results = Lists.newArrayList();
            for (Query q : params) {
                results.add(factual.fetch("restaurants-us", q));
            }
            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(List<ReadResponse> responses) {
            dialog.dismiss();
            for (ReadResponse response : responses) {
                for (Map<String, Object> restaurant : response.getData()) {
                    Restaurant currentRestaurant = new Restaurant();
                    currentRestaurant.setName((String) restaurant.get("name"));
                    currentRestaurant.setAddress((String) restaurant.get("address"));
                    currentRestaurant.setType("Some cuisine");
                    if (restaurant.get("rating") != null) {
                        if (restaurant.get("rating") instanceof Double) {
                            currentRestaurant.setRating((Double) restaurant.get("rating"));
                        } else {
                            currentRestaurant.setRating(((Integer) restaurant.get("rating")).doubleValue());
                        }
                    }
                    String str = "";
                    if (restaurant.get("cuisine") != null) {
                        if (restaurant.get("cuisine") instanceof JSONArray) {
                            JSONArray jsonArray = (JSONArray) restaurant.get("cuisine");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    String s = (String) jsonArray.get(i);
                                    str += (" " + s + ",");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            str = str.substring(0, str.length() - 1);
                            currentRestaurant.setType(str);
                        }
                    }
                    restaurants.add(currentRestaurant);
                }
                if(restaurants.size()>0) {
                    lst_restaurant.setVisibility(View.VISIBLE);
                    txtv_no_results.setVisibility(View.GONE);
                    imgv_dining_image.setVisibility(View.GONE);
                    restaurantListAdapter.updateRestaurants(restaurants);
                }else{
                    lst_restaurant.setVisibility(View.GONE);
                    txtv_no_results.setVisibility(View.VISIBLE);
                    imgv_dining_image.setVisibility(View.GONE);
                    txtv_no_results.setText("No Results found for '"+str+"'");
                }
            }
        }

    }
}
