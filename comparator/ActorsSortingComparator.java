package comparator;

import application.Actor;

import java.util.Comparator;

public class ActorsSortingComparator implements Comparator<Actor> {

    @Override
    public int compare(Actor o1, Actor o2) {
        int RatingCompare = Double.compare(o1.getRating(), o2.getRating());
        int TitleCompare = o1.getTitle().compareTo(o2.getTitle());

        if (RatingCompare == 0) {
            return ((TitleCompare == 0) ? RatingCompare : TitleCompare);
        } else {
            return RatingCompare;
        }
    }
}
