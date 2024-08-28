package game.logic.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import chaselogic.WaveManager;

import game.logic.configuration.Configuration;
import game.logic.field.Field;
import game.logic.position.Position;
import game.logic.exception.PlayerGetGoalException;
import game.logic.exception.EnemyGetPlayerException;


public class EntityService {

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

    public boolean movePlayer(String direction, int id)
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
            player.setPosition(newPosition);
            field_.setEntity(player);
            return true;
        } else if (field_.checkPosition(newPosition, player.getTargetIcon())) {
            throw new PlayerGetGoalException();
        }
        return false;
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
            if ((field_.checkPosition(positions[i]))
                 && (map[positions[i].x()][positions[i].y()] != 0) ) { //TODO
                ways.put(positions[i], map[positions[i].x()][positions[i].y()]);
            } else if (field_.checkPosition(positions[i],
                                            enemy.getTargetIcon())) {
                throw new EnemyGetPlayerException();
            }
        }

        ArrayList<Map.Entry<Position, Integer>> list = sortMapByValue(ways);
        // for (int i = 0; i < list.size(); ++i) {
        //     System.out.printf("\n%d\n", list.get(i).getValue());
        // }
        int index = list.size() - 1;
        while (index >= 0) {
            Position position = list.get(index).getKey();
            if (moveEnemy(enemy, position)) {
                System.out.printf("Enemy move from %d %d to %d %d.\n", pos.x(),
                                  pos.y(), position.x(), position.y());
                index = -1;
            }
            --index;
        }
    }

    private boolean moveEnemy(Enemy enemy, Position newPosition)
        throws EnemyGetPlayerException {
        if (field_.checkPosition(newPosition)) {
            field_.clearPosition(enemy.getPosition());
            enemy.setPosition(newPosition);
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
}
