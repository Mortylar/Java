

public class EntityService {

    private int playersCount_;
    private char playerIcon_;

    private int enemiesCount_;
    private char enemyIcon_;

    private int obstaclesCount_;
    private char obstacleIcon_;

    private int targetsCount_;
    private char targetIcon_;

    private Player[] playerArr_;
    private Enemy[] enemyArr_;
    private Obstacle[] obstacleArr_;
    private Target[] targetArr_;

    public void setPlayersData(int count, char icon) {
        playersCount_ = count;
        playerIcon_ = icon;
    }

    public void setEnemiesData(int count, char icon) {
        enemiesCount_ = count;
        enemyIcon_ = icon;
    }

    public void setObstaclesData(int count, char icon) {
        obstaclesCount_ = count;
        obstacleIcon_ = icon;
    }

    public void setTargetsData(int count, char icon) {
        targetsCount_ = count;
        targetIcon_ = icon;
    }

    public void build() {
        createPlayerArray();
        createEnemyArray();
        createObstacleArray();
        createTargetArray();
    }

    private void createPlayerArray() {
        playerArr_ = new Player[playersCount_];
        for (int i = 0; i < playersCount_; ++i) {
            playerArr_[i] = new Player(playerIcon_);
        }
    }

    private void createEnemyArray() {
        enemyArr_ = new Enemy[enemiesCount_];
        for (int i = 0; i < enemiesCount_; ++i) {
            enemyArr_[i] = new Enemy(enemyIcon_);
        }
    }

    private void createObstacleArray() {
        obstacleArr_ = new Obstacle[obstaclesCount_];
        for (int i = 0; i < obstaclesCount_; ++i) {
            obstacleArr_[i] = new Obstacle(obstacleIcon_);
        }
    }

    private void createTargetArray() {
        targetArr_ = new Target[targetsCount_];
        for (int i = 0; i < targetsCount_; ++i) {
            targetArr_[i] = new Target(targetIcon_);
        }
    }

    public Entity[] getEntityArr(char icon) {
        if (icon == playerIcon_) {
            return playerArr_;
        } else if (icon == enemyIcon_) {
            return enemyArr_;
        } else if (icon == obstacleIcon_) {
            return obstacleArr_;
        } else if (icon == targetIcon_) {
            return targetArr_;
        } else {
            System.err.printf("Entity with icon %s not found.\n", icon);
            System.exit(-1);
            return null;
        }
    }
}
