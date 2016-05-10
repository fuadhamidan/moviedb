package com.fuadhamidan.moviedb.feature.movie_list;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;

import com.fuadhamidan.moviedb.R;
import com.fuadhamidan.moviedb.databinding.FragmentMovieListBinding;
import com.fuadhamidan.moviedb.feature.main.BaseFragment;
import com.fuadhamidan.moviedb.model.Movie;
import com.fuadhamidan.moviedb.model.MovieResults;
import com.fuadhamidan.moviedb.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.feature.movie_list
 * -Desc Class
 */
public class MovieFragment extends BaseFragment implements MovieViewModel.MovieRequestListener,
        SwipeRefreshLayout.OnRefreshListener{
    private final static String POPULAR = "popular";
    private final static String TOP_RATED = "top_rated";

    private static final String STATE_LIST = "state_list";
    private static final String STATE_SORT = "state_sort";

    private List<MovieResults> mResultses;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MovieAdapter mMovieAdapter;
    private MovieViewModel mMovieViewModel;
    private FragmentMovieListBinding mFragmentMovieBinding;

    private boolean mTwoPane;
    private int inSortOrder = 1;

    public static MovieFragment newInstance(boolean twoPane) {
        MovieFragment fragment = new MovieFragment();

        Bundle bundle = new Bundle();
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
        mTwoPane = getArguments().getBoolean("TWO_PANE");

        mMovieViewModel = new MovieViewModel(this, this);
        mFragmentMovieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false);

        ButterKnife.bind(this, mFragmentMovieBinding.getRoot());

        mFragmentMovieBinding.setViewmodel(mMovieViewModel);

        return mFragmentMovieBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarTitle(R.string.toolbar_title_popular);

        mResultses = new ArrayList<>();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if(savedInstanceState != null){
            inSortOrder = savedInstanceState.getInt(STATE_SORT);
            mResultses = savedInstanceState.getParcelableArrayList(STATE_LIST);
        }

        setupRecyclerView(mFragmentMovieBinding.recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (inSortOrder == 3)
            populateFavouriteList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mResultses.size() > 0){
            outState.putInt(STATE_SORT, inSortOrder);
            outState.putParcelableArrayList(STATE_LIST, (ArrayList<MovieResults>) mResultses);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        if (inSortOrder == 1)
            mMovieViewModel.fetch(POPULAR);
        else if (inSortOrder == 2)
            mMovieViewModel.fetch(TOP_RATED);
        else if (inSortOrder == 3)
            populateFavouriteList();
    }

    @Override
    public void onMovieChanged(List<MovieResults> resultses) {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);

        mResultses = resultses;

        mMovieAdapter.setResultses(resultses);
        mMovieAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        mMovieAdapter = new MovieAdapter(this, mResultses, mTwoPane);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,
                    getResources().getInteger(R.integer.grid_spacing), true));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,
                    getResources().getInteger(R.integer.grid_spacing), true));
        }

        recyclerView.setAlpha(0f);
        recyclerView.animate()
                .setDuration(100)
                .alphaBy(0f)
                .alpha(1f)
                .setInterpolator(new AnticipateInterpolator())
                .start();

        recyclerView.setAdapter(mMovieAdapter);

        if (mResultses.size() == 0)
            mMovieViewModel.fetch(POPULAR);
        else
            mMovieViewModel.mVisibilityProgress.set(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort_order, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.getItem(inSortOrder-1).setChecked(true);

        switch (inSortOrder){
            case 1:
                getToolbar().setTitle(R.string.toolbar_title_popular); break;
            case 2:
                getToolbar().setTitle(R.string.toolbar_title_top_rated); break;
            case 3:
                getToolbar().setTitle(R.string.toolbar_title_favorites); break;
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popular:
                if (inSortOrder != 1) {
                    inSortOrder = 1;

                    item.setChecked(true);
                    mMovieViewModel.fetch(POPULAR);

                    mSwipeRefreshLayout.setRefreshing(true);

                    getToolbar().setTitle(R.string.toolbar_title_popular);
                }
                break;
            case R.id.top_rated:
                if (inSortOrder != 2) {
                    inSortOrder = 2;

                    item.setChecked(true);
                    mMovieViewModel.fetch(TOP_RATED);

                    mSwipeRefreshLayout.setRefreshing(true);

                    getToolbar().setTitle(R.string.toolbar_title_top_rated);
                }
                break;
            case R.id.favorites:
                if(mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);

                if (inSortOrder != 3) {
                    inSortOrder = 3;

                    item.setChecked(true);

                    populateFavouriteList();

                    getToolbar().setTitle(R.string.toolbar_title_favorites);

                    mMovieViewModel.mVisibilityProgress.set(View.GONE);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateFavouriteList(){
        if(mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);

        RealmResults<Movie> movies = getRealm().where(Movie.class).findAll();

        mResultses = new ArrayList<>();

        if (movies.size() == 0) {
            mMovieViewModel.mVisibilityMessage.set(View.VISIBLE);
            mMovieViewModel.mMessage.set(getString(R.string.message_no_movie_favorites));

            mMovieAdapter.setResultses(mResultses);
            mMovieAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < movies.size(); i++) {
                MovieResults results = new MovieResults();

                Movie movie = movies.get(i);

                results.setId(movie.getId());
                results.setRelease_date(movie.getRelease_date());
                results.setPoster_path(movie.getPoster_path());
                results.setOriginal_title(movie.getOriginal_title());
                results.setOverview(movie.getOverview());
                results.setBackdrop_path(movie.getBackdrop_path());

                mResultses.add(results);
            }

            mMovieAdapter.setResultses(mResultses);
            mMovieAdapter.notifyDataSetChanged();
        }
    }
}