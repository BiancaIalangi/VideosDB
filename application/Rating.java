package application;

public class Rating {

    private String title;

    private int season;

    private double grade;

    private String user;

    public Rating(String title, int season, double grade, String user) {
        this.title = title;
        this.season = season;
        this.user = user;
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "title='" + title + '\'' +
                ", season=" + season +
                ", grade=" + grade +
                ", user='" + user + '\'' +
                '}';
    }
}
