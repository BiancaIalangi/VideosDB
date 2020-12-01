package lists;

import application.*;
import entertainment.Genre;
import fileio.*;

import java.util.*;


public class Query {

    private final List<User> userList;
    private final List<Movie> movieList;
    private final List<Serial> serialList;
    private final List<ActorInfo> actorsInfoList;

    /**
     * create a list of lists that copy all the database
     */
    public Query() {
        this.userList = new ArrayList<>();
        this.serialList = new ArrayList<>();
        this.movieList = new ArrayList<>();
        this.actorsInfoList = new ArrayList<>();
    }

    /**
     * getter
     * @return a list of users
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * getter
     * @return a list of movies
     */
    public List<Movie> getMovieList() {
        return movieList;
    }

    /**
     * create a copy of UserInputData
     * @param u a list of users
     */
    public void makeUsers(final List<UserInputData> u) {
        for (UserInputData userInputData : u) {
            User usr = new User(userInputData);
            userList.add(usr);
        }
    }

    /**
     * create a copy of SerialInputData
     * @param s  a list of serials
     */
    public void makeSerial(final List<SerialInputData> s) {
        for (SerialInputData serialInputData : s) {
            Serial ser = new Serial(serialInputData);
            serialList.add(ser);
        }
    }

    /**
     * create a copy of MovieInputData
     * @param m a list of movies
     */
    public void makeMovies(final List<MovieInputData> m) {
        for (MovieInputData movieInputData : m) {
            Movie mov = new Movie(movieInputData);
            movieList.add(mov);
        }
    }

    /**
     * create a copy of ActorInputData
     * @param a a list of actors
     */
    public void makeActorsInfo(final List<ActorInputData> a) {
        for (ActorInputData actorInputData : a) {
            ActorInfo act = new ActorInfo(actorInputData);
            actorsInfoList.add(act);
        }
    }

    /**
     *
     * @param title of movie searched
     * @return the whole info about movie
     */
    public Movie getByTitleMovie(final String title) {
        for (Movie movie : this.movieList) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    /**
     *
     * @param name of user searched
     * @return the whole info about user
     */
    public User getByUsername(final String name) {
        for (User user : this.userList) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     *
     * @param title of serial searched
     * @return the whole info about serial
     */
    public Serial getByTitleSerial(final String title) {
        for (Serial serial : this.serialList) {
            if (serial.getTitle().equals(title)) {
                return serial;
            }
        }
        return null;
    }

    /**
     * generate generalRating for all shows
     * sort cast from a to z from all shows
     */
    public void generalRatingAsc() {
        for (Serial serial : serialList) {
            serial.generalRating();
            serial.sortCastAsc();
        }

        for (Movie movie : movieList) {
            movie.generalRating();
            movie.sortCastAsc();
        }

    }

    /**
     * generate generalRating for all shows
     *  sort cast from z to a from all shows
     */
    public void generalRatingDesc() {
        for (Serial serial : serialList) {
            serial.generalRating();
            serial.sortCastDesc();
        }

        for (Movie  movie : movieList) {
            movie.generalRating();
            movie.sortCastDesc();
        }
    }

    /**
     *
     * @param a the list of all actors
     * @param name name of actos
     * @return if the actors is in actor list or not
     */
    public int existActor(final ArrayList<Actor> a, final String name) {
        int find = -1;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getName().equals(name)) {
                find = i;
            }
        }
        return find;
    }

    /**
     *
     * @return general rating for every actor
     * from all shows
     */
    public ArrayList<Actor> ordRating() {
        generalRatingAsc();
        ArrayList<Actor> actorList = new ArrayList<>();
        for (Serial serial : serialList) {
            for (int i = 0; i < serial.getCast().size(); i++) {
                if (serial.getGeneralRatingSerial() != 0) {
                    if (existActor(actorList,
                            serial.getCast().get(i)) != -1) {
                        actorList.get(existActor(actorList,
                                serial.getCast().get(i))).incrementTimesin();
                        actorList.get(existActor(actorList,
                                serial.getCast().get(i))).setRating(
                                        serial.getGeneralRatingSerial());
                    } else {
                        Actor a = new Actor(serial.getGeneralRatingSerial(),
                                serial.getCast().get(i));
                        actorList.add(a);
                    }
                }
            }
        }

        for (Movie m : movieList) {
            for (int i = 0; i < m.getCast().size(); i++) {
                if (m.getGeneralRatingMovie() != 0) {
                    if (existActor(actorList, m.getCast().get(i)) != -1) {
                        actorList.get(existActor(actorList, m.getCast().get(i))).incrementTimesin();
                        actorList.get(existActor(actorList,
                                m.getCast().get(i))).setRating(m.getGeneralRatingMovie());
                    } else {
                        Actor a = new Actor(m.getGeneralRatingMovie(), m.getCast().get(i));
                        actorList.add(a);
                    }
                }
            }
        }

        for (Actor actor : actorList) {
            actor.finalRating();
        }
        return actorList;
    }

    /**
     *
     * @param actorList the database of all actors
     * @param n how actors need to show
     * @param type if is asc or desc
     * @return the first(or last) n actors by Rating
     */
    public ArrayList<String> actorsAverage(final ArrayList<Actor> actorList,
                                           final int n, final String type) {
        Comparator<Actor> comparator = Comparator.comparing(Actor::getName);
        actorList.sort(comparator);
        Comparator<Actor> comparator1 = Comparator.comparing(Actor::getRating);
        actorList.sort(comparator1);

        if (type.equals("desc")) {
            generalRatingDesc();          //reverse la castul ordonat alfabetic
            Collections.reverse(actorList);
        }

        ArrayList<String> a = new ArrayList<>();

        for (Actor actor : actorList) {
            a.add(actor.getName());
        }

        while (a.size() > n) {
            a.remove(a.size() - 1);
        }

        return a;
    }

    /**
     *
     * @param awards the list of awards that an actors needs to have
     * @param type if is asc or desc
     * @return the list of actors that have the awords
     */
    public ArrayList<Awards> queryAwards(final List<String> awards, final String type) {
        ArrayList<Awards> list = new ArrayList<>();
        for (ActorInfo actor : actorsInfoList) {
            if (actor.getAwards().size() < awards.size()) {
                continue;
            } else if (actor.containAwards(awards) == 0) {
                continue;
            } else {
                int s = actor.numberOfAwards();
                Awards a = new Awards(actor.getName(), s);
                list.add(a);
            }
        }

        return list;
    }

    /**
     *
     * @param n how many movies need to show
     * @param year filter
     * @param genre filter
     * @param type asc or desc
     * @return the first(or last) n movies by rating with this filters
     */
    public ArrayList<String> ratingMovies(final int n, final String year,
                                          final Genre genre, final String type) {
        ArrayList<String> ratingMovieList = new ArrayList<>();

        generalRatingAsc();

        Comparator<Movie> comparator = Comparator.comparing(Movie::getTitle);
        movieList.sort(comparator);
        Comparator<Movie> comparator1 = Comparator.comparing(Movie::getGeneralRatingMovie);
        movieList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(movieList);
        }

        for (Movie m : movieList) {
            if (m.checkFiltersMovie(year, genre) == 1) {
                if (m.getGeneralRatingMovie() != 0) {
                    ratingMovieList.add(m.getTitle());
                }
            }
        }

        while (ratingMovieList.size() > n) {
            ratingMovieList.remove(ratingMovieList.size() - 1);
        }

        return ratingMovieList;
    }

    /**
     *
     * @param n how many serials need to show
     * @param year filter
     * @param genre filter
     * @param type asc or desc
     * @return the first (or last) n serials with this filters
     */
    public ArrayList<String> ratingSerial(final int n, final String year,
                                          final Genre genre, final String type) {
        ArrayList<String> ratingSerialList = new ArrayList<>();

        generalRatingAsc();

        Comparator<Serial> comparator = Comparator.comparing(Serial::getTitle);
        serialList.sort(comparator);
        Comparator<Serial> comparator1 = Comparator.comparing(Serial::getGeneralRatingSerial);
        serialList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(serialList);
        }

        for (Serial s : serialList) {
            if (s.checkFiltersSerial(year, genre) == 1) {
                if (s.getGeneralRatingSerial() != 0) {
                    ratingSerialList.add(s.getTitle());
                }
            }
        }

        while (ratingSerialList.size() > n) {
            ratingSerialList.remove(ratingSerialList.size() - 1);
        }

        return ratingSerialList;
    }

    /**
     *
     * @param n how many movies need to show
     * @param year filter
     * @param genre filter
     * @param type asc or desc
     * @return the most common n movies in favoritelist's users with this filters
     */
    public ArrayList<String> favoriteMovies(final int n, final String year,
                                            final Genre genre, final String type) {
        ArrayList<String> favoriteMovieList = new ArrayList<>();
        generalRatingAsc();

        for (Movie m : movieList) {
            m.iterateFavoriteMovie(userList);
        }

        Comparator<Movie> comparator = Comparator.comparing(Movie::getTitle);
        movieList.sort(comparator);
        Comparator<Movie> comparator1 = Comparator.comparing(Movie::getTimesInFavoriteMovies);
        movieList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(movieList);
        }

        for (Movie m : movieList) {
            if (m.checkFiltersMovie(year, genre) == 1) {
                if (m.getTimesInFavoriteMovies() != 0) {
                    favoriteMovieList.add(m.getTitle());
                }
            }
        }

        while (favoriteMovieList.size() > n) {
            favoriteMovieList.remove(favoriteMovieList.size() - 1);
        }

        return favoriteMovieList;
    }

    /**
     *
     * @param n how many serials need to show
     * @param year filter
     * @param genre filter
     * @param type asc or desc
     * @return the most common n serials in favoritelist's users with this filters
     */
    public ArrayList<String> favoriteSerial(final int n, final String year,
                                            final Genre genre, final String type) {
        ArrayList<String> favoriteSerialList = new ArrayList<>();
        generalRatingAsc();

        for (Serial s : serialList) {
            s.iterateFavoriteSerial(userList);
        }

        Comparator<Serial> comparator = Comparator.comparing(Serial::getTitle);
        serialList.sort(comparator);
        Comparator<Serial> comparator1 = Comparator.comparing(Serial::getTimesInFavoriteSerial);
        serialList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(serialList);
        }

        for (Serial s : serialList) {
            if (s.checkFiltersSerial(year, genre) == 1) {
                if (s.getTimesInFavoriteSerial() != 0) {
                    favoriteSerialList.add(s.getTitle());
                }
            }
        }

        while (favoriteSerialList.size() > n) {
            favoriteSerialList.remove(favoriteSerialList.size() - 1);
        }

        return favoriteSerialList;
    }

    /**
     *
     * @param n how many movies need to show
     * @param year filter
     * @param genre filter
     * @param type asc or desc
     * @return the longest movies (as duration) with this filters
     */
    public ArrayList<String> longestMovies(final int n, final String year,
                                           final Genre genre, final String type) {
        ArrayList<String> longestMovieList = new ArrayList<>();

        Comparator<Movie> comparator = Comparator.comparing(Movie::getTitle);
        movieList.sort(comparator);
        Comparator<Movie> comparator1 = Comparator.comparing(Movie::getDuration);
        movieList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(movieList);
        }

        for (Movie m : movieList) {
            if (m.checkFiltersMovie(year, genre) == 1) {
                longestMovieList.add(m.getTitle());
            }
        }

        while (longestMovieList.size() > n) {
            longestMovieList.remove(longestMovieList.size() - 1);
        }
        return longestMovieList;
    }

    /**
     *
     * @param n how many serials need to show
     * @param year filter
     * @param genre filter
     * @param type asc or desc
     * @return the longest serial (as duration)  with this filters
     */
    public ArrayList<String> longestSerial(final int n, final String year,
                                           final Genre genre, final String type) {
        ArrayList<String> longestSerialList = new ArrayList<>();

        for (Serial s : serialList) {
            s.setDurationSerial();
        }

        Comparator<Serial> comparator = Comparator.comparing(Serial::getTitle);
        serialList.sort(comparator);
        Comparator<Serial> comparator1 = Comparator.comparing(Serial::getDurationSerial);
        serialList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(serialList);
        }

        for (Serial s : serialList) {
            if (s.checkFiltersSerial(year, genre) == 1) {
                longestSerialList.add(s.getTitle());
            }
        }

        while (longestSerialList.size() > n) {
            longestSerialList.remove(longestSerialList.size() - 1);
        }

        return longestSerialList;
    }

    /**
     *
     * @param n how many movies need to show
     * @param year filter
     * @param genre filter
     * @param type asc or desc
     * @return the most viewed n movies with this filters
     */
    public ArrayList<String> mostViewedMovies(final int n, final String year,
                                              final Genre genre, final String type) {
        ArrayList<String> mostViewedMovieList = new ArrayList<>();

        for (Movie m : movieList) {
            m.setTotalViewsMovie(userList);
        }

        Comparator<Movie> comparator = Comparator.comparing(Movie::getTitle);
        movieList.sort(comparator);
        Comparator<Movie> comparator1 = Comparator.comparing(Movie::getTotalViews);
        movieList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(movieList);
        }

        for (Movie m : movieList) {
            if (m.checkFiltersMovie(year, genre) == 1) {
                if (m.getTotalViews() != 0) {
                    mostViewedMovieList.add(m.getTitle());
                }
            }
        }

        while (mostViewedMovieList.size() > n) {
            mostViewedMovieList.remove(mostViewedMovieList.size() - 1);
        }

        return mostViewedMovieList;
    }

    /**
     *
     * @param n how many serials need to show
     * @param year filter
     * @param genre filter
     * @param type asc or desc
     * @return the most viewed n serials with this filters
     */
    public ArrayList<String> mostViewedSerial(final int n, final String year,
                                              final Genre genre, final String type) {
        ArrayList<String> mostViewedSerialList = new ArrayList<>();

        for (Serial s : serialList) {
            s.setTotalViewsSerial(userList);
        }

        Comparator<Serial> comparator = Comparator.comparing(Serial::getTitle);
        serialList.sort(comparator);
        Comparator<Serial> comparator1 = Comparator.comparing(Serial::getTotalViews);
        serialList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(serialList);
        }

        for (Serial s : serialList) {
            if (s.checkFiltersSerial(year, genre) == 1) {
                if (s.getTotalViews() != 0) {
                    mostViewedSerialList.add(s.getTitle());
                }
            }
        }

        while (mostViewedSerialList.size() > n) {
            mostViewedSerialList.remove(mostViewedSerialList.size() - 1);
        }

        return mostViewedSerialList;
    }

    /**
     *
     * @param n how many users need to show
     * @param type asc or desc
     * @return the most active n users
     */
    public ArrayList<String> activity(final int n, final String type) {
        ArrayList<String> activUser = new ArrayList<>();

        Comparator<User> comparator = Comparator.comparing(User::getUsername);
        userList.sort(comparator);
        Comparator<User> comparator1 = Comparator.comparing(User::getActivity);
        userList.sort(comparator1);

        if (type.equals("desc")) {
            Collections.reverse(userList);
        }

        for (User u : userList) {
            if (u.getActivity() != 0) {
                activUser.add(u.getUsername());
            }
        }

        while (activUser.size() > n) {
            activUser.remove(activUser.size() - 1);
        }

        return activUser;
    }

    /**
     *
     * @param words the words that description must include
     * @param description the actor's description
     * @return if the description include or not the words
     */
    public boolean verifyDescription(final List<String> words, final List<String> description) {
        for (String word : words) {
            if (!description.contains(word)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param words the words that description must include
     * @param type asc or desc
     * @return the names of actors
     */
    public ArrayList<String> doFilterDescription(final List<String> words, final String type) {
        ArrayList<String> filter = new ArrayList<>();

        Comparator<ActorInfo> comparator = Comparator.comparing(ActorInfo::getName);
        actorsInfoList.sort(comparator);

        if (type.equals("desc")) {
            Collections.reverse(actorsInfoList);
        }

        for (ActorInfo a : actorsInfoList) {
            String[] description = a.getCareerDescription().toLowerCase().split("\\W", -2);
            List<String> text = new ArrayList<>(Arrays.asList(description));
            if (verifyDescription(words, text)) {
                filter.add(a.getName());
            }
        }
        return filter;
    }
}