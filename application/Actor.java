package application;

public class Actor {

    private final String name;

    private double rating;

    private int timesin;

    /**
     * setter for actor entity (used for Average)
     * @param rating of movie/serial he/she played
     * @param name of actor
     */
    public Actor(final double rating, final String name) {
        this.name = name;
        this.rating = rating;
        timesin = 1;
    }

    /**
     * getter for actor name
     * @return actor name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for rating average
     * @return average rating of movies/serials he/she played
     */
    public double getRating() {
        return rating;
    }

    /**
     * sum of ratings from movies/serials actor played
     * @param addRating is a rating from a movie/serial played
     */
    public void setRating(final double addRating) {
        rating += addRating;
    }

    /**
     * average from actor's movies/serials played
     */
    public void finalRating() {
        rating = rating / timesin;
    }

    /**
     * getter of timesin
     * @return in how many movies from showlist appear
     */
    public int getTimesin() {
        return timesin;
    }

    /**
     * every time Actor appear in a show from Showlist
     * timesin gets bigger
     */
    public void incrementTimesin() {
        timesin++;
    }
}
