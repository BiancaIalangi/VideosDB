package main;

import application.Action;
import application.Rating;
import application.Serial;
import application.User;
import checker.Checkstyle;
import checker.Checker;
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
import java.util.List;
import java.util.Objects;

import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;


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
        List<User> userList = new ArrayList<>();
        List<Serial> serialList = new ArrayList<>();
        List<Rating> ratingList = new ArrayList<>();
        Lists l =  new Lists(userList, serialList, ratingList);

        String message;
        int find;

        for (ActionInputData action : actions) {
            if (action.getActionType().equals("command")) {
                Action a = new Action(action);
                find = 0;
                while (!users.get(find).getUsername().equals(action.getUsername())) {
                    find++;
                }
                User u = new User(users.get(find));
                FavViewRating f = new FavViewRating(a, u, l);
                message = f.Commands(u, a);
                JSONObject object = fileWriter.writeFile(action.getActionId(),
                            "", message);
                arrayResult.add(object);
                }
            }
        fileWriter.closeJSON(arrayResult);
    }
}