
import java.lang.Thread;
import java.lang.Runnable;

import file.FReader;
import file.FileLoader;
import file.URLStore;


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
				System.out.printf("Store size = %d\n", baseStoreSize);

				for (int i = 0; i < threadsCount_; ++i) {
					Thread thread = new Thread(new DownloadsThread(store_, baseStoreSize));
					thread.start();
				}
		}


}

class DownloadsThread implements Runnable {

    private static final String START_STATUS = "start";
		private static final String FINISH_STATUS = "finish";

    private URLStore store_;
    int baseStoreSize_;

		public DownloadsThread(URLStore store, int size) {
		    store_ = store;
				baseStoreSize_ = size;
		}

		public void run() {
        while(true) {
		        int fileNumber = baseStoreSize_ - store_.size() + 1;
						String url = store_.pop();
						if (url != null) {
                logging(fileNumber, START_STATUS);
						    FileLoader loader = new FileLoader(url);
								loader.loadFile();
								logging(fileNumber, FINISH_STATUS);
						} else {
						   return; 
						}
		    }
		}

		public void logging(int fileNumber, String status) {
		    System.out.printf("%s %s download file number %d\n", Thread.currentThread().getName(), status, fileNumber);
		}
}
