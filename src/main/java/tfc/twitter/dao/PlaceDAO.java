package tfc.twitter.dao;

import org.springframework.stereotype.Repository;
import twitter4j.*;
import twitter4j.api.PlacesGeoResources;

/**
 * TDF
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

    public Place findPlaceById(String pPlaceId) throws TwitterException {
        return placesGeoResources.getGeoDetails(pPlaceId);
    }

    //todo remove after tests
    public static void main (String[] args){
        PlaceDAO placeDAO = new PlaceDAO();
        Place place = null;
        try {
            place = placeDAO.findPlaceById("2bb0b13fcaa74150");
            System.out.println("place: " + place);
            GeoLocation[][] boundingBox =place.getBoundingBoxCoordinates();
            for (GeoLocation[] geoLocations : boundingBox) {
                for (GeoLocation geoLocation : geoLocations) {
                    System.out.println("BoundingGeo: " + geoLocation);

                }
            }
            GeoLocation[][] geometryCoordinates = place.getGeometryCoordinates();
            for (GeoLocation[] geometryCoordinate : geometryCoordinates) {
                for (GeoLocation geoLocation : geometryCoordinate) {
                    System.out.println("GeometryCoord: " + geoLocation);
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
