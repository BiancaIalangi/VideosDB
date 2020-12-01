package application;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private String username;

    private String subscriptionType;

    private Map<String, Integer> history;

    private ArrayList<String> favoriteMovies;

    private Map<String, Double> ratedMovies = new HashMap<>();

    private Map<String, List<Integer>> ratedSerial = new HashMap<>();

    private int activity;

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

    public Map<String,  List<Integer>> getRatedSerial() {
        return ratedSerial;
    }

    public void setRatedSerial(Map<String,  List<Integer>> ratedSerial) {
        this.ratedSerial = ratedSerial;
    }

    public int getActivity() {
        return activity;
    }

    public void increaseActivity() {
        ++activity;
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
    public void addHasRatedMovie(String title, Double grade) {
        ratedMovies.put(title, grade);
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

    public int didRateShowS(String title, int nr) {
        if (!ratedSerial.containsKey(title)) {
            ratedSerial.put(title, new ArrayList<>());
        }
        else if (ratedSerial.get(title).contains(nr))
            return 0;
        ratedSerial.get(title).add(nr);
        return 1;
    }


    public int didRateMovie(String title) {
        for (Map.Entry<String, Double> s : ratedMovies.entrySet()) {
            if (s.getKey().equals(title)) {
                return 0;
            }
        }
        return 1;
    }

//    public void setActivity () {
//        for (Map.Entry<String, List<Integer>> s : ratedSerial.entrySet()) {
//            activity += s.getValue().size();
//        }
//        activity += ratedMovies.size();
//    }

    public int addFavoriteList(String title) {
        for (String favoriteMovie : favoriteMovies) {
            if (favoriteMovie.equals(title))
                return 1;
        }
        return 0;
    }
}
