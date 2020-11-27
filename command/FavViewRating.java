package command;

import application.Action;
import application.Rating;
import application.Serial;
import application.User;
import common.Constants;
import lists.Lists;

import java.util.ArrayList;

public class FavViewRating {
    private User u;
    private Action a;

    private Lists l;

    public FavViewRating(Action a, User u, Lists l) {
        this.a = a;
        this.u = u;
        this.l = l;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public Action getA() {
        return a;
    }

    public void setA(Action a) {
        this.a = a;
    }
    

    public String Commands(User u, Action a) {
        String message = "";
        switch (this.a.getType()) {
            case "favorite" -> {
                if (this.u.getHistory().get(this.a.getTitle()) == null) {
                    message = Constants.ERROR + this.a.getTitle() + " is not seen";
                    return message;
                }
                if (this.u.getFavoriteMovies().contains(a.getTitle())) {
                    message = Constants.ERROR + this.a.getTitle() + " is already in favourite list";
                    return message;
                }
                message = Constants.SUCCESS + this.a.getTitle() + " was added as favourite";
                if (!this.l.getUserList().contains(u)) {
                    u.getFavoriteMovies().add(this.a.getTitle());
                    this.l.getUserList().add(u);
                } else {
                    this.l.getUserList().remove(u);
                    u.getFavoriteMovies().add(this.a.getTitle());
                    this.l.getUserList().add(u);
                }
                return message;
            }
            case "view" -> {
                if (!this.l.getUserList().contains(u)) {
                    u.getHistory().putIfAbsent(this.a.getTitle(), 0);
                    this.l.getUserList().add(u);
                }
                int find = this.l.getUserList().indexOf(u);
                u.getHistory().put(this.a.getTitle(), ((this.u.getHistory().get(this.a.getTitle())) + 1));
                this.l.getUserList().remove(find);
                this.l.getUserList().add(find, u);
                message = Constants.SUCCESS + this.a.getTitle() + " was viewed with total views of " + (this.u.getHistory().get(this.a.getTitle()));
                return message;
            }
            case "rating" -> {
                if (this.u.getHistory().get(this.a.getTitle()) == null) {
                    message = Constants.ERROR + this.a.getTitle() + " is not seen";
                    return message;
                }
                Rating r = new Rating(a.getTitle(), a.getSeasonNumber(), a.getGrade(), a.getUsername());
                ArrayList<Integer> position = new ArrayList<Integer>();

                if (!this.l.getRatingList().isEmpty()) {
                    for (Integer i = 0; i < this.l.getRatingList().size(); i++) {
                        if (r.getUser().equals(this.l.getRatingList().get(i).getUser()))
                            position.add(i);
                    }
                    for (int i = 0; i < position.size(); i++) {
                        if (!r.getTitle().equals(this.l.getRatingList().get(position.get(i)).getUser()))
                            position.remove(i);
                    }
                    for (int i = 0; i < position.size(); i++) {
                        if (r.getSeason() != this.l.getRatingList().get(position.get(i)).getSeason())
                            position.remove(i);
                    }
                }
                if (a.getSeasonNumber() == 0) {
                    if (!position.isEmpty())
                        message = Constants.ERROR + this.a.getTitle() + " has been already rated";
                    else
                        message = Constants.SUCCESS + this.a.getTitle() + " was rated with " + this.a.getGrade() + " by " + this.a.getUsername();
                         this.l.getRatingList().add(r);

                } else {
                    if (!position.isEmpty())
                        message = Constants.ERROR + this.a.getTitle() + " has been already rated";
                    else {
                        message = Constants.SUCCESS + this.a.getTitle() + " was rated with " + this.a.getGrade() + " by " + this.a.getUsername();
                        this.l.getRatingList().add(r);
                    }
                }
            }
        }
        return message;
    }
}
