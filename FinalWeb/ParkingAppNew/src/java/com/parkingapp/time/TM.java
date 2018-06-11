package com.parkingapp.time;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TM {

    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
    public static String DD_MM_YYYY_HH_MM_SS_A = "dd/MM/yyyy hh:mm:ss a";
    public static String DD_MM_YYYY_HH_MM = "dd/MM/yyyy hh:mm";
    public static String HH_MM_SS = "hh:mm:ss";

    public  static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    public static String getDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static boolean isBeforeOneHour(String dateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS_A);
            Date bookingdate = sdf.parse(dateTime);
            Calendar cal = Calendar.getInstance();
            long minMinus = ((cal.getTimeInMillis() - bookingdate.getTime()) / MINUTE);
            if (minMinus > 60) {
                return true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(TM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    

    public static String dateToMysqlDateTime(String dateTime) throws ParseException {
        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date dt = new java.util.Date(dateTime);
        String currentTime = sdf.format(dt);
        return currentTime;
    }

    public static long diffDate(String date1, String date2, String format) {
        long d = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            d = d1.getTime() - d2.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(TM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    public static void main(String[] args) throws ParseException {
        //System.out.println("date :" + isBeforeOneHour("22/09/2017 04:34:00 PM"));
        long diffDate = diffDate( "22/09/2017 09:40:42 PM", getDate(TM.DD_MM_YYYY_HH_MM_SS_A),TM.DD_MM_YYYY_HH_MM_SS_A);
        System.out.println(diffDate/TM.HOUR);
    }
}
