package tfc.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * TFC
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:07
 */
public class PlaceDTO {
    private String attributes;
    private List<CoordinatesDTO> boundingBox = new ArrayList<CoordinatesDTO>();
    private String countryCode;
    private String countryName;
    private String fullName;
    private String id;
    private String tweetRelated;
    private String type;
    private String url;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTweetRelated() {
        return tweetRelated;
    }

    public void setTweetRelated(String tweetRelated) {
        this.tweetRelated = tweetRelated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public void addCoordinatesDTO(CoordinatesDTO pCoordinatesDTO){
        boundingBox.add(pCoordinatesDTO);
    }

    public List<CoordinatesDTO> getBoundingBox() {
        return boundingBox;
    }
}
