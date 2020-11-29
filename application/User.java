package application;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String username;

    private String subscriptionType;

    private Map<String, Integer> history;

    private ArrayList<String> favoriteMovies;

    private Map<String, Double> ratedMovies = new HashMap<>();

    private Map<String, Double> ratedSerials = new HashMap<>();

    private ArrayList<String> ratedShows = new ArrayList<>();

    private HashMap<String, Integer> ratedSerial = new HashMap<>();


    public User(UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.history = user.getHistory();
        this.favoriteMovies = user.getFavoriteMovies();
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Double> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(Map<String, Double> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public Map<String, Double> getRatedSerials() {
        return ratedSerials;
    }

    public void setRatedSerials(Map<String, Double> ratedSerials) {
        this.ratedSerials = ratedSerials;
    }

    public ArrayList<String> getRatedShows() {
        return ratedShows;
    }

    public void setRatedShows(ArrayList<String> ratedShows) {
        this.ratedShows = ratedShows;
    }

    public HashMap<String, Integer> getRatedSerial() {
        return ratedSerial;
    }

    public void setRatedSerial(HashMap<String, Integer> ratedSerial) {
        this.ratedSerial = ratedSerial;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public void addHasRatedMovie(String title) {

        ratedShows.add(title);
    }

    public void addRatedSerial(String title, int season) {
        ratedSerial.put(title, season);
    }

    public int didRateShowS(String title, int nr) {
        for (HashMap.Entry<String, Integer> entry: ratedSerial.entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            if ((k.equals(title)) && (v == nr)) {
                return 0;
            }
        }
        return 1;
    }


    public int didRateMovie(String title) {
        for (String s : ratedShows) {
            if (s.equals(title)) {
                return 0;
            }
        }
        return 1;
    }

    public int addFavoriteList(String title) {
        for (String favoriteMovie : favoriteMovies) {
            if (favoriteMovie.equals(title))
                return 1;
        }
        return 0;
    }
}
