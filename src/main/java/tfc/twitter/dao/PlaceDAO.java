package tfc.twitter.dao;

import org.springframework.stereotype.Repository;
import tfc.twitter.TwitterManager;
import twitter4j.Place;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.PlacesGeoResources;

/**
 * TFC
 * User: Esaú González
 * Date: 27/04/14
 * Time: 17:55
 */
@Repository("TPlaceDAO")
public class PlaceDAO {

    Twitter twitter;
    PlacesGeoResources placesGeoResources;

    public PlaceDAO() {
        twitter = TwitterFactory.getSingleton();
        placesGeoResources = twitter.placesGeo();
    }

    public Place findPlaceById(String pPlaceId) throws InterruptedException {
        Place toReturn = null;
        do {
            try {
                toReturn = placesGeoResources.getGeoDetails(pPlaceId);
            } catch (TwitterException e) {
                TwitterManager.handleTwitterException(e);
            }
        } while (toReturn==null);
        return toReturn;
    }
   
}
