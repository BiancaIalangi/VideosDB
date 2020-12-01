package application;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.Utils.stringToAwards;

public class ActorInfo {

    private String name;

    private String careerDescription;

    private ArrayList<String> filmography;

    private Map<ActorsAwards, Integer> awards;

    public ActorInfo (ActorInputData a) {
        this.name = a.getName();
        this.careerDescription = a.getCareerDescription();
        this.filmography = a.getFilmography();
        this.awards = a.getAwards();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public int numberOfAwards () {
        int s = 0;
        for (Integer r : awards.values())
            s += r;
        return s;
    }

    public int containAwards (List<String> trophee) {
        for (String t : trophee) {
            if (!awards.containsKey(stringToAwards(t)))
                return 0;
        }
        return 1;
    }

}
