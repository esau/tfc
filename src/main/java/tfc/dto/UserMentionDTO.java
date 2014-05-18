package tfc.dto;

/**
 * TDF
 * User: Esaú González
 * Date: 27/04/14
 * Time: 18:05
 */
public class UserMentionDTO extends EntityDTO{

    private String mentionsUserId;  //todo cambiar a UserDTO
    private String mentionsScreenName;

    public UserMentionDTO() {
        super(EntityType.USER_MENTION);
    }

    public String getMentionsUserId() {
        return mentionsUserId;
    }

    public void setMentionsUserId(String mentionsUserId) {
        this.mentionsUserId = mentionsUserId;
    }

    public String getMentionsScreenName() {
        return mentionsScreenName;
    }

    public void setMentionsScreenName(String mentionsScreenName) {
        this.mentionsScreenName = mentionsScreenName;
    }

    @Override
    public String toString() {
        return getMentionsScreenName();
    }
}
