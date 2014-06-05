package tfc.twitter.impl;

import twitter4j.*;

import java.util.Date;

/**
 * TFC
 * User: Esaú González
 * Date: 30/05/14
 * Time: 20:25
 */
public class PoisonedPillStatus implements Status {
    @Override
    public Date getCreatedAt() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getText() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getSource() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isTruncated() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getInReplyToStatusId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getInReplyToUserId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getInReplyToScreenName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GeoLocation getGeoLocation() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Place getPlace() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isFavorited() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isRetweeted() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getFavoriteCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User getUser() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isRetweet() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Status getRetweetedStatus() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long[] getContributors() {
        return new long[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getRetweetCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isRetweetedByMe() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getCurrentUserRetweetId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isPossiblySensitive() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLang() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Scopes getScopes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int compareTo(Status o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UserMentionEntity[] getUserMentionEntities() {
        return new UserMentionEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public URLEntity[] getURLEntities() {
        return new URLEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HashtagEntity[] getHashtagEntities() {
        return new HashtagEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MediaEntity[] getMediaEntities() {
        return new MediaEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SymbolEntity[] getSymbolEntities() {
        return new SymbolEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getAccessLevel() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
