package game.logic.configuration;

import game.logic.exception.IllegalConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ConfigurationReader {

    public static final int ENEMY_INDEX = 0;
    public static final int PLAYER_INDEX = 1;
    public static final int WALL_INDEX = 2;
    public static final int GOAL_INDEX = 3;
    public static final int EMPTY_INDEX = 4;

    private static final int COLOR_SHIFT = 5;
    private static final int VALUE_POSITION = 0;

    private static final String FILE_PREFIX = "/application-";
    private static final String FILE_POSTFIX = ".properties";

    private static final String DELIMITER = " = ";

    private String fileName_;
    private String[] data_;

    public ConfigurationReader(String profile) {
        fileName_ = FILE_PREFIX + profile + FILE_POSTFIX;
    }

    public void read() throws IllegalConfigurationException {
        try {
            data_ = new BufferedReader(
                        new InputStreamReader(
                            getClass().getResourceAsStream(fileName_),
                            StandardCharsets.UTF_8))
                        .lines()
                        .toArray(String[] ::new);
        } catch (Exception e) {
            throw new IllegalConfigurationException();
        }

        extractValues();
    }

    private void extractValues() throws IllegalConfigurationException {
        try {
            for (int i = 0; i < data_.length; ++i) {
                data_[i] = data_[i].split(DELIMITER)[1].trim();
                if (data_[i].isEmpty()) {
                    data_[i] = " ";
                }
            }
        } catch (Exception e) {
            throw new IllegalConfigurationException();
        }
    }

    public char value(int index) { return data_[index].charAt(VALUE_POSITION); }

    public String getColor(int index) { return data_[COLOR_SHIFT + index]; }
}
