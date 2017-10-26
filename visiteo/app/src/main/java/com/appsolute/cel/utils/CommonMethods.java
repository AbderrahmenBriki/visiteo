package com.appsolute.cel.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Arun on 1/18/2016.
 */
public class CommonMethods {
    /**
     * get date format
     *
     * @param dateString
     * @return
     */
    public static Date getDateFormat(String dateString) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(dateString));
        Date date = cal.getTime();
        try {
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get boolean value from cursor
     *
     * @param value
     * @return
     */
    public static boolean getBoolean(int value) {
        if (value == 0)
            return false;
        else
            return true;
    }
}
