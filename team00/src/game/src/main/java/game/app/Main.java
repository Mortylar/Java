package game.app;

import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import chaselogic.WaveManager;

import game.logic.configuration.Configuration;
import game.logic.position.Position;
import game.logic.field.Field;
import game.logic.entity.EntityService;

import game.logic.entity.exception.EnemyGetPlayerException;
import game.logic.entity.exception.PlayerGetGoalException;

public class Main {

    @Parameter(names = {"-ec", "--enemiesCount="}, description = "Enemies Count")
    private static int enemiesCount_;

    @Parameter(names = {"-wc", "--wallsCount="}, description = "Obstacles Count")
    private static int wallsCount_;

    @Parameter(names = {"-s", "--size="}, description = "Field size")
    private static int size_;

    @Parameter(names = {"-p", "--profile="}, description = "User profile")
    private static String profile_;


    private static Field field_;
    private static EntityService service_;
    private static Configuration conf_;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);

        conf_ = new Configuration();
        conf_.setEnemyCount(enemiesCount_);
        conf_.setWallCount(wallsCount_);
        conf_.setPlayerCount(1);
        conf_.setGoalCount(1);

        field_ = new Field(size_, conf_.getEmptyIcon());

        service_ = new EntityService(field_, conf_);
        service_.build();
        generateField();
        field_.print();


        while (true) {
            Scanner scanner = new Scanner(System.in);
            String ans = scanner.nextLine();
            try {
                while (!service_.movePlayer(ans, 0)) {
                    ans = scanner.nextLine();
                }
            } catch (PlayerGetGoalException e) {
                System.out.print("\nWIN\n");
                System.exit(0);
            }
            field_.print();
            System.out.printf("\n==========\nEnemies:\n");
            try {
                service_.moveEnemies();
            } catch (EnemyGetPlayerException e) {
                System.out.print("\nYOU DIED\n");
                System.exit(0);
            }
            field_.print();
            System.out.printf("\n==========\nPlayer:\n");
        }

    }

    private static boolean generateField() {
        boolean isHavingAPath = false;
        WaveManager manager;
        //manager.configure(conf_.getPlayerIcon(), conf_.getGoalIcon(),
          //                new int[] {conf_.getWallIcon(), conf_.getEnemyIcon()});
        while (false == isHavingAPath) { 
            field_.generateEntitiesPosition(
                service_.getEntityArr(conf_.getPlayerIcon()));
            field_.generateEntitiesPosition(
                service_.getEntityArr(conf_.getWallIcon()));
            field_.generateEntitiesPosition(
                service_.getEntityArr(conf_.getEnemyIcon()));
            field_.generateEntitiesPosition(
                service_.getEntityArr(conf_.getGoalIcon()));

            manager = new WaveManager(field_.getCopy());
            manager.configure(conf_.getPlayerIcon(), conf_.getGoalIcon(),
                              new int[] {conf_.getWallIcon(), conf_.getEnemyIcon()});

            int[][] map = manager.run();
            Position goalPosition = service_.getEntityArr(conf_.getGoalIcon())[0].getPosition();
            for (int i = -1; i <= 1; i += 2) {
                for (int j = -1; j <= 1; j += 2) { //TODO right algorithm
                    int x = i + goalPosition.x();
                    int y = j + goalPosition.y();
                    if ((x >= 0) && (y >= 0) && (x < map.length) && (y < map.length)) {
                        if ((map[x][y] != 0) && (map[x][y] < map.length * map.length)) {
                            return true;
                        }
                    }
                }
            }
            field_.clear();
            System.out.printf("\nBad field generate --> regeneration...\n");
       }
       return false;
    }
}
