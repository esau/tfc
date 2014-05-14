package tfc.dto;

import tfc.entities.EntityType;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:05
 */
public class HashtagDTO extends EntityDTO{

    private String text;

    public HashtagDTO() {
        super(EntityType.HASHTAG);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
