package com.fuadhamidan.moviedb.feature.movie_list;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.feature.movie_detail.DetailActivity;
import com.fuadhamidan.moviedb.feature.movie_detail.DetailFragment;
import com.fuadhamidan.moviedb.model.MovieResults;
import com.fuadhamidan.moviedb.util.StringUtils;
import com.fuadhamidan.moviedb.viewmodel.ViewModel;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.movie_list
 * -Desc Class
 */
public class ItemMovieViewModel extends BaseObservable implements ViewModel {
    private MovieResults mMovieResults;
    private MovieFragment mMovieFragment;

    private boolean mTwoPane;

    public ItemMovieViewModel(MovieResults movieResults, MovieFragment movieFragment, boolean twoPane) {
        mMovieResults = movieResults;
        mMovieFragment = movieFragment;
        mTwoPane = twoPane;
    }

    public MovieResults getMovieResults() {
        return mMovieResults;
    }

    public void setMovieResults(MovieResults movieResults) {
        mMovieResults = movieResults;

        notifyChange();
    }

    public void onMovieClick(View view) {
        if (mTwoPane) {
            FragmentTransaction fragmentTransaction = mMovieFragment.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, DetailFragment.newInstance(mMovieResults, mTwoPane));
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(mMovieFragment.getActivity(), DetailActivity.class);

            intent.putExtra("TwoPane", mTwoPane);
            intent.putExtra("MovieResults", mMovieResults);

            mMovieFragment.startActivity(intent);
        }
    }

    @BindingAdapter({"posterPath"})
    public static void posterImg(final ImageView view, String poster_path) {
        Glide.with(view.getContext())
                .load(StringUtils.formatPoster185Url(poster_path))
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    @Override
    public void destroy() {
    }
}

