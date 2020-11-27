package application;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class Action {

    private String actionType;

    private String type;

    private String username;

    private String objectType;

    private String sortType;

    private String criteria;

    private String title;

    private String genre;

    private int number;

    private double grade;

    private int seasonNumber;

    private List<List<String>> filters = new ArrayList<>();

    /*public Action (String actionType, String type, String username, String objectType, String sortType, String criteria,
                   String title, String genre, int number, double grade, int seasonNumber, List<List<String>> filters) {
        this.actionType = actionType;
        this.type = type;
        this
    }*/
    public Action (String username) {
        this.username = username;
    }

    public Action (String username, double grade, int seasonNumber) {
        this.grade = grade;
        this.seasonNumber = seasonNumber;
    }

    public Action(ActionInputData action) {
        this.actionType = action.getActionType();
        this.type = action.getType();
        this.username = action.getUsername();
        this.objectType = action.getObjectType();
        this.sortType = action.getSortType();
        this.criteria = action.getCriteria();
        this.title = action.getTitle();
        this.genre = action.getGenre();
        this.number = action.getNumber();
        this.grade = action.getGrade();
        this.seasonNumber = action.getSeasonNumber();
        this.filters = action.getFilters();
    }

    public int findUser(List<UserInputData> action) {
        for (int i = 0; i < action.size(); i++) {
            if (this.getUsername().equals(action.get(i).getUsername()))
                return i;
        }
        return 0;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public List<List<String>> getFilters() {
        return filters;
    }

    public void setFilters(List<List<String>> filters) {
        this.filters = filters;
    }

}
