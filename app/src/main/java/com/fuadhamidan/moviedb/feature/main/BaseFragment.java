package com.fuadhamidan.moviedb.feature.main;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import io.realm.Realm;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.main
 * -Desc Class
 */

public class BaseFragment extends Fragment {
    public Realm getRealm() {
        return ((BaseActivity) getActivity()).getRealm();
    }

    public Toolbar getToolbar() {
        return ((BaseActivity) getActivity()).getToolbar();
    }

    public void setToolbar(Toolbar toolbar) {
        ((BaseActivity) getActivity()).setToolbar(toolbar);
    }

    public void setToolbarTitle(int title) {
        ((BaseActivity) getActivity()).setTitle(title);
    }
    public void setToolbarTitle(String title) {
        ((BaseActivity) getActivity()).setTitle(title);
    }

    public void subActivity(boolean subActivity){
        ((BaseActivity) getActivity()).subActivity(subActivity);
    }
}
