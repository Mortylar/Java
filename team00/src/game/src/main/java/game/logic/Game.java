package game.logic;

import java.util.Scanner;

//import com.beust.jcommander.JCommander;
//import com.beust.jcommander.Parameter;

import chaselogic.WaveManager;

import game.logic.configuration.Configuration;
import game.logic.position.Position;
import game.logic.field.Field;
import game.logic.entity.EntityService;

import game.logic.entity.exception.EnemyGetPlayerException;
import game.logic.entity.exception.PlayerGetGoalException;

public class Game {

    private static Field field_;
    private static EntityService service_;
    private static Configuration conf_;

    public Game(Configuration configuration) {
        conf_ = configuration;
    }


    public void build() {
        field_ = new Field(conf_.getFieldSize(), conf_.getEmptyIcon());

        service_ = new EntityService(field_, conf_);
        service_.build();
        generateField();
    }

    public void run() {
        int playerId = 0;
        field_.print();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String ans = scanner.nextLine();
            try {
                while (!service_.movePlayer(ans, playerId)) {
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
