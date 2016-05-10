package com.fuadhamidan.moviedb.data;

import com.fuadhamidan.moviedb.model.Movie;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.data
 * -Desc Class
 */

public interface MovieService {
    String ENDPOINT = "http://api.themoviedb.org/3/";
    String IMAGE_ENDPOINT = "http://image.tmdb.org/t/p/";
    String YOUTUBE_ENDPOINT = "https://www.youtube.com/watch?v=";

    @GET("movie/{sortOrder}")
    Observable<MovieResponse> list(
            @Path("sortOrder") String sortOrder,
            @Query("api_key") String apikey);

    @GET("movie/{movieId}")
    Observable<Movie> movie(
            @Path("movieId") int movieId,
            @Query("api_key") String apikey);

    @GET("movie/{movieId}/videos")
    Observable<TrailerResponse> trailer(
            @Path("movieId") int movieId,
            @Query("api_key") String apikey);

    @GET("movie/{movieId}/reviews")
    Observable<ReviewResponse> review(
            @Path("movieId") int movieId,
            @Query("api_key") String apikey);
}
