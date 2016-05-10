package com.fuadhamidan.moviedb.data;

import com.fuadhamidan.moviedb.model.ReviewResults;

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

public class ReviewResponse {

    /**
     * id : 293660
     * page : 1
     * results : []
     * total_pages : 1
     * total_results : 2
     */

    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private List<ReviewResults> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<ReviewResults> getResults() {
        return results;
    }

    public void setResults(List<ReviewResults> results) {
        this.results = results;
    }
}
