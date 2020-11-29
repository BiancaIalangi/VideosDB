package lists;

import application.Actor;
import application.Movie;
import application.Serial;
import application.User;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;


public class Lists {

    private List<User> userList;
    private List<Serial> serialList;
    private List<Movie> movieList;

    public Lists() {
        this.userList = new ArrayList<>();
        this.serialList = new ArrayList<>();
        this.movieList = new ArrayList<>();
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

    public ArrayList<Actor> ordRating() {
        ArrayList<Actor> actorList = new ArrayList<>();
        for (Serial serial : serialList) {
            double rating = serial.getGeneralRatingSerial();
            String title = serial.getTitle();
            if (rating != 0) {
                Actor a = new Actor(title, rating);
                actorList.add(a);
            }
        }

        for (Movie movie : movieList) {
            double rating = movie.getGeneralRatingMovie();
            String title = movie.getTitle();
            if (rating != 0) {
                Actor a = new Actor(title, rating);
                actorList.add(a);
            }
        }
        return actorList;
    }

    public int getAllCast(ArrayList<Actor> a) {
        int sum = 0;
        for (Actor actor : a) {
            String title = actor.getTitle();
            Serial s = getByTitleSerial(title);
            if (s != null) {
                sum += s.getCast().size();
            } else {
                Movie m = getByTitleMovie(title);
                sum += m.getCast().size();
            }
        }
        return sum;
    }

    public ArrayList<String> actorsAverage(ArrayList<Actor> actorList, int n) {
        int sum = getAllCast(actorList);
        ArrayList<String> a = new ArrayList<>();
        if (n >= sum) {
            for (Actor actor : actorList) {
                String title = actor.getTitle();
                Serial s = getByTitleSerial(title);
                if (s != null) {
                    a.addAll(s.getCast());
                } else {
                    Movie m = getByTitleMovie(title);
                    a.addAll(m.getCast());
                }
            }
            return a;
        }
        for (Actor actor : actorList) {
            if (n <= 0) {
                break;
            }
            String title = actor.getTitle();
            Serial s = getByTitleSerial(title);         // find title in series
            if (s != null) {
                if (s.getCast().size() >= n) {
                    for (int j = 0; j < n; j++) {
                        a.add(s.getCast().get(j));
                    }
                } else {
                    a.addAll(s.getCast());
                }
            } else {
                Movie m = getByTitleMovie(title);
                if (m.getCast().size() >= n) {
                    for (int j = 0; j < n; j++) {
                        a.add(m.getCast().get(j));
                    }
                } else {
                    a.addAll(m.getCast());
                }
            }
            n = n - a.size();
        }
        return a;
    }

    @Override
    public String toString() {
        return "Lists{" +
                "movieList=" + movieList +
                '}';
    }
}

