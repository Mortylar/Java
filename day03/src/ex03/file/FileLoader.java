package file;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileLoader {

    private String urlString_;
    private Path targetName_;

    public FileLoader(String url) {
        urlString_ = url;
        targetName_ = Path.of(url).getFileName();
    }

    public void loadFile() {
        URI uri = URI.create(urlString_.toString());
        URL url;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            System.err.printf("%s\n", e.getMessage());
            return;
        }
        try (InputStream stream = url.openStream()) {
            Files.copy(stream, targetName_,
                       StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.printf("%s\n", e.getMessage());
        }
    }
}
