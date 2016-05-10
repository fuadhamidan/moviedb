package com.fuadhamidan.moviedb.feature.movie_list;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.data.MovieFactory;
import com.fuadhamidan.moviedb.data.MovieResponse;
import com.fuadhamidan.moviedb.data.MovieService;
import com.fuadhamidan.moviedb.model.MovieResults;
import com.fuadhamidan.moviedb.util.Cons;
import com.fuadhamidan.moviedb.util.DataUtils;
import com.fuadhamidan.moviedb.util.DialogFactory;
import com.fuadhamidan.moviedb.viewmodel.ViewModel;

import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.movie_list
 * -Desc Class
 */
public class MovieViewModel implements ViewModel {
    private List<MovieResults> mResultses;

    private Context mContext;
    private Scheduler mScheduler;
    private Subscription mSubscription;
    private MovieService mMovieService;
    private MovieFragment mMovieFragment;
    private MovieRequestListener mListener;

    public ObservableInt mVisibilityMessage;
    public ObservableInt mVisibilityProgress;

    public ObservableField<String> mMessage;

    public interface MovieRequestListener {
        void onMovieChanged(List<MovieResults> movieResultses);
    }

    public MovieViewModel(MovieFragment movieFragment, MovieRequestListener listener) {
        mListener           = listener;
        mScheduler          = Schedulers.io();
        mMovieService       = MovieFactory.create();
        mMovieFragment      = movieFragment;
        mContext            = movieFragment.getActivity();

        mVisibilityMessage  = new ObservableInt(View.GONE);
        mVisibilityProgress = new ObservableInt(View.VISIBLE);

        mMessage            = new ObservableField<>("");
    }

    public void fetch(String sortOrder){
        mVisibilityMessage.set(View.GONE);

        if(DataUtils.isNetworkAvailable(mContext)){
            getMovie(sortOrder);
        }else{
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.message_no_network_connection);
        }
    }

    public void getMovie(String sortOrder) {
        unSubscribeObservable();

        mSubscription = mMovieService.list(sortOrder, Cons.MOVIE_DB_API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mScheduler)
                .subscribe(new Subscriber<MovieResponse>() {
                    @Override
                    public void onCompleted() {
                        if(mResultses != null) {
                            if (mListener != null)
                                mListener.onMovieChanged(mResultses);

                            mVisibilityProgress.set(View.GONE);

                            Timber.d("Complete");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createSimpleOkErrorDialog(mContext, e.getMessage());
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        mResultses = movieResponse.getResults();

                        Timber.i("Popular Movie List:");
                        for (int i = 0; i < mResultses.size(); i++) {
                            Timber.v(mResultses.get(i).getOriginal_title());
                        }
                    }
                });
    }

    private void unSubscribeObservable() {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    private void reset(){
        unSubscribeObservable();

        mContext        = null;
        mSubscription   = null;
    }

    @Override
    public void destroy() {
        reset();
    }
}

