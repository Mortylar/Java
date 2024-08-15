import java.lang.InterruptedException;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;

class Program {

    private static final String ARGUMENT_PREFIX = "--count=";

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        mainThread.setName("Human");
        if (args.length == 0) {
            System.err.print("No arguments found - expected one.\n");
            System.exit(-1);
        }

        int inputNumber = Integer.parseInt(
            args[0].substring(ARGUMENT_PREFIX.length(), args[0].length()));
    
		    Printer printer = new Printer(inputNumber);

        Runnable egg = () -> {
            for (int i = 0; i < inputNumber; ++i) {
                printer.print(i);
            }
        };

        Thread eggThread = new Thread(egg, "Egg");
        Thread henThread = new Thread(egg, "Hen");

        eggThread.start();
        henThread.start();
    }
}

class Printer {

    private int limitNumber_;

    public Printer(int number) {
        limitNumber_ = number - 1;
		}

		public synchronized void print(int limitNumber) {
		  System.out.printf("%s\n", Thread.currentThread().getName());
			try {
          notifyAll();
			    if (limitNumber < limitNumber_) wait();
			} catch (InterruptedException e) {
			    System.err.printf("%s\n", e.getMessage());
			}
		}


}
