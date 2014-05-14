package tfc.entities;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:05
 * This class represents the entity UserMention
 */
public class UserMention extends Entity {
    private User mentionsUser;
    private String screenName;


    protected UserMention() {
        super(EntityType.USER_MENTION);
    }

    public User getMentionsUser() {
        return mentionsUser;
    }

    public void setMentionsUser(User mentionsUser) {
        this.mentionsUser = mentionsUser;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
