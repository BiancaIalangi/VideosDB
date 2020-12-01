package application;

public class Awards {
    private String name;
    private int numberOfAwards;

    public Awards(String name, int numberOfAwards) {
        this.name = name;
        this.numberOfAwards = numberOfAwards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfAwards() {
        return numberOfAwards;
    }

    public void setNumberOfAwards(int numberOfAwards) {
        this.numberOfAwards = numberOfAwards;
    }

    @Override
    public String toString() {
        return "Awards{" +
                "name='" + name + '\'' +
                ", numberOfAwards=" + numberOfAwards +
                '}';
    }
}
