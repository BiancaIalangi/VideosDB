package application;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Serial {
    private String title;

    private int numberOfSeasons;

    private ArrayList<Season> seasons;

    private int currentSeason;

    private int duration;

    private int year;

    private ArrayList<String> cast;

    private ArrayList<String> genres;

    private HashMap<String, Double> ratedSeasons= new HashMap<>();

    private double generalRatingSerial;

    private double sumRatings = 0;

    public Serial(SerialInputData serialInputData) {
        this.title = serialInputData.getTitle();
        this.numberOfSeasons =  serialInputData.getNumberSeason();
        this.seasons = serialInputData.getSeasons();
        this.cast = serialInputData.getCast();
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

    public void sortCastAsc () {
        Collections.sort(cast);
    }

    public void sortCastDesc () {
        Collections.reverse(cast);
    }

    @Override
    public String toString() {
        return "Serial{" +
                "cast=" + cast +
                '}';
    }
}
