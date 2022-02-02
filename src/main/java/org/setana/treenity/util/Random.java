package org.setana.treenity.util;

public class Random {

    public static double randomLong() {
        final double START = -90;
        final double END = +90;

        double random = new java.util.Random().nextDouble();
        return START + (random * (END - START));
    }

    public static double randomLat() {
        final double START = -180;
        final double END = +180;

        double random = new java.util.Random().nextDouble();
        return START + (random * (END - START));
    }

}
