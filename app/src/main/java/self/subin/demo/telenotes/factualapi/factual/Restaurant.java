package self.subin.demo.telenotes.factualapi.factual;

/**
 * Object class to store the Restaurant details
 * <p/>
 * Created by Subin on 3/7/2016.
 */
public class Restaurant {

    private String name;
    private String address;
    private String type;
    private double rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
