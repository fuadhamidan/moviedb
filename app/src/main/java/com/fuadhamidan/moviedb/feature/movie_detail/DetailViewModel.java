package com.fuadhamidan.moviedb.feature.movie_detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.data.MovieFactory;
import com.fuadhamidan.moviedb.data.MovieService;
import com.fuadhamidan.moviedb.data.ReviewResponse;
import com.fuadhamidan.moviedb.data.TrailerResponse;
import com.fuadhamidan.moviedb.model.Movie;
import com.fuadhamidan.moviedb.model.ReviewResults;
import com.fuadhamidan.moviedb.model.TrailerResults;
import com.fuadhamidan.moviedb.util.Cons;
import com.fuadhamidan.moviedb.util.DataUtils;
import com.fuadhamidan.moviedb.util.DateUtils;
import com.fuadhamidan.moviedb.util.DialogFactory;
import com.fuadhamidan.moviedb.viewmodel.ViewModel;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
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
 * com.fuadhamidan.moviedb.feature.movie_detail
 * -Desc Class
 */

public class DetailViewModel implements ViewModel {
    private List<ReviewResults> mReviewResults;
    private List<TrailerResults> mTrailerResults;

    public Movie mMovie;

    private Realm mRealm;
    private Context mContext;
    private Scheduler mScheduler;
    private Subscription mSubscription;
    private MovieService mMovieService;
    private DetailFragment mDetailFragment;
    private TrailerRequestListener mListener;
    private FloatingActionButton mFloatingActionButton;

    public ObservableInt mVisibilityMessageReview;
    public ObservableInt mVisibilityMessageTrailer;
    public ObservableInt mVisibilityRecyclerReview;

    public ObservableField<String> mTitle;
    public ObservableField<String> mReleaseYear;
    public ObservableField<String> mRuntime;
    public ObservableField<String> mVoteAverage;
    public ObservableField<String> mOverview;

    private int mMovieId = 0;

    public interface TrailerRequestListener {
        void onMovieChanged(Movie movie);
        void onReviewChanged(List<ReviewResults> reviewResults);
        void onTrailerChanged(List<TrailerResults> trailerResults);
    }

    public DetailViewModel(DetailFragment detailFragment, TrailerRequestListener listener) {
        mListener       = listener;
        mScheduler      = Schedulers.io();
        mMovieService   = MovieFactory.create();
        mRealm          = detailFragment.getRealm();
        mContext        = detailFragment.getActivity();
        mDetailFragment = detailFragment;

        mTitle          = new ObservableField<>("-");
        mReleaseYear    = new ObservableField<>("-");
        mRuntime        = new ObservableField<>("-");
        mVoteAverage    = new ObservableField<>("-");
        mOverview       = new ObservableField<>("-");

        mVisibilityMessageReview  = new ObservableInt(View.VISIBLE);
        mVisibilityMessageTrailer = new ObservableInt(View.VISIBLE);
        mVisibilityRecyclerReview = new ObservableInt(View.GONE);
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public void setFloatingActionButton(FloatingActionButton floatingActionButton) {
        mFloatingActionButton = floatingActionButton;
    }

    public void fetch(int movieId) {
        if (DataUtils.isNetworkAvailable(mContext)) {
            getMovie(movieId);
        } else {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.message_no_network_connection);
        }
    }

    private void fetchTrailer(int movieId) {
        if (DataUtils.isNetworkAvailable(mContext)) {
            getTrailer(movieId);
        } else {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.message_no_network_connection);
        }
    }

    private void fetchReview(int movieId) {
        if (DataUtils.isNetworkAvailable(mContext)) {
            getReview(movieId);
        } else {
            DialogFactory.createSimpleOkErrorDialog(mContext, R.string.message_no_network_connection);
        }
    }

    public void onFavoritedClick(View view){
        if (mMovie != null) {
            RealmQuery<Movie> movies = mRealm.where(Movie.class).equalTo("id", mMovieId);

            if(movies.count() > 0){
                mRealm.beginTransaction();

                Movie mov = movies.findFirst();
                mov.deleteFromRealm();

                mRealm.commitTransaction();

                mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorUnfavorited)));

                DialogFactory.createSimpleSnackbarDone(mDetailFragment.getActivity().getCurrentFocus(),
                        R.string.message_unfavorited, Snackbar.LENGTH_SHORT).show();
            }else{
                mRealm.beginTransaction();
                mRealm.copyToRealm(mMovie);
                mRealm.commitTransaction();

                mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorFavorited)));

                DialogFactory.createSimpleSnackbarDone(mDetailFragment.getActivity().getCurrentFocus(),
                        R.string.message_favorited, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public void getMovie(int movieId) {
        unSubscribeObservable();

        mSubscription = mMovieService.movie(movieId, Cons.MOVIE_DB_API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mScheduler)
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {
                        if (mMovie != null) {
                            Date release = DateUtils.stringToDate(mMovie.getRelease_date());

                            mTitle.set(mMovie.getOriginal_title());
                            mReleaseYear.set(DateUtils.getYear(release));
                            mRuntime.set(mMovie.getRuntime() + "m");
                            mVoteAverage.set(String.format("%.1f", mMovie.getVote_average()) + "/10");
                            mOverview.set(mMovie.getOverview());

                            if (mListener != null)
                                mListener.onMovieChanged(mMovie);

                            fetchTrailer(mMovie.getId());
                        }

                        Timber.d("Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createSimpleOkErrorDialog(mContext, e.getMessage());
                    }

                    @Override
                    public void onNext(Movie movie) {
                        mMovie = movie;
                    }
                });
    }

    public void getTrailer(final int movieId) {
        unSubscribeObservable();

        mSubscription = mMovieService.trailer(movieId, Cons.MOVIE_DB_API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mScheduler)
                .subscribe(new Subscriber<TrailerResponse>() {
                    @Override
                    public void onCompleted() {
                        if (mTrailerResults != null) {
                            if (mListener != null)
                                mListener.onTrailerChanged(mTrailerResults);

                            fetchReview(movieId);

                            if (mTrailerResults.size() > 0) {
                                mVisibilityMessageTrailer.set(View.GONE);
                            }
                        }

                        Timber.d("Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createSimpleOkErrorDialog(mContext, e.getMessage());
                    }

                    @Override
                    public void onNext(TrailerResponse trailerResponse) {
                        mTrailerResults = trailerResponse.getResults();
                    }
                });
    }

    public void getReview(int movieId) {
        unSubscribeObservable();

        mSubscription = mMovieService.review(movieId, Cons.MOVIE_DB_API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mScheduler)
                .subscribe(new Subscriber<ReviewResponse>() {
                    @Override
                    public void onCompleted() {
                        if (mReviewResults != null) {
                            if (mListener != null)
                                mListener.onReviewChanged(mReviewResults);

                            if (mReviewResults.size() > 0) {
                                mVisibilityMessageReview.set(View.GONE);
                                mVisibilityRecyclerReview.set(View.VISIBLE);
                            }
                        }

                        Timber.d("Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createSimpleOkErrorDialog(mContext, e.getMessage());
                    }

                    @Override
                    public void onNext(ReviewResponse reviewResponse) {
                        mReviewResults = reviewResponse.getResults();
                    }
                });
    }

    private void unSubscribeObservable() {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    private void reset() {
        unSubscribeObservable();

        mContext = null;
        mSubscription = null;
    }

    @Override
    public void destroy() {
        reset();
    }
}

