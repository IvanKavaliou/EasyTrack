package com.itibo.tracking;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Converters {

    public static String parseFormateDate(String dateString, String inFormat, String outFormat)
    {
        return convertDateToString(convertStringToDate(dateString, inFormat), outFormat);
    }

    public static Date convertStringToDate(String dateString, String inFormat){
        SimpleDateFormat format = new SimpleDateFormat(inFormat);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (Exception e){
            System.out.println(dateString +" / " + inFormat);
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static String convertDateToString(Date date, String outFormat){
        SimpleDateFormat formatNew = new SimpleDateFormat(outFormat);
        formatNew.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatNew.format(date);
    }
}
