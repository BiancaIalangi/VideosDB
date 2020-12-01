package comparator;

import application.Actor;

import java.util.Comparator;

public class ActorsSortingComparator implements Comparator<Actor> {

    @Override
    public int compare(Actor o1, Actor o2) {
        int RatingCompare = Double.compare(o1.getRating(), o2.getRating());
        int NameCompare = o1.getName().compareTo(o2.getName());

        if (RatingCompare == 0) {
            return ((NameCompare == 0) ? RatingCompare : NameCompare);
        } else {
            return RatingCompare;
        }
    }
}