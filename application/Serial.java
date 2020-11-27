package application;

import entertainment.Season;
import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serial {
    private String title;

    private int numberOfSeasons;

    private ArrayList<Season> seasons;

    private int currentSeason;

    private int duration;

    public double [] seasonsRatings;

    public Serial(SerialInputData serialInputData) {
        this.title = serialInputData.getTitle();
        this.numberOfSeasons =  serialInputData.getNumberSeason();
        this.seasons = serialInputData.getSeasons();
        this.seasonsRatings = new double[this.numberOfSeasons];
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double[] getSeasonsRatings() {
        return seasonsRatings;
    }

    public void setSeasonsRatings(double[] seasonsRatings) {
        this.seasonsRatings = seasonsRatings;
    }

    public void rateSeason(double rating, int i) {
        this.seasonsRatings[i-1] = rating;
    }

    @Override
    public String toString() {
        return "Serial{" +
                "title='" + title + '\'' +
                ", numberOfSeasons=" + numberOfSeasons +
                ", seasonsRatings=" + Arrays.toString(seasonsRatings) +
                '}';
    }
}
