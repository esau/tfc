package tfc.dto;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:08
 */
public class CoordinatesDTO {
    private double latitude;
    private double longitude;
    private String tweetId;
    private String placeId;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

}
