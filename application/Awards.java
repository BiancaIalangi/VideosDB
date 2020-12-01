package application;

public class Awards {

    private final String name;

    private final int numberOfAwards;

    /**
     * setter of Awards - an object that keep how many awards
     * of a certain type are
     * @param name the name of the award
     * @param numberOfAwards how many awards of the name type are
     */
    public Awards(final String name, final int numberOfAwards) {
        this.name = name;
        this.numberOfAwards = numberOfAwards;
    }

    /**
     * getter of getName
     * @return the name of award
     */
    public String getName() {
        return name;
    }

    /**
     * getter of number of awards
     * @return the number of a certain type of award
     */
    public int getNumberOfAwards() {
        return numberOfAwards;
    }

}
