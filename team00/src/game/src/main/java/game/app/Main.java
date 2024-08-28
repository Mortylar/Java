package game.app;

//import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

//import chaselogic.WaveManager;

import game.logic.configuration.Configuration;
//import game.logic.entity.EntityService;
//import game.logic.position.Position;
//import game.logic.field.Field;
import game.logic.Game;

import game.logic.exception.IllegalParameterException;
//import game.logic.entity.exception.PlayerGetGoalException;

public class Main {

    @Parameter(names = {"-ec", "--enemiesCount="}, description = "Enemies Count")
    private static int enemiesCount_;

    @Parameter(names = {"-wc", "--wallsCount="}, description = "Obstacles Count")
    private static int wallsCount_;

    @Parameter(names = {"-s", "--size="}, description = "Field size")
    private static int size_;

    @Parameter(names = {"-p", "--profile="}, description = "User profile")
    private static String profile_;


    private static Configuration conf_;

    public static void main(String[] args) throws IllegalParameterException {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);

        conf_ = new Configuration();
        conf_.setEnemyCount(enemiesCount_);
        conf_.setWallCount(wallsCount_);
        conf_.setPlayerCount(1);
        conf_.setGoalCount(1);
        conf_.setFieldSize(size_);

        Game game = new Game(conf_);
        game.build();
        game.run();

    }

}
