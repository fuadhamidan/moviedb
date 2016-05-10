package com.fuadhamidan.moviedb.feature.movie_detail;

import android.databinding.BaseObservable;

import com.fuadhamidan.moviedb.model.ReviewResults;
import com.fuadhamidan.moviedb.viewmodel.ViewModel;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.movie_detail
 * -Desc Class
 */
public class ItemReviewViewModel extends BaseObservable implements ViewModel {
    private ReviewResults mReviewResults;

    public ItemReviewViewModel(ReviewResults reviewResults) {
        mReviewResults = reviewResults;
    }

    public ReviewResults getReviewResults() {
        return mReviewResults;
    }

    public void setReviewResults(ReviewResults reviewResults) {
        mReviewResults = reviewResults;

        notifyChange();
    }

    @Override
    public void destroy() {
    }
}

