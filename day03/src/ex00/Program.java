import java.lang.InterruptedException;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;

class Program {

    private static final String ARGUMENT_PREFIX = "--count=";

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        mainThread.setName("Human");
        if (args.length == 0) {
            System.err.print("No arguments found - expected one.\n");
            System.exit(-1);
        }

        int inputNumber = Integer.parseInt(
            args[0].substring(ARGUMENT_PREFIX.length(), args[0].length()));

        Runnable egg = () -> {
            for (int i = 0; i < inputNumber; ++i) {
                System.out.printf("%s\n", Thread.currentThread().getName());
                try {
                    Thread.currentThread().sleep(1);
                } catch (InterruptedException e) {
                    System.err.printf("%s\n", e.getMessage());
                }
            }
        };

        Thread eggThread = new Thread(egg, "Egg");
        Thread henThread = new Thread(egg, "Hen");
        Thread mainDupletThread = new Thread(egg, "Aboba");

        eggThread.start();
        henThread.start();
        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            System.err.printf("%s\n", e.getMessage());
        }
        mainDupletThread.run();
    }
}
