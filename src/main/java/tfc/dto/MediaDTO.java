package tfc.dto;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:07
 */
public class MediaDTO extends EntityDTO{

    private String displayUrl;
    private String id;
    private String mediaUrl;
    private String mediaType;

    public MediaDTO() {
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
