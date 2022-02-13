package org.setana.treenity.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.setana.treenity.model.Location;


public class GeometryUtil {

    private static final double EARTH_RADIUS = 6371.01; // Approx Earth radius in KM

// https://wooody92.github.io/project/JPA%EC%99%80-MySQL%EB%A1%9C-%EC%9C%84%EC%B9%98-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%8B%A4%EB%A3%A8%EA%B8%B0/
    public static Location makeLocation(double baseLatitude, double baseLongitude,
        double distance, double bearing) {
        double radianLatitude = toRadian(baseLatitude);
        double radianLongitude = toRadian(baseLongitude);
        double radianAngle = toRadian(bearing);
        double distanceRadius = distance / EARTH_RADIUS;

        double latitude = Math.asin(sin(radianLatitude) * cos(distanceRadius) +
            cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
        double longitude = radianLongitude + Math.atan2(sin(radianAngle) * sin(distanceRadius) *
            cos(radianLatitude), cos(distanceRadius) - sin(radianLatitude) * sin(latitude));

        longitude = normalizeLongitude(longitude);
        return new Location(toDegree(longitude), toDegree(latitude));
    }

    public static Location makeLocation(Location location, double distance, double bearing) {
        return makeLocation(location.getLatitude(),
            location.getLongitude(),
            distance,
            bearing);
    }

    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    private static Double sin(Double coordinate) {
        return Math.sin(coordinate);
    }

    private static Double cos(Double coordinate) {
        return Math.cos(coordinate);
    }

    private static Double normalizeLongitude(Double longitude) {
        return (longitude + 540) % 360 - 180;
    }

    // https://github.com/jasonwinn/haversine/blob/master/Haversine.java
    public static double toDistance(double startLat, double startLong, double endLat,
        double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    public static double toDistance(Location start, Location end) {
        return toDistance(start.getLatitude(),
            start.getLongitude(),
            end.getLatitude(),
            end.getLongitude());
    }

    public static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public static Point createPoint(double lon, double lat) {
        GeometryFactory gf = new GeometryFactory();
        return gf.createPoint(new Coordinate(lon, lat));
    }
}