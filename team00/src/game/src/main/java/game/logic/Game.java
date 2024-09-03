package game.logic;

import chaselogic.WaveManager;
import game.logic.configuration.Configuration;
import game.logic.entity.EntityService;
import game.logic.exception.EnemyGetPlayerException;
import game.logic.exception.IllegalParameterException;
import game.logic.exception.PlayerGetGoalException;
import game.logic.exception.PlayerIsStackedException;
import game.logic.field.Field;
import game.logic.position.Position;

public class Game {

    private static final int REGENERATE_LIMIT = 10;

    private static Field field_;
    private static EntityService service_;
    private static Configuration conf_;

    public Game(Configuration configuration) { conf_ = configuration; }

    public void build() throws IllegalParameterException {
        field_ = new Field(conf_);

        service_ = new EntityService(field_, conf_);
        service_.build();
        checkArguments();
        generateField();
    }

    public void run() {
        int playerId = 0;
        if (!conf_.getProfile().equals(conf_.DEV_PROFILE)) {
            field_.clearScreen();
        }
        field_.print();
        while (true) {
            try {
                service_.movePlayer(playerId);
            } catch (PlayerGetGoalException e) {
                sorry();
            } catch (PlayerIsStackedException e) {
                congratulations();
            }
            System.out.printf("\nEnemies:\n");
            try {
                service_.moveEnemies();
            } catch (EnemyGetPlayerException e) {
                congratulations();
            }
            System.out.printf("\nPlayer:\n");
        }
    }

    private static boolean generateField() {
        boolean isHavingAPath = false;
        WaveManager manager;
        int regenerateCount = 0;
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
            manager.configure(
                conf_.getPlayerIcon(), conf_.getGoalIcon(),
                new int[] {conf_.getWallIcon(), conf_.getEnemyIcon()});

            int[][] map = manager.run();
            Position pos =
                service_.getEntityArr(conf_.getGoalIcon())[0].getPosition();
            Position[] positions =
                new Position[] {new Position(pos).move(Position.UP),
                                new Position(pos).move(Position.DOWN),
                                new Position(pos).move(Position.LEFT),
                                new Position(pos).move(Position.RIGHT)};
            for (int i = 0; i < positions.length; ++i) {
                int x = positions[i].x();
                int y = positions[i].y();
                if ((x >= 0) && (y >= 0) && (x < map.length) &&
                    (y < map.length)) {
                    if ((map[x][y] != 0) &&
                        (map[x][y] < map.length * map.length)) {
                        return true;
                    }
                }
            }
            field_.clear();
            ++regenerateCount;
            if (regenerateCount > REGENERATE_LIMIT) {
                System.err.printf(
                    "\nRegeneration limit is end. Try with new arguments.\n");
                System.exit(-1);
            }
            System.out.printf("\nBad field generate --> regeneration...\n");
        }
        return false;
    }

    private void checkArguments() throws IllegalParameterException {
        int needEmpty = 2;
        int emptySpace = conf_.getFieldSize() * conf_.getFieldSize() -
                         conf_.getEnemyCount() - conf_.getWallCount();
        if (emptySpace < needEmpty) {
            throw new IllegalParameterException();
        }
    }

    private void sorry() {
        System.out.printf("\nSorry, but you win\n");
        System.exit(0);
    }

    private void congratulations() {
        System.out.printf("\nCongratulations!!! You lost!\n");
        System.exit(0);
    }
}
