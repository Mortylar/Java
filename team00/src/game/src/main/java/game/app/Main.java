package game.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import game.logic.Game;
import game.logic.configuration.Configuration;
import game.logic.exception.IllegalParameterException;

@Parameters(separators = "=")

public class Main {

    @Parameter(names = {"-ec", "--enemiesCount"}, description = "Enemies Count")
    private static int enemiesCount_;

    @Parameter(names = {"-wc", "--wallsCount"}, description = "Obstacles Count")
    private static int wallsCount_;

    @Parameter(names = {"-s", "--size"}, description = "Field size")
    private static int size_;

    @Parameter(names = {"-p", "--profile"}, description = "User profile")
    private static String profile_;

    private static Configuration conf_;

    public static void main(String[] args) throws IllegalParameterException {
        Main main = new Main();
        try {
            JCommander.newBuilder().addObject(main).build().parse(args);
        } catch (ParameterException e) {
            System.err.printf("\n%s.\n", e.getMessage());
            System.exit(-1);
        }

        conf_ = new Configuration();
        conf_.setProfile(profile_);
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
