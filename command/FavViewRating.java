package command;

import application.Action;
import application.User;
import common.Constants;

public class FavViewRating {
    private User u;
    private Action a;

    public FavViewRating(Action a, User u){
        this.a = a;
        this.u = u;
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
                this.u.getFavoriteMovies().add(this.a.getTitle());
                return message;
            }
            case "view" -> {
                this.u.getHistory().putIfAbsent(this.a.getTitle(), 0);
                message = Constants.SUCCESS + this.a.getTitle() + " was viewed with total views of " + (this.u.getHistory().get(this.a.getTitle()) + 1);
                this.u.getHistory().replace(this.a.getTitle(), this.u.getHistory().get(this.a.getTitle()) + 1);
                return message;
            }
            case "rating" -> {
                if (this.u.getHistory().get(this.a.getTitle()) == null) {
                    message = Constants.ERROR + this.a.getTitle() + " is not seen";
                    return message;
                }
                if (a.getSeasonNumber() != 0) {
                    //Serial s = new Serial(this.a.getTitle(), this.a.getSeasonNumber());
                    //s.setRatings(this.a.getGrade() + s.getRatings());
                }
                message = Constants.SUCCESS + this.a.getTitle() + " was rated with " + this.a.getGrade() + " by " + this.a.getUsername();
            }
        }
        return message;
    }
}
