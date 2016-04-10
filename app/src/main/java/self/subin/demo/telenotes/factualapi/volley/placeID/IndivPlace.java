package self.subin.demo.telenotes.factualapi.volley.placeID;

/**
 * Created by Subin on 3/10/2016.
 */
public class IndivPlace {

    private String place_id;
    private String woeid;
    private String latitude;
    private String longitude;
    private String place_url;
    private String place_type;
    private String place_type_id;
    private String timezone;
    private String _content;
    private String woe_name;

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlace_url() {
        return place_url;
    }

    public void setPlace_url(String place_url) {
        this.place_url = place_url;
    }

    public String getPlace_type() {
        return place_type;
    }

    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    public String getPlace_type_id() {
        return place_type_id;
    }

    public void setPlace_type_id(String place_type_id) {
        this.place_type_id = place_type_id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public String getWoe_name() {
        return woe_name;
    }

    public void setWoe_name(String woe_name) {
        this.woe_name = woe_name;
    }
}
