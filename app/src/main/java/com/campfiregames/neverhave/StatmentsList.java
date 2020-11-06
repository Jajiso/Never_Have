package com.campfiregames.neverhave;

import java.util.ArrayList;
import java.util.Arrays;

public class StatmentsList {

    private ArrayList<String> statments;
    private ArrayList<String> statmentsClone;

    public StatmentsList(String[] statments, String[] statmentsWithIP){
        this.statments = new ArrayList<>();
        this.statments.addAll(Arrays.asList(statments));
        this.statments.addAll(Arrays.asList(statmentsWithIP));

        this.statmentsClone = new ArrayList<>();
    }

    public String getStatment() {
        if (statmentsClone.size() == 0) {
            //Primero Pasamos el Garbage collector para limpiar la memoria RAM
            Runtime.getRuntime().gc(); //Tambien se puede llamar de laa siguiente forma System.gc();
            this.statmentsClone = (ArrayList<String>) this.statments.clone();
        }
        int randomString = UtilsTools.generateRamdomNumber(statmentsClone.size());
        String result = statmentsClone.get(randomString);
        statmentsClone.remove(randomString);
        return result;
    }
}
