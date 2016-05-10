package com.fuadhamidan.moviedb.data;

import com.fuadhamidan.moviedb.model.MovieResults;

import java.util.List;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.data
 * -Desc Class
 */

public class MovieResponse {

    /**
     * page : 1
     * results : []
     * total_results : 19737
     * total_pages : 987
     */

    private int page;
    private int total_results;
    private int total_pages;
    private List<MovieResults> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<MovieResults> getResults() {
        return results;
    }

    public void setResults(List<MovieResults> results) {
        this.results = results;
    }
}
