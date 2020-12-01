package application;

import java.util.ArrayList;

public class Show {
    private final String title;

    private final ArrayList<String> genre;

    private final double rating;

    private final int views;

    private final int position;

    private int timesInFavorite;

    /**
     * setter of a list with all movies and shows
     * @param title title of movie or serial
     * @param genre genre of movie or serial
     * @param rating general rating
     * @param views total views from user
     * @param position number of appearance in database
     */
    public Show(final String title, final ArrayList<String> genre,
                 final double rating, final int views, final int position) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.views = views;
        this.position = position;
    }

    /**
     * getter
     * @return position in database of this show
     */
    public int getPosition() {
        return position;
    }

    /**
     * getter
     * @return show title
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter
     * @return genres of this show
     */
    public ArrayList<String> getGenre() {
        return genre;
    }

    /**
     * getter
     * @return total views from all users to this show
     */
    public int getViews() {
        return views;
    }

    /**
     * getter
     * @return average rating to this show
     */
    public double getRating() {
        return rating;
    }

    /**
     * getter
     * @return at how many users appear in FavoriteList
     */
    public int getTimesInFavorite() {
        return timesInFavorite;
    }

    /**
     * every time a user put this show in their FavoriteList
     * this number increase
     */
    public void increaseTimesInFavorite() {
        ++timesInFavorite;
    }
}
