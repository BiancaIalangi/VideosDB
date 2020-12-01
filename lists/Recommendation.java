package lists;

import application.Movie;
import application.Serial;
import application.Show;
import application.User;
import comparator.ShowSortingCompare;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recommendation extends Lists{
    private List<Show> titleShow = new ArrayList<>();
    private Lists l;
    private List<MovieInputData> movieList;
    private List<SerialInputData> serialList;
    private Map<String, Integer> genreTimes = new HashMap<>();
    private List<String> listByGenres = new ArrayList<>();

    public Recommendation(Lists lists, List<MovieInputData> movieList, List<SerialInputData> serialList) {
        this.l = lists;
        this.movieList = movieList;
        this.serialList = serialList;
    }

    public void addShow() {
        int position = 0;
        for (MovieInputData movie : movieList) {
            position++;
            Movie m = l.getByTitleMovie(movie.getTitle());
            m.generalRating();
            Show show = new Show(m.getTitle(), m.getGenres(), m.getGeneralRatingMovie(), m.getTotalViews(), position);
            titleShow.add(show);
        }

        for (SerialInputData serial : serialList) {
            position++;
            Serial s = l.getByTitleSerial(serial.getTitle());
            s.generalRating();
            Show show = new Show(s.getTitle(), s.getGenres(), s.getGeneralRatingSerial() , s.getTotalViews(), position);
            titleShow.add(show);
        }
    }

    public String standardRecommend(String name) {
        addShow();

        String message = null;
        User u = l.getByUsername(name);

        for (Show t : titleShow) {
            if (!u.getHistory().containsKey(t.getTitle())) {
                message = t.getTitle();
                break;
            }

        }
        return message;
    }

    public String bestSeenRecommend(String name) {
        addShow();
        List<Show> byRating = new ArrayList<>(titleShow);

        String message = null;
        User u = l.getByUsername(name);

        byRating.sort(new ShowSortingCompare());

        for (Show r : byRating) {
            if (!u.getHistory().containsKey(r.getTitle())) {
                message = r.getTitle();
                break;
            }
        }
        return message;
    }

    public  ArrayList<String> searchRecommend(String name, String genre) {
        addShow();
        List<Show> byRating = new ArrayList<>(titleShow);

        ArrayList<String> message = new ArrayList<>();
        User u = l.getByUsername(name);

        if (!u.getSubscriptionType().equals("PREMIUM"))
            return null;

        Comparator<Show> comparator = Comparator.comparing(Show::getRating);
        byRating.sort(comparator);
        comparator = comparator.thenComparing(Show::getTitle);
        Stream<Show> generate = byRating.stream().sorted(comparator);

        List<Show> sortedRating = generate.collect(Collectors.toList());
        for (Show r : sortedRating) {
            if (!u.getHistory().containsKey(r.getTitle())) {
               for (String g : r.getGenre())
                   if (g.equals(genre))
                       message.add(r.getTitle());
            }
        }
        return message;
    }

    public void generateTimesInFavorite() {
        addShow();

        for (Show s : titleShow)
            for (User u : l.getUserList())
                if (u.getFavoriteMovies().contains(s.getTitle()))
                    s.increaseTimesInFavorite();
    }


    public String favCommend (String name) {

        generateTimesInFavorite();
        List<Show> byFav = new ArrayList<>(titleShow);

        String message = null;
        User u = l.getByUsername(name);

        if (!u.getSubscriptionType().equals("PREMIUM"))
            return null;

        Comparator<Show> comparator1 = Comparator.comparing(Show::getPosition);
        byFav.sort(comparator1);
        Comparator<Show> comparator = Comparator.comparing(Show::getTimesInFavorite);
        byFav.sort(comparator.reversed());


        for (Show r : byFav) {
            if (!u.getHistory().containsKey(r.getTitle())) {
                message = r.getTitle();
                break;
            }
        }
        return message;
    }

    private Map<String, Integer> sortByValue(HashMap<String, Integer> genreTimes) {
        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(genreTimes.entrySet());

        list.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void generateHashMap () {
        addShow();
        HashMap <String, Integer> getInHashMap = new HashMap<>();

        for (Show s : titleShow)
            for (String g : s.getGenre()) {
                getInHashMap.putIfAbsent(g, 0);
                getInHashMap.put(g, getInHashMap.get(g) + 1);
            }

        genreTimes = sortByValue(getInHashMap);

        for (Map.Entry<String, Integer> entry : genreTimes.entrySet())
            listByGenres.add(entry.getKey());

        Collections.reverse(listByGenres);
//        for (String s : listByGenres)
//            System.out.println(s);
    }


    public String popularCommend (String name) {
        addShow();
        generateHashMap();

//        for (Show s : titleShow)
//            System.out.println(s.getTitle() + " " + s.getPosition());

        String message = null;
        User u = l.getByUsername(name);

        if (!u.getSubscriptionType().equals("PREMIUM"))
            return null;

        for (String g : listByGenres) {
            for (Show r : titleShow)
                if (r.getGenre().contains(g))
                    if (!u.getHistory().containsKey(r.getTitle())) {
                        message = r.getTitle();
                        break;
                    }
            if (message != null)
                break;
        }


        return message;
    }



}
