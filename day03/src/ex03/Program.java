import java.lang.InterruptedException;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;


import file.FileLoader;

class Program {

    public static void main(String[] args) {
        int threadsCount = 3;
        ThreadsService service = new ThreadsService(threadsCount);
				service.run();
    }
}
