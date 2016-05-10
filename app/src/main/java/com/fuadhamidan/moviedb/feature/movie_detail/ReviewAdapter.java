package com.fuadhamidan.moviedb.feature.movie_detail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.databinding.ItemReviewBinding;
import com.fuadhamidan.moviedb.model.ReviewResults;

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
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewResults> mResultses;

    public ReviewAdapter(List<ReviewResults> resultses) {
        mResultses  = resultses;
    }

    public void setResultses(List<ReviewResults> resultses) {
        mResultses = resultses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemReviewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_review,
                parent,
                false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.bindReview(mResultses.get(position));
    }

    @Override
    public int getItemCount() {
        return mResultses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemReviewBinding mItemReviewBinding;

        public ViewHolder(ItemReviewBinding binding) {
            super(binding.itemReview);
            mItemReviewBinding = binding;
        }

        void bindReview(ReviewResults reviewResults) {
            if (mItemReviewBinding.getViewmodel() == null) {
                mItemReviewBinding.setViewmodel(new ItemReviewViewModel(reviewResults));
            } else {
                mItemReviewBinding.getViewmodel().setReviewResults(reviewResults);
            }
        }
    }
}


