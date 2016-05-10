package com.fuadhamidan.moviedb.feature.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.main
 * -Desc Class
 */

public class BaseActivity extends AppCompatActivity {
    protected Realm mRealm;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        mRealm = Realm.getInstance(realmConfig);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        mToolbar = toolbar;

        setSupportActionBar(mToolbar);
    }

    public Realm getRealm() {
        return mRealm;
    }

    public void subActivity(boolean subActivity){
        if (subActivity) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    public void setTitle(int title){
        getSupportActionBar().setTitle(getResources().getString(title));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
