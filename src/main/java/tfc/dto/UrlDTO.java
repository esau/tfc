package tfc.dto;

/**
 * TFC
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:05
 */
public class UrlDTO extends EntityDTO{

    private String comesFromUserId;
    private String displayUrl;
    private String mediaUrl;

    public UrlDTO() {
        super(EntityType.URL);
    }

    public String getComesFromUserId() {
        return comesFromUserId;
    }

    public void setComesFromUserId(String comesFromUserId) {
        this.comesFromUserId = comesFromUserId;
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

    @Override
    public String toString() {
        if (getDisplayUrl().length()>21) {
            return getDisplayUrl().substring(11, 21);
        } else {
            return getDisplayUrl();
        }
    }
}
