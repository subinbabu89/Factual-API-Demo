package self.subin.demo.telenotes.factualapi.volley.placeID;

import java.util.List;

/**
 * Created by Subin on 3/10/2016.
 */
public class Place {
    private List<IndivPlace> place;
    private String query;
    private int total;

    public List<IndivPlace> getPlace() {
        return place;
    }

    public void setPlace(List<IndivPlace> place) {
        this.place = place;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
