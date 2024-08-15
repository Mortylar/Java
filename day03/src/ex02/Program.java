import java.lang.InterruptedException;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;

class Program {

    private static final String ARGUMENT_PREFIX = "--count=";

    public static void main(String[] args) {
   
	      int arraySize = 20;
				int threadsCount = 7;

				CommonResource resource = new CommonResource(arraySize);

				int sum = 0;
				for (int i = 0; i < resource.size(); ++i) {
				    sum += resource.getData()[i];
				}

				System.out.printf("\nsum = %d\n", sum);

				for (int i = 0; i < threadsCount; ++i) {
				    Thread thread = new Thread(new CounterThread(resource, threadsCount, i));
            thread.start();
				}

	  

		}

}

