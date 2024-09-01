package game.logic.configuration;

import game.logic.exception.IllegalConfigurationException;

public class Configuration {

    public static String WHITE_COLOR = "WHITE";
    public static String BLACK_COLOR = "BLACK";
    public static String GREEN_COLOR = "GREEN";
    public static String BLUE_COLOR = "BLUE";
    public static String RED_COLOR = "RED";
    public static String YELLOW_COLOR = "YELLOW";
    public static String CYAN_COLOR = "CYAN";
    public static String MAGENTA_COLOR = "MAGENTA";

    public static String DEV_PROFILE = "dev";
    private static String profile_;

    private static char enemyIcon_;
    private static char playerIcon_;
    private static char wallIcon_;
    private static char goalIcon_;
    private static char emptyIcon_;

    private static String enemyColor_;
    private static String playerColor_;
    private static String wallColor_;
    private static String goalColor_;
    private static String emptyColor_;

    private static int enemyCount_;
    private static int playerCount_;
    private static int wallCount_;
    private static int goalCount_;

    private static int fieldSize_;

    public Configuration() {}

    private void setDefaultConfiguration() {
        enemyIcon_ = 'X';
        playerIcon_ = '@';
        wallIcon_ = '#';
        goalIcon_ = 'O';
        emptyIcon_ = ' ';

        enemyColor_ = "RED";
        playerColor_ = "GREEN";
        wallColor_ = "MAGENTA";
        goalColor_ = "BLUE";
        emptyColor_ = "YELLOW";
    }

    public void configure() {
        ConfigurationReader reader = new ConfigurationReader(profile_);
        try {
            reader.read();
        } catch (IllegalConfigurationException e) {
            System.err.printf("\nWarning: configuration file is incorrect.\n");
            setDefaultConfiguration();
            return;
        }
        enemyIcon_ = reader.value(reader.ENEMY_INDEX);
        playerIcon_ = reader.value(reader.PLAYER_INDEX);
        wallIcon_ = reader.value(reader.WALL_INDEX);
        goalIcon_ = reader.value(reader.GOAL_INDEX);
        emptyIcon_ = reader.value(reader.EMPTY_INDEX);

        enemyColor_ = reader.getColor(reader.ENEMY_INDEX);
        playerColor_ = reader.getColor(reader.PLAYER_INDEX);
        wallColor_ = reader.getColor(reader.WALL_INDEX);
        goalColor_ = reader.getColor(reader.GOAL_INDEX);
        emptyColor_ = reader.getColor(reader.EMPTY_INDEX);
    }

    public char getEnemyIcon() { return enemyIcon_; }

    public char getPlayerIcon() { return playerIcon_; }

    public char getWallIcon() { return wallIcon_; }

    public char getGoalIcon() { return goalIcon_; }

    public char getEmptyIcon() { return emptyIcon_; }

    public int getEnemyCount() { return enemyCount_; }

    public int getPlayerCount() { return playerCount_; }

    public int getWallCount() { return wallCount_; }

    public int getGoalCount() { return goalCount_; }

    public int getFieldSize() { return fieldSize_; }

    public String getEnemyColor() { return enemyColor_; }

    public String getPlayerColor() { return playerColor_; }

    public String getWallColor() { return wallColor_; }

    public String getGoalColor() { return goalColor_; }

    public String getEmptyColor() { return emptyColor_; }

    public String getProfile() { return profile_; }

    public boolean isDevProfile() { return profile_.equals(DEV_PROFILE); }

    public void setEnemyIcon(char icon) { enemyIcon_ = icon; }

    public void setPlayerIcon(char icon) { playerIcon_ = icon; }

    public void setWallIcon(char icon) { wallIcon_ = icon; }

    public void setGoalIcon(char icon) { goalIcon_ = icon; }

    public void setEmptyIcon(char icon) { emptyIcon_ = icon; }

    public void setEnemyCount(int count) { enemyCount_ = count; }

    public void setPlayerCount(int count) { playerCount_ = count; }

    public void setWallCount(int count) { wallCount_ = count; }

    public void setGoalCount(int count) { goalCount_ = count; }

    public void setFieldSize(int size) { fieldSize_ = size; }

    public void setEnemyColor(String color) { enemyColor_ = color; }

    public void setPlayerColor(String color) { playerColor_ = color; }

    public void setWallColor(String color) { wallColor_ = color; }

    public void setGoalColor(String color) { goalColor_ = color; }

    public void setEmptyColor(String color) { emptyColor_ = color; }

    public void setProfile(String profile) { profile_ = profile; }
}
