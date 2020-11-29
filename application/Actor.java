package application;


import java.util.ArrayList;

public class Actor {
    private String title;
    private double rating;

    public Actor (String title, double rating) {
        this.title = title;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                '}';
    }
}
