package file;

import java.util.ArrayList;

public class URLStore {

    private ArrayList<String> urlList_;

    public URLStore() { urlList_ = new ArrayList<String>(); }

    public void readFromFile(String file) {
        FReader reader = new FReader(file);
        reader.openFile();
        String line = reader.getNextLineOrReturnNull();
        while (line != null) {
            if (!line.isEmpty()) {
                urlList_.add(line);
            }
            line = reader.getNextLineOrReturnNull();
        }
        reader.close();
    }

    public int size() { return urlList_.size(); }

    public String pop() {
        int ind = urlList_.size();
        return ((ind == 0) ? null : urlList_.remove(ind - 1));
    }
}
