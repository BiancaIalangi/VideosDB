package command;

import application.User;
import common.Constants;
import fileio.ActionInputData;
import lists.Query;

import java.util.Map;

public class FavViewRating {
    private final Query l;

    public FavViewRating(final Query l) {
        this.l = l;
    }

    /**
     *
     * @param a the action entity needed to perform
     * @return if the action was performed
     */
    public String commandFavViewRating(final ActionInputData a) {
        String message;
        User u = this.l.getByUsername(a.getUsername());

        switch (a.getType()) {
            case "favorite" -> {
                if (u.getHistory().get(a.getTitle()) == null) {

                    message = Constants.ERROR + a.getTitle() + " is not seen";
                    return message;
                }
                if (u.getFavoriteMovies().contains(a.getTitle())) {

                    message = Constants.ERROR + a.getTitle() + " is already in favourite list";
                    return message;
                }

                message = Constants.SUCCESS + a.getTitle() + " was added as favourite";

                if (this.l.getByUsername(a.getUsername()).addFavoriteList(a.getTitle()) == 0) {
                    this.l.getByUsername(a.getUsername()).getFavoriteMovies().add(a.getTitle());
                }

                if (l.getByTitleMovie(a.getTitle()) != null) {
                    l.getByTitleMovie(a.getTitle()).increaseTimesInFavoriteMovies();
                } else  {
                    l.getByTitleSerial(a.getTitle()).increaseTimesInFavoriteSerial();
                }

                return message;
            }
            case "view" -> {

                this.l.getByUsername(a.getUsername()).getHistory().putIfAbsent(a.getTitle(), 0);
                this.l.getByUsername(a.getUsername()).getHistory().put(a.getTitle(),
                        (this.l.getByUsername(a.getUsername()).getHistory().get(a.getTitle()) + 1));

                message = Constants.SUCCESS + a.getTitle() + " was viewed with total views of "
                        + (this.l.getByUsername(a.getUsername()).getHistory().get(a.getTitle()));
                return message;
            }
            case "rating" -> {

                if (u.getHistory().get(a.getTitle()) == null) {
                    message = Constants.ERROR + a.getTitle() + " is not seen";
                    return message;
                }
                for (Map.Entry<String, Integer> entry : u.getHistory().entrySet()) {
                    String key = entry.getKey();
                    if (key.equals(a.getTitle())) {
                        if (a.getSeasonNumber() == 0) {
                            if (l.getByUsername(a.getUsername()).didRateMovie(key) == 1) {

                                l.getByUsername(
                                        a.getUsername()).addHasRatedMovie(key, a.getGrade());
                                l.getByTitleMovie(key).increaseDivider();
                                l.getByTitleMovie(key).addRate(a.getGrade());
                                l.getByUsername(a.getUsername()).increaseActivity();

                                message = Constants.SUCCESS
                                        + a.getTitle() + " was rated with "
                                        + a.getGrade() + " by " + a.getUsername();
                            } else {
                                message = Constants.ERROR
                                        + a.getTitle() + " has been already rated";
                            }
                        } else {
                            if (l.getByUsername(a.getUsername()).didRateShowS(
                                    a.getTitle(), a.getSeasonNumber()) == 1) {

                                l.getByTitleSerial(a.getTitle()).addRatedSeasons(
                                        a.getUsername(), a.getGrade());
                                l.getByUsername(a.getUsername()).increaseActivity();

                                message = Constants.SUCCESS
                                        + a.getTitle()
                                        + " was rated with " + a.getGrade()
                                        + " by " + a.getUsername();
                            } else {
                                message = Constants.ERROR
                                        + a.getTitle() + " has been already rated";
                            }
                        }
                        return message;
                    }
                }
            }
        }
        return null;
    }
}
