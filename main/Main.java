
package main;
import application.Actor;
import application.Awards;
import checker.Checker;
import checker.Checkstyle;
import command.FavViewRating;
import common.Constants;
import entertainment.Genre;
import fileio.*;
import lists.Query;
import lists.Recommendation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static utils.Utils.stringToGenre;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }


    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        List<ActorInputData> actors = input.getActors();
        List<UserInputData> users = input.getUsers();
        List<ActionInputData> actions = input.getCommands();
        List<SerialInputData> serial = input.getSerials();
        List<MovieInputData> movies = input.getMovies();
        Query l = new Query();
        l.makeUsers(users);
        l.makeMovies(movies);
        l.makeSerial(serial);
        l.makeActorsInfo(actors);

        for (ActionInputData action : actions) {
            StringBuilder message = new StringBuilder();
            if (action.getActionType().equals("command")) {
                FavViewRating f = new FavViewRating(l);
                message.append(f.commandFavViewRating(action));
            }
            if (action.getActionType().equals("query")) {
                switch (action.getCriteria()) {
                    case "average" -> {

                        ArrayList<Actor> a = l.ordRating();
                        List<String> average = l.actorsAverage(a,
                                action.getNumber(), action.getSortType());
                        message.append("Query result: [").append(average.get(0));
                        for (int i = 1; i < average.size(); i++) {
                            message.append(", ").append(average.get(i));
                        }
                        message.append("]");
                    }
                    case "awards" -> {
                        List<Awards> awards;
                        awards = l.queryAwards(action.getFilters().get(3),  action.getSortType());

                        Comparator<Awards> comparator1 = Comparator.comparing(Awards::getName);
                        awards.sort(comparator1);
                        Comparator<Awards> comparator = Comparator.
                                comparing(Awards::getNumberOfAwards);
                        awards.sort(comparator);

                        if (action.getSortType().equals("desc")) {
                            Collections.reverse(awards);
                        }
                        if (awards.isEmpty()) {
                            message.append("Query result: []");
                        } else {
                            message.append("Query result: [").append(awards.get(0).getName());
                            for (int i = 1; i < awards.size(); i++) {
                                message.append(", ").append(awards.get(i).getName());
                            }
                            message.append("]");
                        }
                    }
                    case "ratings" -> {
                        ArrayList<String> show;
                        if (action.getObjectType().equals("movies")) {
                            show = l.ratingMovies(action.getNumber(),
                                    action.getFilters().get(0).get(0),
                                    stringToGenre(action.getFilters().get(1).get(0)),
                                    action.getSortType());
                        } else {
                            show = l.ratingSerial(action.getNumber(),
                                    action.getFilters().get(0).get(0),
                                    stringToGenre(action.getFilters().get(1).get(0)),
                                    action.getSortType());
                        }
                        if (show.isEmpty()) {
                            message.append("Query result: []");
                        } else {
                            message.append("Query result: [").append(show.get(0));
                            for (int i = 1; i < show.size(); i++) {
                                message.append(", ").append(show.get(i));
                            }
                            message.append("]");
                        }
                    }
                    case "favorite" -> {
                        ArrayList<String> fav;
                        Genre gen = null;
                        if (action.getFilters().get(1).get(0) != null) {
                            gen = stringToGenre(action.getFilters().get(1).get(0));
                        }
                        if (action.getObjectType().equals("movies")) {
                            fav = l.favoriteMovies(action.getNumber(),
                                    action.getFilters().get(0).get(0), gen, action.getSortType());
                        } else {
                            fav = l.favoriteSerial(action.getNumber(),
                                    action.getFilters().get(0).get(0), gen, action.getSortType());
                        }
                        if (fav.isEmpty() || (action.getFilters().
                                get(1).get(0) != null && gen == null)) {
                            message.append("Query result: []");
                        } else {
                            message.append("Query result: [").append(fav.get(0));
                            for (int i = 1; i < fav.size(); i++) {
                                message.append(", ").append(fav.get(i));
                            }
                            message.append("]");
                        }
                    }
                    case "longest" -> {
                        ArrayList<String> longest;
                        Genre gen = null;
                        if (action.getFilters().get(1).get(0) != null) {
                            gen = stringToGenre(action.getFilters().get(1).get(0));
                        }
                        if (action.getObjectType().equals("movies")) {
                            longest = l.longestMovies(action.getNumber(),
                                    action.getFilters().get(0).get(0), gen, action.getSortType());
                        } else {
                            longest = l.longestSerial(action.getNumber(),
                                    action.getFilters().get(0).get(0), gen, action.getSortType());
                        }
                        if (longest.isEmpty() || (action.getFilters().
                                get(1).get(0) != null && gen == null)) {
                            message.append("Query result: []");
                        } else {
                            message.append("Query result: [").append(longest.get(0));
                            for (int i = 1; i < longest.size(); i++) {
                                message.append(", ").append(longest.get(i));
                            }
                            message.append("]");
                        }
                    }
                    case "most_viewed" -> {
                        ArrayList<String> mostViewed;
                        Genre gen = null;
                        if (action.getFilters().get(1).get(0) != null) {
                            gen = stringToGenre(action.getFilters().get(1).get(0));
                        }
                        if (action.getObjectType().equals("movies")) {
                            mostViewed = l.mostViewedMovies(action.getNumber(),
                                    action.getFilters().get(0).get(0), gen, action.getSortType());
                        } else {
                            mostViewed = l.mostViewedSerial(action.getNumber(),
                                    action.getFilters().get(0).get(0), gen, action.getSortType());
                        }
                        if (mostViewed.isEmpty() || (action.getFilters().
                                get(1).get(0) != null && gen == null)) {
                            message.append("Query result: []");
                        } else {
                            message.append("Query result: [").append(mostViewed.get(0));
                            for (int i = 1; i < mostViewed.size(); i++) {
                                message.append(", ").append(mostViewed.get(i));
                            }
                            message.append("]");
                        }
                    }
                    case "num_ratings" -> {
                        ArrayList<String> userActivity;
                        userActivity = l.activity(action.getNumber(), action.getSortType());
                        if (userActivity.isEmpty()) {
                            message.append("Query result: []");
                        } else {
                            message.append("Query result: [").append(userActivity.get(0));
                            for (int i = 1; i < userActivity.size(); i++) {
                                message.append(", ").append(userActivity.get(i));
                            }
                            message.append("]");
                        }
                    }
                    case "filter_description" -> {
                        ArrayList<String> filter;
                        filter = l.doFilterDescription(action.
                                getFilters().get(2), action.getSortType());
                        if (filter.isEmpty()) {
                            message.append("Query result: []");
                        } else {
                            message.append("Query result: [").append(filter.get(0));
                            for (int i = 1; i < filter.size(); i++) {
                                message.append(", ").append(filter.get(i));
                            }
                            message.append("]");
                        }
                    }
                }
            } else if (action.getActionType().equals("recommendation")) {
                Recommendation r = new Recommendation(l, movies, serial);
                switch (action.getType()) {
                    case "standard" -> {
                        String output = r.standardRecommend(action.getUsername());
                        message.append("StandardRecommendation ");
                        if (output == null) {
                            message.append("cannot be applied!");
                        } else  {
                            message.append("result: ").append(output);
                        }
                    }
                    case "best_unseen" -> {
                        String output = r.bestSeenRecommend(action.getUsername());
                        message.append("BestRatedUnseenRecommendation ");
                        if (output == null) {
                            message.append("cannot be applied!");
                        } else  {
                            message.append("result: ").append(output);
                        }
                    }
                    case "search" -> {
                        ArrayList<String> output = r.searchRecommend(
                                action.getUsername(), action.getGenre());
                        message.append("SearchRecommendation ");
                        if (output.isEmpty()) {
                            message.append("cannot be applied!");
                        } else  {
                            message.append("result: ").append(output);
                        }
                    }
                    case "favorite" -> {
                        String output = r.favCommend(action.getUsername());
                        message.append("FavoriteRecommendation ");
                        if (output == null) {
                            message.append("cannot be applied!");
                        } else  {
                            message.append("result: ").append(output);
                        }
                    }
                    case "popular" -> {
                        String output = r.popularCommend(action.getUsername());
                        message.append("PopularRecommendation ");
                        if (output == null) {
                            message.append("cannot be applied!");
                        } else  {
                            message.append("result: ").append(output);
                        }
                    }
                }
            }
            JSONObject object = fileWriter.writeFile(action.getActionId(),
                    "", message.toString());
            arrayResult.add(object);
        }
        fileWriter.closeJSON(arrayResult);
    }
}