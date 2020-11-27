package application;

import fileio.MovieInputData;

import java.util.ArrayList;

public class Movie {
    private int duration;

    private  String title;

    private  int year;

    private  ArrayList<String> cast;

    private  ArrayList<String> genres;

    public Movie(MovieInputData movie) {
        this.duration = movie.getDuration();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.cast = movie.getCast();
        this.genres = movie.getGenres();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
