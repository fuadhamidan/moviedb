package com.fuadhamidan.moviedb.feature.movie_list;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.databinding.ItemMovieBinding;
import com.fuadhamidan.moviedb.model.MovieResults;

import java.util.List;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.movie_list
 * -Desc Class
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private MovieFragment mMovieFragment;
    private List<MovieResults> mResultses;

    private boolean mTwoPane;

    public MovieAdapter(MovieFragment movieFragment, List<MovieResults> resultses, boolean twoPane) {
        mResultses  = resultses;
        mMovieFragment = movieFragment;
        mTwoPane = twoPane;
    }

    public void setResultses(List<MovieResults> resultses) {
        mResultses = resultses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_movie,
                parent,
                false);

        return new ViewHolder(binding, mMovieFragment);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.bindMovie(mResultses.get(position), mTwoPane);
    }

    @Override
    public int getItemCount() {
        return mResultses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MovieFragment mMovieFragment;
        ItemMovieBinding mItemMovieBinding;

        public ViewHolder(ItemMovieBinding binding, MovieFragment movieFragment) {
            super(binding.itemMovie);
            mMovieFragment = movieFragment;
            mItemMovieBinding = binding;
        }

        void bindMovie(MovieResults movieResults, boolean twoPane) {
            if (mItemMovieBinding.getViewmodel() == null) {
                mItemMovieBinding.setViewmodel(new ItemMovieViewModel(movieResults, mMovieFragment, twoPane));
            } else {
                mItemMovieBinding.getViewmodel().setMovieResults(movieResults);
            }
        }
    }
}


