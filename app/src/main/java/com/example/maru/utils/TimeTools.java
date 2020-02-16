package com.example.maru.utils;


public abstract class TimeTools {

    // FIELDS --------------------------------------------------------------------------------------

    private static final String SEPARATOR = ":";
    public static final String PATTERN_FORMAT = "hh" + TimeTools.SEPARATOR + "mm";

    // METHODS -------------------------------------------------------------------------------------


    public static String convertSecondToString(int time) {
        int hour = time / 3600;
        int minute = (time % 3600) / 60;

        return ((hour < 10) ? "0" : "") + hour +
                TimeTools.SEPARATOR +
                ((minute < 10) ? "0" : "") + minute;
    }
}
