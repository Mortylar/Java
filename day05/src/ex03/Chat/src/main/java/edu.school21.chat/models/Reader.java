package edu.school21.chat.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Reader {

    public Reader(){};

    public String read(String fileName) throws IOException {
        return new BufferedReader(new InputStreamReader(
                                      getClass().getResourceAsStream(fileName),
                                      StandardCharsets.UTF_8))
            .lines()
            .collect(Collectors.joining("\n"));
    }
}
