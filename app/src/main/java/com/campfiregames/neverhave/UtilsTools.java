package com.campfiregames.neverhave;

import java.util.Random;

public class UtilsTools {

    /*Genera un numero pseudo-aleatorio (range es el rango del que dispone para elegir un numero)
    *Recuerda que generara un numero entre "0 y (RANGE - 1)"
    */
    public static int generateRamdomNumber(int range) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return random.nextInt(range);
    }
}
