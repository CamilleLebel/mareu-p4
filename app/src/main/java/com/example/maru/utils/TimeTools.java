package com.example.maru.utils;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public abstract class TimeTools {

    // FIELDS --------------------------------------------------------------------------------------

    private static final String SEPARATOR = ":";
    public static final String PATTERN_FORMAT = "hh" + TimeTools.SEPARATOR + "mm";

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Returns a {@link String} that contains the time in specific format
     * @param hour an integer that contains the hour [0 ; 24]
     * @param minute an integer that contains the minute [0 ; 60]
     * @return a {@link String} that contains the time in hh[SEPARATOR]mm
     * @exception Exception throws when hour or minute is no correct
     */
    public static String convertHourAndMinuteToString(int hour, int minute) throws Exception {
        String convertedString = ((hour < 10) ? "0" : "")   + hour +
                TimeTools.SEPARATOR        +
                ((minute < 10) ? "0" : "") + minute;

        // EXCEPTION
        if (hour < 0 || hour > 24 || minute < 0 || minute > 60) {
            throw new Exception("Error: Out of boundary for at least one of arguments");
        }


        return  ((hour < 10) ? "0" : "")   + hour +
                TimeTools.SEPARATOR        +
                ((minute < 10) ? "0" : "") + minute;
    }

    public static String convertSecondToString(int time) {
        int hour = time / 3600;
        int minute = (time % 3600) / 60;
        String convertedString = ((hour < 10) ? "0" : "") + hour +
                TimeTools.SEPARATOR +
                ((minute < 10) ? "0" : "") + minute;

        return ((hour < 10) ? "0" : "") + hour +
                TimeTools.SEPARATOR     +
                ((minute < 10) ? "0" : "") + minute;
    }
}
