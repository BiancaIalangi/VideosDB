package application;

import java.util.Comparator;

public class ActorsSortingComparator implements Comparator<Actor> {

    @Override
    public int compare(Actor o1, Actor o2) {
        int RatingCompare;
        if (o1.getRating() < o2.getRating())
            RatingCompare = -1;
        else if (o1.getRating() > o2.getRating())
            RatingCompare = 1;
        else RatingCompare = 0;
        int TitleCompare = o1.getTitle().compareTo(o2.getTitle());

        if (RatingCompare == 0) {
            return ((TitleCompare == 0) ? RatingCompare : TitleCompare);
        } else {
            return RatingCompare;
        }
    }
}
