package application;

import entertainment.Genre;
import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static utils.Utils.stringToGenre;

public class Serial {
    private String title;

    private int numberOfSeasons;

    private ArrayList<Season> seasons;

    private int currentSeason;

    private int durationSerial;

    private int year;

    private ArrayList<String> cast;

    private ArrayList<String> genres;

    private HashMap<String, Double> ratedSeasons= new HashMap<>();


    private double generalRatingSerial;

    private double sumRatings;

    private int timesInFavoriteSerial;

    private int totalViews;


    public Serial(SerialInputData serialInputData) {
        this.title = serialInputData.getTitle();
        this.numberOfSeasons =  serialInputData.getNumberSeason();
        this.seasons = serialInputData.getSeasons();
        this.cast = serialInputData.getCast();
        this.year = serialInputData.getYear();
        this.genres = serialInputData.getGenres();
    }

    public int getTimesInFavoriteSerial() {
        return timesInFavoriteSerial;
    }

    public void increaseTimesInFavoriteSerial() {
        timesInFavoriteSerial++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }

    public double getSumRatings() {
        return sumRatings;
    }

    public void setSumRatings(double sumRatings) {
        this.sumRatings = sumRatings;
    }

    public double getGeneralRatingSerial() {
        return generalRatingSerial;
    }

    public void setGeneralRatingSerial(double generalRatingSerial) {
        this.generalRatingSerial = generalRatingSerial;
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

    public HashMap<String, Double> getRatedSeasons() {
        return ratedSeasons;
    }

    public void setRatedSeasons(HashMap<String, Double> ratedSeasons) {
        this.ratedSeasons = ratedSeasons;
    }

    public void addRatedSeasons (String name, Double rate) {
        ratedSeasons.put(name, rate);
        sumRatings += rate;
    }

    public void generalRating () {
        ArrayList<String> users = new ArrayList<>();
        for (HashMap.Entry<String, Double> entry : ratedSeasons.entrySet()){
            String key = entry.getKey();
            if (!users.equals(key)) {
                users.add(key);
            }
        }
        if (users.size() == 0)
            generalRatingSerial = 0;
        else generalRatingSerial = sumRatings/(users.size() * numberOfSeasons);
    }

    public int checkFiltersSerial (String yearFilter, Genre genreFilter) {
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
            else  return 1;
        }

        if (yFilter == 0) {
            for (String genre : genres)
                if (stringToGenre(genre).equals(genreFilter))
                    return 1;
                return 0;
        }

        if (yFilter == year) {
            for (String genre : genres)
                if (stringToGenre(genre).equals(genreFilter))
                    return 1;
        }
        return 0;
    }

    public void iterateFavoriteSerial (List<User> u) {
        for (int i = 0 ; i < u.size(); i++) {
            for (int j = 0; j < u.get(i).getFavoriteMovies().size(); j++)
                if ( u.get(i).getFavoriteMovies().get(j).equals(title))
                    timesInFavoriteSerial++;
        }
    }
    public void setDurationSerial () {
        for (int i = 0; i < seasons.size(); i++){
            durationSerial += seasons.get(i).getDuration();
        }
    }

    public void setTotalViewsSerial(List<User> u) {
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

    public int getDurationSerial() {
        return durationSerial;
    }

    public void sortCastDesc () {
        Collections.reverse(cast);
    }

    public int getTotalViews() {
        return totalViews;
    }
}
