package com.sheehabe.tourmate.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String App_ID="c8ec88bd32e4febab50b194549e0d2e6";
    public static Location CurrentLocation=null;

    public static String convertUnixTimeToDate(int dt){
        Date date = new Date(dt * 1000L);
        SimpleDateFormat dateSDF = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted =dateSDF.format( date );

        return formatted;
    }
    public static String convertUnixTimeToHour(int dt){
        Date date = new Date(dt * 1000L);
        SimpleDateFormat dateSDF = new SimpleDateFormat("HH:mm ");
        String formatted =dateSDF.format( date );

        return formatted;
    }
}
