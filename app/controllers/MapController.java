package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collection;

public class MapController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(MapController.class);

    public Result locations() {
        LOGGER.debug("Get my locations");
        final Collection<Place> locations = myLocations();
        final JsonNode json = Json.toJson(locations);
        return ok(json);
    }

    private Collection<Place> myLocations() {
        return ImmutableList.of(
                new Place("GNITS", new Location(17.411817, 78.398951)),
                new Place("Waverock", new Location(17.418153, 78.347624)),
                new Place("Home", new Location(17.428127, 78.329992))
        );
    }

    static class Place {

        private String name;
        private Location location;

        public Place(String name, Location location) {
            this.name = name;
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    static class Location {
        private Double latitude;
        private Double longitude;

        public Location(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }

}
