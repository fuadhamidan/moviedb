package com.fuadhamidan.moviedb.feature.movie_detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.data.MovieService;
import com.fuadhamidan.moviedb.databinding.FragmentMovieDetailBinding;
import com.fuadhamidan.moviedb.feature.main.BaseFragment;
import com.fuadhamidan.moviedb.model.Movie;
import com.fuadhamidan.moviedb.model.MovieResults;
import com.fuadhamidan.moviedb.model.ReviewResults;
import com.fuadhamidan.moviedb.model.TrailerResults;
import com.fuadhamidan.moviedb.util.DateUtils;
import com.fuadhamidan.moviedb.util.StringUtils;
import com.fuadhamidan.moviedb.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmQuery;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.movie_detail
 * -Desc Class
 */
public class DetailFragment extends BaseFragment implements DetailViewModel.TrailerRequestListener{
    private static final String STATE_MOVIE = "state_movie";
    private static final String STATE_LIST_REVIEW = "state_list_review";
    private static final String STATE_LIST_TRAILER = "state_list_trailer";

    @Bind(R.id.image)
    ImageView mImageView;

    @Bind(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    private List<ReviewResults> mReviewResults;
    private List<TrailerResults> mTrailerResults;

    private Movie mMovie;
    private Toolbar mToolbar;
    private MovieResults mMovieResults;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    private DetailViewModel mMovieDetailViewModel;
    private FragmentMovieDetailBinding mFragmentMovieDetailBinding;

    private boolean mTwoPane;

    public static DetailFragment newInstance(MovieResults movieResults, boolean twoPane) {
        DetailFragment fragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("MOVIE_RESULTS", movieResults);
        bundle.putBoolean("TWO_PANE", twoPane);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMovieDetailViewModel = new DetailViewModel(this, this);
        mFragmentMovieDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);

        ButterKnife.bind(this, mFragmentMovieDetailBinding.getRoot());

        mFragmentMovieDetailBinding.setViewmodel(mMovieDetailViewModel);

        return mFragmentMovieDetailBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTwoPane        = getArguments().getBoolean("TWO_PANE");
        mMovieResults   = getArguments().getParcelable("MOVIE_RESULTS");

        if(!mTwoPane){
            mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);

            setToolbar(mToolbar);
            subActivity(true);
        }

        mReviewResults  = new ArrayList<>();
        mTrailerResults = new ArrayList<>();

        setupMovie();
        setToolbarTitle(mMovieResults.getOriginal_title());
        loadBackDrop(mMovieResults.getBackdrop_path());
        checkFavorite(mMovieResults.getId());

        if(savedInstanceState != null){
            mMovie = savedInstanceState.getParcelable(STATE_MOVIE);
            mReviewResults = savedInstanceState.getParcelableArrayList(STATE_LIST_REVIEW);
            mTrailerResults = savedInstanceState.getParcelableArrayList(STATE_LIST_TRAILER);

            if(mReviewResults == null)
                mReviewResults = new ArrayList<>();

            mMovieDetailViewModel.mRuntime.set(mMovie.getRuntime() + "m");
        }

        setupRecyclerView(mFragmentMovieDetailBinding.contentMovie.recyclerView);
        setupRecyclerViewReview(mFragmentMovieDetailBinding.contentMovie.recyclerViewReview);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMovie != null)
            outState.putParcelable(STATE_MOVIE, mMovie);

        if (mReviewResults.size() > 0) {
            outState.putParcelableArrayList(STATE_LIST_REVIEW, (ArrayList<ReviewResults>) mReviewResults);
        }

        if (mTrailerResults.size() > 0) {
            outState.putParcelableArrayList(STATE_LIST_TRAILER, (ArrayList<TrailerResults>) mTrailerResults);
        }

        super.onSaveInstanceState(outState);
    }

    private void setupMovie(){
        Date release  = DateUtils.stringToDate(mMovieResults.getRelease_date());

        mMovieDetailViewModel.mTitle.set(mMovieResults.getOriginal_title());
        mMovieDetailViewModel.mReleaseYear.set(DateUtils.getYear(release));
        mMovieDetailViewModel.mVoteAverage.set(String.format("%.1f", mMovieResults.getVote_average()) + "/10");
        mMovieDetailViewModel.mOverview.set(mMovieResults.getOverview());

        mMovieDetailViewModel.setMovieId(mMovieResults.getId());
        mMovieDetailViewModel.setFloatingActionButton(mFloatingActionButton);
    }

    private void checkFavorite(int id){
        RealmQuery<Movie> movies = getRealm().where(Movie.class).equalTo("id", id);

        if (movies.count() > 0) {
            mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFavorited)));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                if(mTrailerResults.size() != 0){
                    String message = "See the " + mTrailerResults.get(0).getName() + " of " + mMovieResults.getOriginal_title() +
                            " " + MovieService.YOUTUBE_ENDPOINT + mTrailerResults.get(0).getKey() + " #MovieDB #fuadhamidan";
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                }else{
                    String message = mMovieResults.getOriginal_title() + ", Release date " + mMovieResults.getRelease_date() +
                            " with vote average " + mMovieResults.getVote_average() + "/10 #MovieDB #fuadhamidan";
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                }

                startActivity(Intent.createChooser(intent, "Share via"));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieChanged(Movie movie) {
        mMovie = movie;
    }

    @Override
    public void onTrailerChanged(List<TrailerResults> trailerResults) {
        mTrailerResults = trailerResults;

        mTrailerAdapter.setResultses(trailerResults);
        mTrailerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReviewChanged(List<ReviewResults> reviewResults) {
        mReviewResults = reviewResults;

        mReviewAdapter.setResultses(reviewResults);
        mReviewAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        mTrailerAdapter = new TrailerAdapter(getActivity(), mTrailerResults);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAlpha(0f);
        recyclerView.animate()
                .setDuration(100)
                .alphaBy(0f)
                .alpha(1f)
                .setInterpolator(new AnticipateInterpolator())
                .start();

        recyclerView.setAdapter(mTrailerAdapter);

        if(mTrailerResults.size() == 0)
            mMovieDetailViewModel.fetch(mMovieResults.getId());
        else
            mMovieDetailViewModel.mVisibilityMessageTrailer.set(View.GONE);
    }

    private void setupRecyclerViewReview(RecyclerView recyclerView) {
        mReviewAdapter = new ReviewAdapter(mReviewResults);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));

        recyclerView.setAlpha(0f);
        recyclerView.animate()
                .setDuration(100)
                .alphaBy(0f)
                .alpha(1f)
                .setInterpolator(new AnticipateInterpolator())
                .start();

        recyclerView.setAdapter(mReviewAdapter);

        if (mReviewResults.size() > 0) {
            mMovieDetailViewModel.mVisibilityMessageReview.set(View.GONE);
            mMovieDetailViewModel.mVisibilityRecyclerReview.set(View.VISIBLE);
        }
    }

    private void loadBackDrop(String path){
        Glide.with(this)
                .load(StringUtils.formatPosterOriginalUrl(path))
                .centerCrop()
                .into(mImageView);
    }
}