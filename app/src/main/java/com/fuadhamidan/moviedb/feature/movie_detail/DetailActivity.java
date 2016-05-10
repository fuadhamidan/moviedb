package com.fuadhamidan.moviedb.feature.movie_detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.feature.main.BaseActivity;
import com.fuadhamidan.moviedb.model.MovieResults;

import butterknife.ButterKnife;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.movie_detail
 * -Desc Class
 */
public class DetailActivity extends BaseActivity {
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        boolean twopane = bundle.getBoolean("TwoPane");
        MovieResults movieResults = bundle.getParcelable("MovieResults");

        if (savedInstanceState == null) {
            mCurrentFragment = DetailFragment.newInstance(movieResults, twopane);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mCurrentFragment).commit();
        } else {
            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mCurrentFragment");
        }
    }
}
