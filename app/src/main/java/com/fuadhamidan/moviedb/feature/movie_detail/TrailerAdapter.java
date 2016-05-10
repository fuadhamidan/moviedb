package com.fuadhamidan.moviedb.feature.movie_detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.databinding.ItemTrailerBinding;
import com.fuadhamidan.moviedb.model.TrailerResults;

import java.util.List;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.movie_detail
 * -Desc Class
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private Context mContext;
    private List<TrailerResults> mResultses;

    public TrailerAdapter(Context context, List<TrailerResults> resultses) {
        mContext = context;
        mResultses = resultses;
    }

    public void setResultses(List<TrailerResults> resultses) {
        mResultses = resultses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTrailerBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_trailer,
                parent,
                false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.bindTrailer(mResultses.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return mResultses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTrailerBinding mItemTrailerBinding;

        public ViewHolder(ItemTrailerBinding binding) {
            super(binding.itemTrailer);
            mItemTrailerBinding = binding;
        }

        void bindTrailer(TrailerResults trailerResult, Context context) {
            if (mItemTrailerBinding.getViewmodel() == null) {
                mItemTrailerBinding.setViewmodel(new ItemTrailerViewModel(trailerResult, context));
            } else {
                mItemTrailerBinding.getViewmodel().setTrailerResults(trailerResult);
            }
        }
    }
}


