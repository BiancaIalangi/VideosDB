package command;

import application.*;
import common.Constants;
import fileio.ActionInputData;
import lists.Lists;

import java.util.List;
import java.util.Map;

public class FavViewRating {
    private Lists l;

    public FavViewRating(Lists l) {
        this.l = l;
    }


    public String Commands(ActionInputData a, List<Movie> m, List<Serial> s) {
        String message = "";
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
                if (this.l.getByUsername(a.getUsername()).addFavoriteList(a.getTitle()) == 0)
                    this.l.getByUsername(a.getUsername()).getFavoriteMovies().add(a.getTitle());
                return message;
            }
            case "view" -> {
                this.l.getByUsername(a.getUsername()).getHistory().putIfAbsent(a.getTitle(), 0);
                this.l.getByUsername(a.getUsername()).getHistory().put(a.getTitle(), (this.l.getByUsername(a.getUsername()).getHistory().get(a.getTitle()) + 1));
                message = Constants.SUCCESS + a.getTitle() + " was viewed with total views of " + (this.l.getByUsername(a.getUsername()).getHistory().get(a.getTitle()));
                return message;
            }
            case "rating" -> {
                if (u.getHistory().get(a.getTitle()) == null) {
                    message = Constants.ERROR + a.getTitle() + " is not seen";
                    return message;
                }
                for (Map.Entry<String, Integer> entry : u.getHistory().entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    if (key.equals(a.getTitle())) {
                        if (a.getSeasonNumber() == 0) {
                            if (this.l.getByUsername(a.getUsername()).didRateMovie(key) == 1) {
                                this.l.getByUsername(a.getUsername()).addHasRatedMovie(key);
                                l.getByTitleMovie(key).increaseDivider();
                                l.getByTitleMovie(key).addRate(a.getGrade());
                                message = Constants.SUCCESS + a.getTitle() + " was rated with " + a.getGrade() + " by " + a.getUsername();
                            } else message = Constants.ERROR + a.getTitle() + " has been already rated";
                        } else {
                            if (this.l.getByUsername(a.getUsername()).didRateShowS(a.getTitle(), a.getSeasonNumber()) == 1) {
                                this.l.getByUsername(a.getUsername()).addRatedSerial(a.getTitle(), a.getSeasonNumber());
                                l.getByTitleSerial(a.getTitle()).addRatedSeasons(a.getUsername(), a.getGrade());
                                message = Constants.SUCCESS + a.getTitle() + " was rated with " + a.getGrade() + " by " + a.getUsername();
                            } else message = Constants.ERROR + a.getTitle() + " has been already rated";
                        }
                    }
                }
                return message;
            }
        }
        return null;
    }
}
