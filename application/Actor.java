package application;

public class Actor {

    private String title;
    private String name;
    private double rating;
    private int timesin;

    public Actor (double rating, String name) {
        this.name = name;
        this.rating = rating;
        timesin = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimesin(int timesin) {
        this.timesin = timesin;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double addRating) {
        rating += addRating;
    }

    public void finalRating() {
        rating = rating/timesin;
    }

    public int getTimesin() {
        return timesin;
    }

    public void incrementTimesin() {
        timesin++;
    }
}
