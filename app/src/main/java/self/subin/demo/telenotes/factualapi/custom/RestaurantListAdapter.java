package self.subin.demo.telenotes.factualapi.custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.List;

import self.subin.demo.telenotes.factualapi.CallBackList;
import self.subin.demo.telenotes.factualapi.R;
import self.subin.demo.telenotes.factualapi.factual.Restaurant;

/**
 * Adapter to populate the list to display the restaurants
 * <p/>
 * Created by Subin on 3/12/2016.
 */
public class RestaurantListAdapter extends BaseAdapter {

    private List<Restaurant> restaurants;
    private Activity context;

    /**
     * Constructor for the class to initialize it
     *
     * @param context     Activity context of the calling activity
     * @param restaurants List of Restaurant to be displayed
     */
    public RestaurantListAdapter(Activity context, List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        this.context = context;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (rowView == null) {

            rowView = inflater.inflate(R.layout.list_item_layout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtv_rest_name = (TextView) rowView.findViewById(R.id.txtv_rest_name);
            viewHolder.txtv_rest_add = (TextView) rowView.findViewById(R.id.txtv_rest_add);
            viewHolder.txtv_rest_type = (TextView) rowView.findViewById(R.id.txtv_rest_type);
            viewHolder.ratingBar = (RatingBar) rowView.findViewById(R.id.rating);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Restaurant currentRestaurant = restaurants.get(position);
        holder.txtv_rest_name.setText(currentRestaurant.getName());
        holder.txtv_rest_add.setText(currentRestaurant.getAddress());
        holder.txtv_rest_type.setText(currentRestaurant.getType());
        holder.ratingBar.setRating((float) currentRestaurant.getRating());
        if (position == getCount() - 1) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            Button button = (Button) inflater.inflate(R.layout.load_more_button, parent, false);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CallBackList) context).receivecallback("More Pressed");
                }
            });
            linearLayout.addView(rowView);
            linearLayout.addView(button);
            return linearLayout;
        } else {
            return rowView;
        }


    }

    /**
     * Method to update the list with new data
     *
     * @param restaurants
     */
    public void updateRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        //Triggers the list update
        notifyDataSetChanged();
    }

    /**
     * Viewholder to avoid having to inflate the list items on every object
     */
    static class ViewHolder {
        public TextView txtv_rest_name;
        public TextView txtv_rest_add;
        public TextView txtv_rest_type;
        public RatingBar ratingBar;
    }
}
