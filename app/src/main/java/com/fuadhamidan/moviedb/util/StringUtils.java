package com.fuadhamidan.moviedb.util;

import com.fuadhamidan.moviedb.data.MovieService;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.util
 * -Desc Class
 */
public class StringUtils {
    public static String formatPosterOriginalUrl(String poster_path){
        return String.format("%s%s%s", MovieService.IMAGE_ENDPOINT, Cons.POSTER_SIZE_ORIGINAL, poster_path);
    }

    public static String formatPoster780Url(String poster_path){
        return String.format("%s%s%s", MovieService.IMAGE_ENDPOINT, Cons.POSTER_SIZE_w780, poster_path);
    }

    public static String formatPoster185Url(String poster_path){
        return String.format("%s%s%s", MovieService.IMAGE_ENDPOINT, Cons.POSTER_SIZE_w185, poster_path);
    }
}
