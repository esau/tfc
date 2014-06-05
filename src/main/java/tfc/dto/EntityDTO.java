package tfc.dto;

/**
 * TFC
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:05
 */
public abstract class EntityDTO {

    private String comesFromTweetId;
    private int indexA;
    private int indexB;
    private EntityType type;

    protected EntityDTO(EntityType type) {
        this.type = type;
    }

    public int getIndexA() {
        return indexA;
    }

    public void setIndexA(int indexA) {
        this.indexA = indexA;
    }

    public int getIndexB() {
        return indexB;
    }

    public void setIndexB(int indexB) {
        this.indexB = indexB;
    }

    public EntityType getType() {
        return type;
    }

    public String getComesFromTweetId() {
        return comesFromTweetId;
    }

    public void setComesFromTweetId(String comesFromTweetId) {
        this.comesFromTweetId = comesFromTweetId;
    }
}
