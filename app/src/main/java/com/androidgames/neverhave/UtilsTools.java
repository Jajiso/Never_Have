package com.androidgames.neverhave;

import java.util.Random;

public class UtilsTools {

    public static int generateRamdomNumber(int range) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return random.nextInt(range);
    }
}
