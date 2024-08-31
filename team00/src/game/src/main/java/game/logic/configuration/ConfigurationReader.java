package game.logic.configuration;

// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.io.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ConfigurationReader {

    private static final String FILE_PREFIX = "/application-";
    private static final String FILE_POSTFIX = ".properties";

    private static final String DELIMITER = " = ";

    private String fileName_;
    private String[] data_;

    public ConfigurationReader(String profile) {
        fileName_ = FILE_PREFIX + profile + FILE_POSTFIX;
    }

    public void read() {
        System.out.printf("\nname = %s\n", fileName_);
        try {
            data_ = new BufferedReader(
                        new InputStreamReader(
                            getClass().getResourceAsStream(fileName_),
                            StandardCharsets.UTF_8))
                        .lines()
                        .toArray(String[] ::new);
            //.collect(Collectors.joining(System.lineSeparator()));
            // System.out.printf("\n%s\n", data_);
        } catch (Exception e) {
            System.out.printf("\na%s\n", e.getMessage());
        }

        for (int i = 0; i < data_.length; ++i) {
            System.out.printf("\n%s\n", data_[i]);
        }
    }

    public char getEnemyIcon() {
        final int enemyIconIndex = 0;
        String[] str = data_[enemyIconIndex].split(DELIMITER);
        if (str.length != 2) { // TODO
            // TODO throw InvalidConficurationFileException
        }
        return str[1].charAt[0];
    }
}
