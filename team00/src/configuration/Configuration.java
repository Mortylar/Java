//import java.io.Serializable;

public class Configuration {//implements Serializable{

    private static char enemyIcon_;
    private static char playerIcon_;
    private static char wallIcon_;
    private static char goalIcon_;
    private static char emptyIcon_;

    private static String enemyColor_;
    private static String playerColor_;
    private static String wallColor_;
    private static String goalColor_;

    private static int enemyCount_;
    private static int playerCount_;
    private static int wallCount_;
    private static int goalCount_;


    public Configuration() { //TODO
        enemyIcon_ = 'X';
        playerIcon_ = '@';
        wallIcon_ = '#';
        goalIcon_ = 'O';
        emptyIcon_ = ' ';
    }


    public char getEnemyIcon() {
        return enemyIcon_;
    }

    public char getPlayerIcon() {
        return playerIcon_;
    }

    public char getWallIcon() {
        return wallIcon_;
    }

    public char getGoalIcon() {
        return goalIcon_;
    }

    public char getEmptyIcon() {
        return emptyIcon_;
    }

    public int getEnemyCount() {
        return enemyCount_;
    }

    public int getPlayerCount() {
        return playerCount_;
    }

    public int getWallCount() {
        return wallCount_;
    }

    public int getGoalCount() {
        return goalCount_;
    }

    public void setEnemyIcon(char icon) {
        enemyIcon_ = icon;
    }

    public void setPlayerIcon(char icon) {
        playerIcon_ = icon;
    }

    public void setWallIcon(char icon) {
        wallIcon_ = icon;
    }

    public void setGoalIcon(char icon) {
        goalIcon_ = icon;
    }

    public void setEmptyIcon(char icon) {
        emptyIcon_ = icon;
    }

    public void setEnemyCount(int count) {
        enemyCount_ = count;
    }

    public void setPlayerCount(int count) {
        playerCount_ = count;
    }

    public void setWallCount(int count) {
        wallCount_ = count;
    }

    public void setGoalCount(int count) {
        goalCount_ = count;
    }
}
