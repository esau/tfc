package tfc.entities;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:07
 * This class represents the entity Media
 */
public class Media extends Entity {

    private String displayUrl;
    private String id;
    private String mediaUrl;
    private String mediaType;


    protected Media() {
        super(EntityType.MEDIA);
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
