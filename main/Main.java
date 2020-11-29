package main;

import application.Actor;
import application.ActorsSortingComparator;
import checker.Checker;
import checker.Checkstyle;
import command.FavViewRating;
import common.Constants;
import fileio.*;
import lists.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


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
        Lists l = new Lists();
        l.makeUsers(users);
        l.makeMovies(movies);
        l.makeSerial(serial);

        StringBuilder message;


//        for (Movie mvy : l.getMovieList()) {
//            System.out.println("Movies: " + mvy.getRate());
//        }
//
//        for (Serial sry : l.getSerialList()) {
//            System.out.println("Serial: " + sry.getRatedSeasons());
//        }
//
//        for (User usr : l.getUserList()) {
//            System.out.println("User: " + usr.getRatedShows());
//        }
//
//        for (Actor act : l.getActorList()) {
//            System.out.println("Actors: " + act.getName());
//        }

        for (ActionInputData action : actions) {
            if (action.getActionType().equals("command")) {
                FavViewRating f = new FavViewRating(l);
                message = new StringBuilder(f.Commands(action, l.getMovieList(), l.getSerialList()));
                JSONObject object = fileWriter.writeFile(action.getActionId(),
                        "", message.toString());
                arrayResult.add(object);
            }
            if (action.getActionType().equals("query")) {
                if (action.getCriteria().equals("average")) {
                    ArrayList<Actor> a;
                    List<String> average;
                    l.generalRatingAsc();           //ordonez alfabetic cast-ul
                    a = l.ordRating();
                    a.sort(new ActorsSortingComparator());
                    if (action.getSortType().equals("desc")) {
                        l.generalRatingDesc();          //reverse la castul ordonat alfabetic
                        Collections.reverse(a);
                    }
                    average = l.actorsAverage(a, action.getNumber());
                    message = new StringBuilder("Query result: [" + average.get(0));
                    for (int i = 1; i < average.size(); i++) {
                        message.append(", ").append(average.get(i));
                    }
                    message.append("]");
                    JSONObject object = fileWriter.writeFile(action.getActionId(),
                            "", message.toString());
                    arrayResult.add(object);
                }

            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}