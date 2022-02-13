package org.setana.treenity.util;

public class Calculate {

    private static final int TUTORIAL_UNIT = 100;

    public static int getMultiple(Integer cost) {
        return cost / TUTORIAL_UNIT > 0 ? cost / TUTORIAL_UNIT : 1;
    }

}
