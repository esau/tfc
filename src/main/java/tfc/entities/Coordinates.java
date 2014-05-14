package tfc.entities;


/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:08
 * This class represents the Coordinates entity
 */
public class Coordinates {
    private Place place;
    private Tweet tweet;
    private float latitude;
    private float longitude;
    private String type;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
