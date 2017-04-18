package com.gramazski.craps.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandler {
    /**
     * @return
     */
    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        return dateFormat.format(date);
    }

    /**
     * @return
     */
    public static String getCurrentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }
}
