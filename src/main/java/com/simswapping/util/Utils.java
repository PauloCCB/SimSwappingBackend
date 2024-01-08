package com.simswapping.util;

import org.geotools.referencing.GeodeticCalculator;

import javax.xml.crypto.dsig.TransformException;

public class Utils {

    // Constante radial de la Tierra en metros
    private static final double EARTH_RADIUS = 6371000;

    private static final double RADIOUS_METER = 200;

    public static double calcDistance(double latitudReferencia, double longitudReferencia, double latitudPunto, double longitudPunto) throws Exception {
        /*double dLat = Math.toRadians(latitud2 - latitud1);
        double dLon = Math.toRadians(longitud2 - longitud1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitud1)) * Math.cos(Math.toRadians(latitud2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;*/
        GeodeticCalculator calculator = new GeodeticCalculator();
        calculator.setStartingGeographicPoint(longitudReferencia, latitudReferencia);
        calculator.setDestinationGeographicPoint(longitudPunto, latitudPunto);

        //return calculator.getOrthodromicDistance();
        return calculator.getOrthodromicDistance();
    }

    /**
     *
     * @param latitudReferencia
     * @param longitudReferencia
     * @param latitudPunto
     * @param longitudPunto
     * @return "Retorna true cuando esta dentro del radio y false cuando no lo está
     */
    public static boolean isOnRadio(double latitudReferencia,
                                    double longitudReferencia,
                                    double latitudPunto,
                                    double longitudPunto) throws Exception {
            /*double distancia = calcDistance(latitudReferencia, longitudReferencia, latitudPunto, longitudPunto);
            return (distancia <= (RADIOUS_METER + MARGEN_ERROR));*/
        double distancia = calcDistance(latitudReferencia, longitudReferencia, latitudPunto, longitudPunto);
        return distancia <= RADIOUS_METER;

    }
}
