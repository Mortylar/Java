
import file.FReader;
import file.FileLoader;
import file.URLStore;
import java.lang.InterruptedException;
import java.lang.Runnable;
import java.lang.Thread;

public class ThreadsService {

    static private final String URL_FILE = "files_urls.txt";

    private URLStore store_;
    private int threadsCount_;

    public ThreadsService(int threadsCount) {
        threadsCount_ = threadsCount;
        store_ = new URLStore();
    }

    public void run() {
        store_.readFromFile(URL_FILE);
        int baseStoreSize = store_.size();

        for (int i = 0; i < threadsCount_; ++i) {
            Thread thread =
                new Thread(new DownloadsThread(store_, baseStoreSize));
            thread.start();
        }
    }
}

class DownloadsThread implements Runnable {

    private static final String START_STATUS = "start";
    private static final String FINISH_STATUS = "finish";

    private URLStore store_;
    private int baseStoreSize_;
    private int fileNumber_;
    private static boolean accessStatus_ = true;

    public DownloadsThread(URLStore store, int size) {
        store_ = store;
        baseStoreSize_ = size;
        fileNumber_ = 0;
    }

    public void run() {
        while (true) {
            int fileNumber;
            String url;
            synchronized (store_) { url = getUrl(); }
            if (url != null) {
                logging(fileNumber_, START_STATUS);
                FileLoader loader = new FileLoader(url);
                loader.loadFile();
                logging(fileNumber_, FINISH_STATUS);
            } else {
                return;
            }
        }
    }

    public synchronized String getUrl() {
        if (!accessStatus_) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.printf("%s\n.", e.getMessage());
            }
        }
        accessStatus_ = false;
        fileNumber_ = baseStoreSize_ - store_.size() + 1;
        String url = store_.pop();
        accessStatus_ = true;
        notifyAll();
        return url;
    }

    public void logging(int fileNumber, String status) {
        System.out.printf("%s %s download file number %d\n",
                          Thread.currentThread().getName(), status, fileNumber);
    }
}
