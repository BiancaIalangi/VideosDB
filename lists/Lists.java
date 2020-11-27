package lists;

import application.Rating;
import application.Serial;
import application.User;

import java.util.List;

public class Lists {

    private List<User> userList;
    private List<Serial> serialList;
    private List<Rating> ratingList;

    public Lists (List<User> userList, List<Serial> serialList, List<Rating> ratingList) {
        this.userList = userList;
        this.ratingList = ratingList;
        this.serialList = serialList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Serial> getSerialList() {
        return serialList;
    }

    public void setSerialList(List<Serial> serialList) {
        this.serialList = serialList;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    @Override
    public String toString() {
        return "Lists{" +
                "ratingList=" + ratingList +
                '}';
    }
}
