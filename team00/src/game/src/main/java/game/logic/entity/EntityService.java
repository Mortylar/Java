package game.logic.entity;

import chaselogic.WaveManager;
import game.logic.configuration.Configuration;
import game.logic.exception.EnemyGetPlayerException;
import game.logic.exception.PlayerGetGoalException;
import game.logic.exception.PlayerIsStackedException;
import game.logic.field.Field;
import game.logic.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EntityService {

    private static final String END_GAME_CODE = "9";
    private static final String CONFIRM_ENEMY_MOVE_DEV_CODE = "8";

    private Configuration conf_;

    private Player[] playerArr_;
    private Enemy[] enemyArr_;
    private Obstacle[] obstacleArr_;
    private Target[] targetArr_;

    private Field field_;

    public EntityService(Field field, Configuration configuration) {
        field_ = field;
        conf_ = configuration;
    }

    public void build() {
        createPlayerArray();
        createEnemyArray();
        createObstacleArray();
        createTargetArray();
    }

    private void createPlayerArray() {
        int playersCount = conf_.getPlayerCount();
        playerArr_ = new Player[playersCount];
        for (int i = 0; i < playersCount; ++i) {
            playerArr_[i] = new Player(conf_.getPlayerIcon());
            playerArr_[i].setTarget(conf_.getGoalIcon());
        }
    }

    private void createEnemyArray() {
        int enemiesCount = conf_.getEnemyCount();
        enemyArr_ = new Enemy[enemiesCount];
        for (int i = 0; i < enemiesCount; ++i) {
            enemyArr_[i] = new Enemy(conf_.getEnemyIcon());
            enemyArr_[i].setTarget(conf_.getPlayerIcon());
        }
    }

    private void createObstacleArray() {
        int obstaclesCount = conf_.getWallCount();
        obstacleArr_ = new Obstacle[obstaclesCount];
        for (int i = 0; i < obstaclesCount; ++i) {
            obstacleArr_[i] = new Obstacle(conf_.getWallIcon());
        }
    }

    private void createTargetArray() {
        int targetsCount = conf_.getGoalCount();
        targetArr_ = new Target[targetsCount];
        for (int i = 0; i < targetsCount; ++i) {
            targetArr_[i] = new Target(conf_.getGoalIcon());
        }
    }

    public void movePlayer(int playerId)
        throws PlayerGetGoalException, PlayerIsStackedException {
        if (isStacked(playerId)) {
            System.out.printf("\n%s is stacked.\n",
                              playerArr_[playerId].getName());
            throw new PlayerIsStackedException();
        }
        String ans = readAnswer();
        if (ans.equals(END_GAME_CODE)) {
            voluntaryExit();
        }
        while (!movePlayer(ans, playerId)) {
            ans = readAnswer();
            if (ans.equals(END_GAME_CODE)) {
                voluntaryExit();
            }
        }
        if (!conf_.getProfile().equals(conf_.DEV_PROFILE)) {
            field_.clearScreen();
        }
        field_.print();
    }

    private boolean movePlayer(String direction, int id)
        throws PlayerGetGoalException {
        Player player = playerArr_[id];
        Position oldPosition = player.getPosition();
        Position newPosition = new Position(oldPosition);
        if (direction.equals("w")) {
            newPosition.move(-1, 0);
        } else if (direction.equals("s")) {
            newPosition.move(1, 0);
        } else if (direction.equals("d")) {
            newPosition.move(0, 1);
        } else if (direction.equals("a")) {
            newPosition.move(0, -1);
        } else {
            System.out.printf("Direction %s incorrect.\n", direction);
            return false;
        }
        int fieldSize = field_.size();
        if (field_.checkPosition(newPosition)) {
            field_.clearPosition(oldPosition);
            player.move(newPosition);
            field_.setEntity(player);
            return true;
        } else if (field_.checkPosition(newPosition, player.getTargetIcon())) {
            System.out.printf("\nPlayer %s just saved the universe\n",
                              player.getName());
            throw new PlayerGetGoalException();
        }
        return false;
    }

    public boolean isStacked(int playerId) {
        boolean isStacked = true;
        Player player = playerArr_[playerId];
        Position pos = player.getPosition();
        Position[] positions = {new Position(pos).move(Position.UP),
                                new Position(pos).move(Position.DOWN),
                                new Position(pos).move(Position.RIGHT),
                                new Position(pos).move(Position.LEFT)};
        for (int i = 0; i < positions.length; ++i) {
            if (field_.checkPosition(positions[i], player.getTargetIcon()) ||
                field_.checkPosition(positions[i])) {
                isStacked = false;
                return isStacked;
            }
        }
        return isStacked;
    }

    public void moveEnemies() throws EnemyGetPlayerException {
        WaveManager manager = new WaveManager(field_.getCopy());
        manager.configure(conf_.getPlayerIcon(), conf_.getEnemyIcon(),
                          new int[] {conf_.getWallIcon(), conf_.getGoalIcon()});
        int[][] map = manager.run();
        for (int i = 0; i < enemyArr_.length; ++i) {
            moveEnemy(enemyArr_[i], map);
        }
    }

    private void moveEnemy(Enemy enemy, int[][] map)
        throws EnemyGetPlayerException {
        Position pos = enemy.getPosition();
        Position[] positions = {new Position(pos).move(Position.UP),
                                new Position(pos).move(Position.DOWN),
                                new Position(pos).move(Position.RIGHT),
                                new Position(pos).move(Position.LEFT)};
        HashMap<Position, Integer> ways = new HashMap<Position, Integer>();
        for (int i = 0; i < positions.length; ++i) {
            if ((field_.checkPosition(positions[i])) &&
                (map[positions[i].x()][positions[i].y()] != 0)) {
                ways.put(positions[i], map[positions[i].x()][positions[i].y()]);
            } else if (field_.checkPosition(positions[i],
                                            enemy.getTargetIcon())) {
                System.out.printf("\n%s eat player\n", enemy.getName());
                throw new EnemyGetPlayerException();
            }
        }

        ArrayList<Map.Entry<Position, Integer>> list = sortMapByValue(ways);
        int index = list.size() - 1;
        while (index >= 0) {
            Position position = list.get(index).getKey();
            if (moveEnemy(enemy, position)) {
                System.out.printf("Enemy %s move from %d %d to %d %d.\n",
                                  enemy.getName(), pos.x(), pos.y(),
                                  position.x(), position.y());
                index = -1;
                if (conf_.getProfile().equals(conf_.DEV_PROFILE)) {
                    String ans = readAnswer();
                    if (ans.equals(END_GAME_CODE)) {
                        voluntaryExit();
                    }
                    while (!ans.equals(CONFIRM_ENEMY_MOVE_DEV_CODE)) {
                        ans = readAnswer();
                        if (ans.equals(END_GAME_CODE)) {
                            voluntaryExit();
                        }
                    }
                    field_.print();
                } else {
                    field_.clearScreen();
                    field_.print();
                }
            }
            --index;
        }
    }

    private boolean moveEnemy(Enemy enemy, Position newPosition)
        throws EnemyGetPlayerException {
        if (field_.checkPosition(newPosition)) {
            field_.clearPosition(enemy.getPosition());
            enemy.move(newPosition);
            field_.setEntity(enemy);
            return true;
        } else if (field_.checkPosition(newPosition, enemy.getTargetIcon())) {
            throw new EnemyGetPlayerException();
        }
        return false;
    }

    private ArrayList<Map.Entry<Position, Integer>>
    sortMapByValue(Map<Position, Integer> m) {
        ArrayList list = new ArrayList(m.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Position, Integer>>() {
            @Override
            public int compare(Map.Entry<Position, Integer> left,
                               Map.Entry<Position, Integer> right) {
                return right.getValue() - left.getValue();
            }
        });
        return list;
    }

    public Entity[] getEntityArr(char icon) {
        if (conf_.getPlayerIcon() == icon) {
            return playerArr_;
        } else if (conf_.getEnemyIcon() == icon) {
            return enemyArr_;
        } else if (conf_.getWallIcon() == icon) {
            return obstacleArr_;
        } else if (conf_.getGoalIcon() == icon) {
            return targetArr_;
        } else {
            System.err.printf("Entity with icon %s not found.\n", icon);
            System.exit(-1);
            return null;
        }
    }

    private String readAnswer() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String ans = scanner.nextLine();
            return ans;
        }
        System.out.printf("\nNo input found. Goodbye, looser.\n");
        System.exit(0);
        return null;
    }

    private void voluntaryExit() {
        System.out.printf("\nCongratulation!!! - You gave up!\n");
        System.exit(0);
    }
}
