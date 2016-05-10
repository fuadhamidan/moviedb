package com.fuadhamidan.moviedb;

import android.app.Application;

import com.fuadhamidan.moviedb.util.Cons;

import timber.log.Timber;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb
 * -Desc Class
 */
public class MovieDbApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(Cons.ENABLE_TIMBER)
            Timber.plant(new Timber.DebugTree());
    }
}
