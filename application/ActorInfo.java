package application;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.Utils.stringToAwards;

public class ActorInfo {

    private final String name;

    private final String careerDescription;

    private final ArrayList<String> filmography;

    private final Map<ActorsAwards, Integer> awards;

    /**
     * setter of ActorInfo (a copy of ActorInputData
     * @param a actor to be copied
     */
    public ActorInfo(final ActorInputData a) {
        this.name = a.getName();
        this.careerDescription = a.getCareerDescription();
        this.filmography = a.getFilmography();
        this.awards = a.getAwards();
    }

    /**
     * getter of name actor
     * @return actor's name
     */
    public String getName() {
        return name;
    }

    /**
     * getter of Career Description
     * @return Descripton of Actor's Career
     */
    public String getCareerDescription() {
        return careerDescription;
    }

    /**
     * getter of Actor's Filmography
     * @return Actor's Filmography
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }

    /**
     * getter of get Awards
     * @return the name of Awards and how many there are
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     * getter of all awards he had
     * @return the number of all awards he won
     */
    public int numberOfAwards() {
        int s = 0;
        for (Integer r : awards.values()) {
            s += r;
        }
        return s;
    }

    /**
     * check if he won a certain trophy
     * @param trophy arraylist with types of awards
     * @return 0 if he didn't win a those trophy, 1 if he won
     */
    public int containAwards(final List<String> trophy) {
        for (String t : trophy) {
            if (!awards.containsKey(stringToAwards(t))) {
                return 0;
            }
        }
        return 1;
    }

}
