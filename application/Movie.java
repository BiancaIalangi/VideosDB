package application;

import entertainment.Genre;
import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.Utils.stringToGenre;

public class Movie {
    private int duration;

    private  String title;

    private  int year;

    private  ArrayList<String> cast;

    private  ArrayList<String> genres;

    private double generalRatingMovie;

    private double sumRatingsMovie = 0;

    private int divider;

    private int timesInFavoriteMovies;

    private int totalViews;


    public Movie(MovieInputData movie) {
        this.duration = movie.getDuration();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.cast = movie.getCast();
        this.genres = movie.getGenres();
    }

    public int getTimesInFavoriteMovies() {
        return timesInFavoriteMovies;
    }

    public void increaseTimesInFavoriteMovies() {
        timesInFavoriteMovies++;
    }

    public double getGeneralRatingMovie() {
        return generalRatingMovie;
    }

    public void setGeneralRatingMovie(double generalRatingMovie) {
        this.generalRatingMovie = generalRatingMovie;
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

    public double getSumRatingsMovie() {
        return sumRatingsMovie;
    }

    public void setSumRatingsMovie(double sumRatingsMovie) {
        this.sumRatingsMovie = sumRatingsMovie;
    }

    public int getDivider() {
        return divider;
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    public void increaseDivider() {
        ++this.divider;
    }

    public void addRate(double d) {
        this.sumRatingsMovie += d;
    }

    public void generalRating () {
        if (this.divider == 0)
            generalRatingMovie = 0;
        else generalRatingMovie = sumRatingsMovie/divider;
    }

    public int checkFiltersMovie (String yearFilter, Genre genreFilter) {
        if (genres == null && genreFilter != null)
            return 0;
        if (genreFilter == null && yearFilter == null) {
            return 1;
        }

        int yFilter;

        if (yearFilter != null) {
            yFilter = Integer.parseInt(yearFilter);
        } else yFilter = 0;

        if (genreFilter == null) {
            if ( yFilter != year)
                return 0;
            return 1;
        }

        if ( yFilter == 0) {
            for (String genre : genres)
                if (stringToGenre(genre).equals(genreFilter))
                    return 1;
                return 0;
        }

        if ( yFilter == year) {
            for (String genre : genres)
                if (stringToGenre(genre).equals(genreFilter))
                    return 1;
        }
       return 0;
    }

    public void iterateFavoriteMovie (List<User> u) {
        for (User user : u) {
            for (int j = 0; j < user.getFavoriteMovies().size(); j++)
                if (user.getFavoriteMovies().get(j).equals(title))
                    timesInFavoriteMovies++;
        }
    }

    public void setTotalViewsMovie(List<User> u) {
        for (User user : u) {
            if (!user.getHistory().isEmpty()) {
                if (user.getHistory().containsKey(title))
                    totalViews += user.getHistory().get(title);
            }
        }
    }

    public void sortCastAsc () {
        Collections.sort(cast);
    }

    public void sortCastDesc () {
        Collections.reverse(cast);
    }

    public int getTotalViews() {
        return totalViews;
    }

}
