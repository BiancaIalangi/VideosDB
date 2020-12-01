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

    private final String title;

    private final int numberOfSeasons;

    private final ArrayList<Season> seasons;

    private int durationSerial;

    private final int year;

    private final ArrayList<String> cast;

    private final ArrayList<String> genres;

    private final HashMap<String, Double> ratedSeasons = new HashMap<>();

    private double generalRatingSerial;

    private double sumRatings;

    private int timesInFavoriteSerial;

    private int totalViews;

    /**
     * setter of movie entity (a copy of SerialInputData)
     * @param serialInputData to be copied
     */
    public Serial(final SerialInputData serialInputData) {
        this.title = serialInputData.getTitle();
        this.numberOfSeasons =  serialInputData.getNumberSeason();
        this.seasons = serialInputData.getSeasons();
        this.cast = serialInputData.getCast();
        this.year = serialInputData.getYear();
        this.genres = serialInputData.getGenres();
    }

    /**
     * getter of getTimesInFavoriteSerial
     * @return in how many Users' FavoriteList appears
     */
    public int getTimesInFavoriteSerial() {
        return timesInFavoriteSerial;
    }

    /**
     * increase every time a user put this serial in his FavoriteList
     */
    public void increaseTimesInFavoriteSerial() {
        timesInFavoriteSerial++;
    }

    /**
     * getter
     * @return serial's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter
     * @return information about all seasons
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * getter of general rating of this serial
     * @return average of user's rating to this serial
     */
    public double getGeneralRatingSerial() {
        return generalRatingSerial;
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
     * @return show's cast distribution
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * getter
     * @return genres of serial
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
     * duration of all serial
     * @return how long is this serial(all seasons)
     */
    public int getDurationSerial() {
        return durationSerial;
    }

    /**
     * sort cast alphabetically from z to a
     */
    public void sortCastDesc() {
        Collections.reverse(cast);
    }

    /**
     * getter
     * @return total views at this serial from all users
     */
    public int getTotalViews() {
        return totalViews;
    }

    /**
     * in help to calculate the GeneralRatingSerial
     * @param name user's name
     * @param rate the grade gave by user
     */
    public void addRatedSeasons(final String name, final Double rate) {
        ratedSeasons.put(name, rate);
        sumRatings += rate;
    }

    /**
     * check if a user from the database rated the same season
     * in the past, if he didn't rate => sum this rating
     * at the end, calculate the generalRatingSerial
     */
    public void generalRating() {
        ArrayList<String> users = new ArrayList<>();
        for (HashMap.Entry<String, Double> entry : ratedSeasons.entrySet()) {
            String key = entry.getKey();
            if (!users.equals(key)) {
                users.add(key);
            }
        }
        if (users.size() == 0) {
            generalRatingSerial = 0;
        } else {
            generalRatingSerial = sumRatings / (users.size() * numberOfSeasons);
        }
    }

    /**
     * check if the serial has the filters wanted from user
     * if yearFilter and genreFilter are null => there is no filter
     * @param yearFilter the year filter put by user
     * @param genreFilter genre type put by user
     * @return if the serial has this filters or not
     */
    public int checkFiltersSerial(final String yearFilter, final Genre genreFilter) {

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
            } else {
                return 1;
            }
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
     * calculate in how many FavoriteList this serial appear
     * @param u a list of all users with their information
     */
    public void iterateFavoriteSerial(final List<User> u) {
        for (User user : u) {
            for (int j = 0; j < user.getFavoriteMovies().size(); j++) {
                if (user.getFavoriteMovies().get(j).equals(title)) {
                    timesInFavoriteSerial++;
                }
            }
        }
    }

    /**
     * the duration of the serial
     */
    public void setDurationSerial() {
        for (Season season : seasons) {
            durationSerial += season.getDuration();
        }
    }

    /**
     * calculate how many views this serial has from all users
     * @param u a list of all users with their information
     */
    public void setTotalViewsSerial(final List<User> u) {
        for (User user : u) {
            if (!user.getHistory().isEmpty()) {
                if (user.getHistory().containsKey(title)) {
                    totalViews += user.getHistory().get(title);
                }
            }
        }
    }
}
