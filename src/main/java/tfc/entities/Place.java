package tfc.entities;

import java.util.List;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:07
 * This class represents the entity Place
 */
public class Place {
    
    private String attributes;
    private List<Coordinates> boundingBox;
    private String countryCode;
    private String countryName;
    private String fullName;
    private String id;
    private List<Tweet> tweetsRelated;
    private String type;
    private String url;

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public List<Coordinates> getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(List<Coordinates> boundingBox) {
        this.boundingBox = boundingBox;
    }

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

    public List<Tweet> getTweetsRelated() {
        return tweetsRelated;
    }

    public void setTweetsRelated(List<Tweet> tweetsRelated) {
        this.tweetsRelated = tweetsRelated;
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
}
