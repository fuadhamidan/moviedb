package com.fuadhamidan.moviedb.data;

import com.fuadhamidan.moviedb.model.TrailerResults;

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

public class TrailerResponse {

    /**
     * id : 271110
     * results : []
     */

    private int id;
    private List<TrailerResults> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerResults> getResults() {
        return results;
    }

    public void setResults(List<TrailerResults> results) {
        this.results = results;
    }
}
