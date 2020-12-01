package lists;

import application.*;
import comparator.*;
import entertainment.Genre;
import fileio.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Lists {

    private List<User> userList;
    private List<Serial> serialList;
    private List<Movie> movieList;
    private List<ActorInfo> actorsInfoList;

    public Lists() {
        this.userList = new ArrayList<>();
        this.serialList = new ArrayList<>();
        this.movieList = new ArrayList<>();
        this.actorsInfoList = new ArrayList<>();
    }

    public void makeUsers (List<UserInputData> u) {
        for (UserInputData userInputData : u) {
            User usr = new User(userInputData);
            userList.add(usr);
        }
    }
    public void makeSerial (List<SerialInputData> s) {
        for (SerialInputData serialInputData : s) {
            Serial ser = new Serial(serialInputData);
            serialList.add(ser);
        }
    }
    public void makeMovies (List<MovieInputData> m) {
        for (MovieInputData movieInputData : m) {
            Movie mov = new Movie(movieInputData);
            movieList.add(mov);
        }
    }

    public void makeActorsInfo (List<ActorInputData> a) {
        for (ActorInputData actorInputData : a) {
            ActorInfo act = new ActorInfo(actorInputData);
            actorsInfoList.add(act);
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Serial> getSerialList() {
        return serialList;
    }

    public void setSerialList(List<Serial> serialList) {
        this.serialList = serialList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Movie getByTitleMovie(String title) {
        for (Movie movie : this.movieList) {
            if (movie.getTitle().equals(title))
                return movie;
        }
        return null;
    }

    public User getByUsername (String name) {
        for (User user : this.userList) {
            if (user.getUsername().equals(name))
                return user;
        }
        return null;
    }

    public Serial getByTitleSerial(String title) {
        for (Serial serial : this.serialList) {
            if (serial.getTitle().equals(title))
                return serial;
        }
        return null;
    }

    public void generalRatingAsc () {
        for (Serial serial : serialList) {
            serial.generalRating();
            serial.sortCastAsc();
        }

        for (Movie movie : movieList) {
            movie.generalRating();
            movie.sortCastAsc();
        }

    }

    public void generalRatingDesc () {
        for (Serial item : serialList) {
            item.generalRating();
            item.sortCastDesc();
        }

        for (Movie value : movieList) {
            value.generalRating();
            value.sortCastDesc();
        }

    }

    public int existActor (ArrayList<Actor> a, String name) {
        int pos = -1;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getName().equals(name))
                pos = i;
        }
        return pos;
    }

    public ArrayList<Actor> ordRating() {
        ArrayList<Actor> actorList = new ArrayList<>();
        for (Serial serial : serialList) {
            for (int i = 0; i < serial.getCast().size(); i++) {
                if (serial.getGeneralRatingSerial() != 0) {
                    if (existActor(actorList, serial.getCast().get(i)) != -1) {
                        actorList.get(existActor(actorList, serial.getCast().get(i))).incrementTimesin();
                        actorList.get(existActor(actorList, serial.getCast().get(i))).setRating(serial.getGeneralRatingSerial());
                    } else {
                        Actor a = new Actor(serial.getGeneralRatingSerial(), serial.getCast().get(i));
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
                        actorList.get(existActor(actorList, m.getCast().get(i))).setRating(m.getGeneralRatingMovie());
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


    public ArrayList<String> actorsAverage(ArrayList<Actor> actorList, int n) {
        ArrayList<String> a = new ArrayList<>();

        for (Actor actor : actorList) {
            a.add(actor.getName());
        }

        while (a.size() > n)
            a.remove(a.size() - 1);

        return a;
    }

    public ArrayList<Awards> queryAwards (List<String> awards) {
        ArrayList<Awards> list = new ArrayList<>();
        for (ActorInfo actor : actorsInfoList) {
            int s;
            if (actor.getAwards().size() < awards.size())
                continue;
            else if (actor.containAwards(awards) == 0) {
                continue;
            } else {
                s = actor.numberOfAwards();
                Awards a = new Awards(actor.getName(), s);
                list.add(a);
            }
        }
        return list;
    }

    public ArrayList<String> ratingMovies (int n, String year, Genre genre, String type) {
        generalRatingAsc();
        movieList.sort(new MovieRatingComparator());
        if (type.equals("desc"))
            Collections.reverse(movieList);
        ArrayList<String> ratingMovieList = new ArrayList<>();
        for (Movie m : movieList) {
            if (m.checkFiltersMovie(year, genre) == 1)
                if (m.getGeneralRatingMovie() != 0)
                    ratingMovieList.add(m.getTitle());
        }

        while (ratingMovieList.size() > n)
            ratingMovieList.remove(ratingMovieList.size() - 1);

        return ratingMovieList;
    }

    public ArrayList<String> ratingSerial (int n, String year, Genre genre, String type) {
        generalRatingAsc();
        serialList.sort(new SerialRatingComparator());
        if (type.equals("desc"))
            Collections.reverse(serialList);
        ArrayList<String> ratingSerialList = new ArrayList<>();
        for (Serial s : serialList) {
            if (s.checkFiltersSerial(year, genre) == 1)
                if (s.getGeneralRatingSerial() != 0)
                    ratingSerialList.add(s.getTitle());
        }

        while (ratingSerialList.size() > n)
            ratingSerialList.remove(ratingSerialList.size() - 1);

        return ratingSerialList;
    }

    public ArrayList<String> favoriteMovies (int n, String year, Genre genre, String type) {
        generalRatingAsc();
        ArrayList<String> favoriteMovieList = new ArrayList<>();
        for (Movie m : movieList) {
            m.iterateFavoriteMovie(userList);
        }
        movieList.sort(new MovieFavSortingComparator());
        if (type.equals("desc"))
            Collections.reverse(movieList);

        for (Movie m : movieList) {
            if (m.checkFiltersMovie(year, genre) == 1)
                if (m.getTimesInFavoriteMovies() != 0)
                    favoriteMovieList.add(m.getTitle());
        }

        while (favoriteMovieList.size() > n)
            favoriteMovieList.remove(favoriteMovieList.size() - 1);

        return favoriteMovieList;
    }

    public ArrayList<String> favoriteSerial (int n, String year, Genre genre, String type) {
        generalRatingAsc();
        ArrayList<String> favoriteSerialList = new ArrayList<>();
        for (Serial s : serialList) {
            s.iterateFavoriteSerial(userList);
        }
        serialList.sort(new SerialFavSortingComparator());
        if (type.equals("desc"))
            Collections.reverse(serialList);

        for (Serial s : serialList) {
            if (s.checkFiltersSerial(year, genre) == 1)
                if (s.getTimesInFavoriteSerial() != 0)
                    favoriteSerialList.add(s.getTitle());
        }

        while (favoriteSerialList.size() > n)
            favoriteSerialList.remove(favoriteSerialList.size() - 1);

        return favoriteSerialList;
    }

    public ArrayList<String> longestMovies (int n, String year, Genre genre, String type) {
        ArrayList<String> longestMovieList = new ArrayList<>();
        movieList.sort(new MovieLongSortingComparator());
        if (type.equals("desc"))
            Collections.reverse(movieList);

        for (Movie m : movieList) {
            if (m.checkFiltersMovie(year, genre) == 1)
                longestMovieList.add(m.getTitle());
        }

        while (longestMovieList.size() > n)
            longestMovieList.remove(longestMovieList.size() - 1);
        return longestMovieList;
    }

    public ArrayList<String> longestSerial (int n, String year, Genre genre, String type) {
        ArrayList<String> longestSerialList = new ArrayList<>();
        for (Serial s : serialList) {
            s.setDurationSerial();
        }
        serialList.sort(new SerialLongSortingComparator());
        if (type.equals("desc"))
            Collections.reverse(serialList);

        for (Serial s : serialList) {
            if (s.checkFiltersSerial(year, genre) == 1)
                longestSerialList.add(s.getTitle());
        }

        while (longestSerialList.size() > n)
            longestSerialList.remove(longestSerialList.size() - 1);

        return longestSerialList;
    }

    public ArrayList<String> mostViewedMovies (int n, String year, Genre genre, String type) {
        ArrayList<String> mostViewedMovieList = new ArrayList<>();
        for (Movie m : movieList) {
            m.setTotalViewsMovie(userList);
        }

        movieList.sort(new MovieViewsSortingComparator());
        if (type.equals("desc"))
            Collections.reverse(movieList);

        for (Movie m : movieList) {
            if (m.checkFiltersMovie(year, genre) == 1)
                if (m.getTotalViews() != 0)
                    mostViewedMovieList.add(m.getTitle());
        }

        while (mostViewedMovieList.size() > n)
            mostViewedMovieList.remove(mostViewedMovieList.size() - 1);
        return mostViewedMovieList;
    }

    public ArrayList<String> mostViewedSerial (int n, String year, Genre genre, String type) {
        ArrayList<String>mostViewedSerialList = new ArrayList<>();
        for (Serial s : serialList) {
            s.setTotalViewsSerial(userList);
        }

        serialList.sort(new SerialViewsSortingComparator());
        if (type.equals("desc"))
            Collections.reverse(serialList);

        for (Serial s : serialList) {
            if (s.checkFiltersSerial(year, genre) == 1)
                if (s.getTotalViews() != 0)
                    mostViewedSerialList.add(s.getTitle());
        }

        while (mostViewedSerialList.size() > n)
            mostViewedSerialList.remove(mostViewedSerialList.size() - 1);

        return mostViewedSerialList;
    }

    public ArrayList<String> activity (int n, String type, List<ActionInputData> act) {
        ArrayList<String> activUser = new ArrayList<>();

        userList.sort(new ActivitySortingComparator());
        if (type.equals("desc"))
            Collections.reverse(userList);

        for (User u : userList) {
            if (u.getActivity() != 0) {
                activUser.add(u.getUsername());
            }
        }

        while (activUser.size() > n)
            activUser.remove( activUser.size() - 1);

        return activUser;
    }

    public boolean verifyDescription (List<String> words, List<String> description) {
        for (int i = 0; i < words.size(); i++) {
            if (!description.contains(words.get(i)))
                return false;
        }
        return true;
    }


    public ArrayList<String> doFilterDescription (List<String> words, String type) {
        ArrayList<String> filter = new ArrayList<>();

        actorsInfoList.sort(new ActorInfoSortingComparator());
        if (type != null && type.equals("desc"))
            Collections.reverse(actorsInfoList);


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

