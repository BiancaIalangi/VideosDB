package application;

import fileio.ActionInputData;
import fileio.UserInputData;

import java.util.List;

public class Action {

    private String actionType;

    private String type;

    private String username;

    private double grade;

    private int seasonNumber;

    /**
     * setter for action entity (a copy from ActionInputData)
     * @param action to be copied
     */
    public Action(final ActionInputData action) {
        this.actionType = action.getActionType();
        this.type = action.getType();
        this.username = action.getUsername();
        this.grade = action.getGrade();
        this.seasonNumber = action.getSeasonNumber();
    }

    /**
     * setter for username
     * @param username to be set
     */
    public Action(final String username) {
        this.username = username;
    }

    /**
     * setter for grade and seasonNumber
     * @param grade to be set
     * @param seasonNumber to be set
     */
    public Action(final double grade, final int seasonNumber) {
        this.grade = grade;
        this.seasonNumber = seasonNumber;
    }

    /**
     * find certain user
     * @param action list of User (a copy from UserInputData)
     * @return position of user needed
     */
    public int findUser(final List<UserInputData> action) {
        for (int i = 0; i < action.size(); i++) {
            if (this.getUsername().equals(action.get(i).getUsername())) {
                return i;
            }
        }
        return 0;
    }

    /**
     * getter for actionType
     * @return actionType
     */
    public String getActionType() {
        return actionType;
    }


    /**
     * getter for commands
     * @return command type
     */
    public String getType() {
        return type;
    }

    /**
     * getter for username
     * @return name of user
     */
    public String getUsername() {
        return username;
    }

}
