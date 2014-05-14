package tfc.entities;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:05
 * This class represents the entity Url
 */
public class Url extends Entity {
    
    private User comesFromUser;
    private String displayUrl;
    private String mediaUrl;

    protected Url() {
        super(EntityType.URL);
    }

    public User getComesFromUser() {
        return comesFromUser;
    }

    public void setComesFromUser(User comesFromUser) {
        this.comesFromUser = comesFromUser;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
