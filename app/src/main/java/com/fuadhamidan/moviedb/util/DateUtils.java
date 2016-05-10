package com.fuadhamidan.moviedb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.util
 * -Desc Class
 */
public class DateUtils {
    public static Date stringToDate(String strDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = simpleDateFormat.parse(strDate);
            return date;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static String getYear(Date formatDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(formatDate);
    }
}
