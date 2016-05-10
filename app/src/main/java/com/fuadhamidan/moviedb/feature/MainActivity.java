package com.fuadhamidan.moviedb.feature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.feature.main.BaseActivity;
import com.fuadhamidan.moviedb.feature.movie_list.MovieFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private Fragment mCurrentFragment;

    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar(mToolbar);
        subActivity(false);

        if (findViewById(R.id.fragment) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            mCurrentFragment = MovieFragment.newInstance(mTwoPane);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, mCurrentFragment).commit();
        } else {
            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mCurrentFragment");
        }
    }
}
