package application;

import java.util.ArrayList;

public class Show {
    private String title;

    private ArrayList<String> genre;

    private double rating;

    private int views;

    private int position;

    private int timesInFavorite;

    public Show (String title, ArrayList<String> genre, double rating, int views, int position) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.views = views;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTimesInFavorite() {
        return timesInFavorite;
    }

    public void increaseTimesInFavorite() {
        ++timesInFavorite;
    }
}
