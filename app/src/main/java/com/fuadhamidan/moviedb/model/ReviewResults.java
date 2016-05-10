package com.fuadhamidan.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by fuadhamidan on 5/6/16.
 * email   : fuadhamidan@gmail.com
 * twitter : @fuadhmidan
 * --
 * Movie DB
 * com.fuadhamidan.moviedb.model
 * -Desc Class
 */
public class ReviewResults implements Parcelable {

    /**
     * id : 56c146cac3a36817f900d5f0
     * author : huy.duc.eastagile
     * content : A funny movie with a romantic love story. Wade Wilson (Ryan Reynolds) is a former Special Forces operative who now works as a mercenary. His world comes crashing down when evil scientist Ajax (Ed Skrein) tortures, disfigures and transforms him into Deadpool. The rogue experiment leaves Deadpool with accelerated healing powers and a twisted sense of humor. With help from mutant allies Colossus and Negasonic Teenage Warhead (Brianna Hildebrand), Deadpool uses his new skills to hunt down the man who nearly destroyed his life.
     * url : https://www.themoviedb.org/review/56c146cac3a36817f900d5f0
     */

    private String id;
    private String author;
    private String content;
    private String url;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public ReviewResults() {
    }

    protected ReviewResults(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Creator<ReviewResults> CREATOR = new Creator<ReviewResults>() {
        @Override
        public ReviewResults createFromParcel(Parcel source) {
            return new ReviewResults(source);
        }

        @Override
        public ReviewResults[] newArray(int size) {
            return new ReviewResults[size];
        }
    };
}
