

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
        }
    }

    private void createEnemyArray() {
        int enemiesCount = conf_.getEnemyCount();
        enemyArr_ = new Enemy[enemiesCount];
        for (int i = 0; i < enemiesCount; ++i) {
            enemyArr_[i] = new Enemy(conf_.getEnemyIcon());
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

    public boolean movePlayer(String direction, int id) {
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
        }
        return false;
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
