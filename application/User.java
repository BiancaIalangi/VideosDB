package application;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Map;

public class User {

    private String username;

    private String subscriptionType;

    private Map<String, Integer> history;

    private ArrayList<String> favoriteMovies;

    private ArrayList<String> ratedVideo = new ArrayList<>();

    //private ArrayList<Serial> ratedSerials = new ArrayList<>();


    public User(UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.history = user.getHistory();
        this.favoriteMovies = user.getFavoriteMovies();
    }

    public User(String username,Map<String, Integer> history, ArrayList<String> favoriteMovies) {
        this.username = username;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
    }

    public String getUsername() {
        return username;
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

    public int addReviewMovie(String title) {
        if (!this.ratedVideo.contains(title)) {
            this.ratedVideo.add(title);
            return 1;
        } else return -1;
    }

    public int addReviewSerial(String title, int season, double[] v, double grade) {
        double [] aux = v.clone();
        if (!this.ratedVideo.contains(title)) {
            this.ratedVideo.add(title);
            return 1;
        } else {
            if (aux[season - 1] == 0) {
                return 1;
            } else return -1;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", history=" + history +
                ", favoriteMovies=" + favoriteMovies +
                ", ratedVideo=" + ratedVideo +
                '}';
    }
}
