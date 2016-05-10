package com.fuadhamidan.moviedb.feature.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.view.View;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.data.MovieService;
import com.fuadhamidan.moviedb.model.TrailerResults;
import com.fuadhamidan.moviedb.util.DialogFactory;
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
public class ItemTrailerViewModel extends BaseObservable implements ViewModel {
    private Context mContext;
    private TrailerResults mTrailerResults;

    public ItemTrailerViewModel(TrailerResults trailerResult, Context context) {
        mContext = context;
        mTrailerResults = trailerResult;
    }

    public TrailerResults getTrailerResults() {
        return mTrailerResults;
    }

    public void setTrailerResults(TrailerResults trailerResults) {
        mTrailerResults = trailerResults;

        notifyChange();
    }

    public void onTrailerClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse(MovieService.YOUTUBE_ENDPOINT + mTrailerResults.getKey()));

        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        } else {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.message_no_receiving_apps);
        }
    }

    @Override
    public void destroy() {
    }
}

