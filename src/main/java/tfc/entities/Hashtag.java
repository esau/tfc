package tfc.entities;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:05
 * This class represents the entity Hashtag
 */
public class Hashtag extends Entity {
    
    private String text;


    protected Hashtag() {
        super(EntityType.HASHTAG);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
