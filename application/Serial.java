package application;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.HashMap;

public class Serial {
    private int numberOfSeasons;

    private ArrayList<Season> seasons;

    private int currentSeason;

    private int duration;

    private HashMap<Integer, Double> ratings;

    public Serial(Season season, SerialInputData serialInputData) {
        this.numberOfSeasons =  serialInputData.getNumberSeason();
        this.seasons = serialInputData.getSeasons();
        this.currentSeason = season.getCurrentSeason();
        this.duration = season.getDuration();
        for (int i = 0; i < serialInputData.getNumberSeason(); i++) {
            if (season.getRatings().get(i) == null) {
                this.ratings.put(season.getCurrentSeason(), 0.0);
            }
            this.ratings.put(season.getCurrentSeason(), season.getRatings().get(i));
        }
    }

}
