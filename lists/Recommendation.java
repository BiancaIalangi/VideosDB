package lists;

import application.Movie;
import application.Serial;
import application.Show;
import application.User;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recommendation extends Query {
    private Query l;
    private List<MovieInputData> movieList;
    private List<SerialInputData> serialList;
    private Map<String, Integer> genreTimes = new HashMap<>();
    private List<String> listByGenres = new ArrayList<>();
    private List<Show> titleShow = new ArrayList<>();

    /**
     * setter for recommendation
     * @param lists list of lists with users, movies and serial
     * @param movieList list with movies
     * @param serialList list with serials
     */
    public Recommendation(final Query lists, final List<MovieInputData> movieList,
                          final List<SerialInputData> serialList) {
        this.l = lists;
        this.movieList = movieList;
        this.serialList = serialList;
    }

    /**
     * add all movies and serials list titleShow
     */
    public void addShow() {
        int position = 0;

        for (MovieInputData movie : movieList) {
            position++;
            Movie m = l.getByTitleMovie(movie.getTitle());
            Show show = new Show(m.getTitle(), m.getGenres(), m.getGeneralRatingMovie(),
                    m.getTotalViews(), position);
            titleShow.add(show);
        }

        for (SerialInputData serial : serialList) {
            position++;
            Serial s = l.getByTitleSerial(serial.getTitle());
            Show show = new Show(s.getTitle(), s.getGenres(),
                    s.getGeneralRatingSerial(), s.getTotalViews(), position);
            titleShow.add(show);
        }
    }

    /**
     *
     * @param name user's name
     * @return the title of show for standard recommendation
     */
    public String standardRecommend(final String name) {
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

    /**
     *
     * @param name user's name
     * @return the title of show for best_unseen recommendation
     */
    public String bestSeenRecommend(final String name) {
        addShow();
        List<Show> byRating = new ArrayList<>(titleShow);

        String message = null;
        User u = l.getByUsername(name);

        Comparator<Show> comparator1 = Comparator.comparing(Show::getPosition);
        byRating.sort(comparator1);
        Comparator<Show> comparator = Comparator.comparing(Show::getRating);
        byRating.sort(comparator.reversed());

        for (Show r : byRating) {
            if (!u.getHistory().containsKey(r.getTitle())) {
                message = r.getTitle();
                break;
            }
        }
        return message;
    }

    /**
     *
     * @param name user's name
     * @param genre type of genre wanted
     * @return titles of shows for search recommendation
     */
    public  ArrayList<String> searchRecommend(final String name, final String genre) {
        addShow();
        List<Show> byRating = new ArrayList<>(titleShow);

        ArrayList<String> message = new ArrayList<>();
        User u = l.getByUsername(name);

        if (!u.getSubscriptionType().equals("PREMIUM")) {
            return null;
        }

        Comparator<Show> comparator = Comparator.comparing(Show::getRating);
        byRating.sort(comparator);
        comparator = comparator.thenComparing(Show::getTitle);
        Stream<Show> generate = byRating.stream().sorted(comparator);

        List<Show> sortedRating = generate.collect(Collectors.toList());
        for (Show r : sortedRating) {
            if (!u.getHistory().containsKey(r.getTitle())) {
               for (String g : r.getGenre()) {
                   if (g.equals(genre)) {
                       message.add(r.getTitle());
                   }
               }
            }
        }
        return message;
    }

    /**
     * in how many favoriteList is a show
     */
    public void generateTimesInFavorite() {
        addShow();

        for (Show s : titleShow) {
            for (User u : l.getUserList()) {
                if (u.getFavoriteMovies().contains(s.getTitle())) {
                    s.increaseTimesInFavorite();
                }
            }
        }
    }


    /**
     *
     * @param name user's name
     * @return title of show for favorite recommendation
     */
    public String favCommend(final String name) {
        generateTimesInFavorite();
        List<Show> byFav = new ArrayList<>(titleShow);

        String message = null;
        User u = l.getByUsername(name);

        if (!u.getSubscriptionType().equals("PREMIUM")) {
            return null;
        }

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

    /**
     *
     * @param genreTimes the hashmap with genre and how many
     *                   movies have that genre
     * @return sorted hashmap by values
     */
    private Map<String, Integer> sortByValue(final HashMap<String, Integer> genreTimes) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(genreTimes.entrySet());

        list.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            temp.put(entry.getKey(), entry.getValue());
        }
        return temp;
    }

    /**
     * create the hashmap that show how many
     * movies have a certain type of genre
     */
    public void generateHashMap() {
        addShow();
        HashMap<String, Integer> getInHashMap = new HashMap<>();

        for (Show s : titleShow) {
            for (String g : s.getGenre()) {
                getInHashMap.putIfAbsent(g, 0);
                getInHashMap.put(g, getInHashMap.get(g) + 1);
            }
        }

        genreTimes = sortByValue(getInHashMap);

        for (Map.Entry<String, Integer> entry : genreTimes.entrySet()) {
            listByGenres.add(entry.getKey());
        }
        Collections.reverse(listByGenres);
    }


    /**
     *
     * @param name user's name
     * @return title of show for popular recommendation
     */
    public String popularCommend(final String name) {
        addShow();
        generateHashMap();

        String message = null;
        User u = l.getByUsername(name);

        if (!u.getSubscriptionType().equals("PREMIUM")) {
            return null;
        }

        for (String g : listByGenres) {
            for (Show r : titleShow) {
                if (r.getGenre().contains(g)) {
                    if (!u.getHistory().containsKey(r.getTitle())) {
                        message = r.getTitle();
                        break;
                    }
                }
            }
            if (message != null) {
                break;
            }
        }

        return message;
    }

}
