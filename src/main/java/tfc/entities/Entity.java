package tfc.entities;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:05
 * This class represents the abstract entity Entity
 */
public abstract class Entity {
    private Tweet comesFromTweet;
    private int indexA;
    private int indexB;
    private EntityType type;

    protected Entity(EntityType type) {
        this.type = type;
    }

    public Tweet getComesFromTweet() {
        return comesFromTweet;
    }

    public void setComesFromTweet(Tweet comesFromTweet) {
        this.comesFromTweet = comesFromTweet;
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

}
