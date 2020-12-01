package application;

import entertainment.Genre;
import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.Utils.stringToGenre;

public class Movie {

    private final int duration;

    private final String title;

    private final int year;

    private final ArrayList<String> cast;

    private final ArrayList<String> genres;

    private double generalRatingMovie;

    private double sumRatingsMovie = 0;

    private int divider;

    private int timesInFavoriteMovies;

    private int totalViews;

    /**
     * setter of movie entity (a copy of MovieInputData)
     * @param movie to be copied
     */
    public Movie(final MovieInputData movie) {
        this.duration = movie.getDuration();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.cast = movie.getCast();
        this.genres = movie.getGenres();
    }

    /**
     * getter of getTimesInFavoriteMovies
     * @return in how many Users' FavoriteList appears
     */
    public int getTimesInFavoriteMovies() {
        return timesInFavoriteMovies;
    }

    /**
     * increase every time a user put this movie in his FavoriteList
     */
    public void increaseTimesInFavoriteMovies() {
        timesInFavoriteMovies++;
    }

    /**
     * getter of general rating of this movie
     * @return average of user's rating to this movie
     */
    public double getGeneralRatingMovie() {
        return generalRatingMovie;
    }

    /**
     * getter of movie's duration
     * @return how long does movie take
     */
    public int getDuration() {
        return duration;
    }

    /**
     * getter
     * @return movie's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter
     * @return movie's year
     */
    public int getYear() {
        return year;
    }

    /**
     * getter
     * @return movie's cast distribution
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * getter
     * @return genres of movie
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * sort cast alphabetically from a to z
     */
    public void sortCastAsc() {
        Collections.sort(cast);
    }

    /**
     * sort cast alphabetically from z to a
     */
    public void sortCastDesc() {
        Collections.reverse(cast);
    }

    /**
     * getter
     * @return total views at this movie from all users
     */
    public int getTotalViews() {
        return totalViews;
    }

    /**
     * in help to calculate the GeneralRatingMovie
     * how many users rated this movie
     */
    public void increaseDivider() {
        ++this.divider;
    }

    /**
     * in help to calculate the generalRatingMovie
     * sum of all users' rating
     * @param d rating from a user
     */
    public void addRate(final double d) {
        this.sumRatingsMovie += d;
    }

    /**
     * calculate the generalRatingMovie
     * in case no one rated this movie the generalRatingMovie is 0
     */
    public void generalRating() {
        if (this.divider == 0) {
            generalRatingMovie = 0;
        } else {
            generalRatingMovie = sumRatingsMovie / divider;
        }
    }


    /**
     * check if the movies has the filters wanted from user
     * if yearFilter and genreFilter are null => there is no filter
     * @param yearFilter the year filter put by user
     * @param genreFilter genre type put by user
     * @return if the movie has this filters or not
     */
    public int checkFiltersMovie(final String yearFilter, final Genre genreFilter) {

        if (genres == null && genreFilter != null) {
            return 0;
        }

        if (genreFilter == null && yearFilter == null) {
            return 1;
        }

        int yFilter;

        if (yearFilter != null) {
            yFilter = Integer.parseInt(yearFilter);
        } else {
            yFilter = 0;
        }

        if (genreFilter == null) {
            if (yFilter != year) {
                return 0;
            }
            return 1;
        }

        if (yFilter == 0) {
            for (String genre : genres) {
                if (stringToGenre(genre).equals(genreFilter)) {
                    return 1;
                }
            }
            return 0;
        }

        if (yFilter == year) {
            for (String genre : genres) {
                if (stringToGenre(genre).equals(genreFilter)) {
                    return 1;
                }
            }
        }
       return 0;
    }

    /**
     * calculate in how many FavoriteList this movie appear
     * @param u a list of all users with their information
     */
    public void iterateFavoriteMovie(final List<User> u) {
        for (User user : u) {
            for (int j = 0; j < user.getFavoriteMovies().size(); j++) {
                if (user.getFavoriteMovies().get(j).equals(title)) {
                    timesInFavoriteMovies++;
                }
            }
        }
    }

    /**
     * calculate how many views this movie has from all users
     * @param u a list of all users with their information
     */
    public void setTotalViewsMovie(final List<User> u) {
        for (User user : u) {
            if (!user.getHistory().isEmpty()) {
                if (user.getHistory().containsKey(title)) {
                    totalViews += user.getHistory().get(title);
                }
            }
        }
    }
}
