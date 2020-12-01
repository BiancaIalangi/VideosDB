package application;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private final String username;

    private final String subscriptionType;

    private final Map<String, Integer> history;

    private final ArrayList<String> favoriteMovies;

    private final Map<String, Double> ratedMovies = new HashMap<>();

    private final Map<String, List<Integer>> ratedSerial = new HashMap<>();

    private int activity;

    /**
     * setter of user entity (a copy of UserInputData)
     * @param user to be copied
     */
    public User(final UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.history = user.getHistory();
        this.favoriteMovies = user.getFavoriteMovies();
    }

    /**
     * getter
     * @return user's name
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter
     * @return how many show's rated
     */
    public int getActivity() {
        return activity;
    }

    /**
     * every time a show is rated by this user
     * this value increase
     */
    public void increaseActivity() {
        ++activity;
    }

    /**
     * getter
     * @return the subscription type of this user
     */
    public String getSubscriptionType() {
        return subscriptionType;
    }

    /**
     * getter
     * @return the history of shows watched by this user
     */
    public Map<String, Integer> getHistory() {
        return history;
    }

    /**
     *
     * @param title movie's title
     * @param grade the grade gave by this user
     */
    public void addHasRatedMovie(final String title, final Double grade) {
        ratedMovies.put(title, grade);
    }

    /**
     * getter
     * @return a list with user's favorite movies
     */
    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    /**
     *
     * @param title serial's title
     * @param nr seson's number
     * @return if this user rated season "nr" of "title" serial
     */
    public int didRateShowS(final String title, final int nr) {

        if (!ratedSerial.containsKey(title)) {
            ratedSerial.put(title, new ArrayList<>());
        } else if (ratedSerial.get(title).contains(nr)) {
            return 0;
        }

        ratedSerial.get(title).add(nr);
        return 1;
    }

    /**
     *
     * @param title movie's title
     * @return if this user rated "title" movie
     */
    public int didRateMovie(final String title) {

        for (Map.Entry<String, Double> s : ratedMovies.entrySet()) {
            if (s.getKey().equals(title)) {
                return 0;
            }
        }
        return 1;
    }

    /**
     *
     * @param title show's title
     * @return if "title" is already in user's
     * list of FavoriteShow in case he want to put
     * it again in his Favorite list
     */
    public int addFavoriteList(final String title) {
        for (String favoriteMovie : favoriteMovies) {
            if (favoriteMovie.equals(title)) {
                return 1;
            }
        }
        return 0;
    }
}
