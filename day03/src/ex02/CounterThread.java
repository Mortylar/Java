import java.lang.Thread;
import java.lang.Runnable;

public class CounterThread implements Runnable {

    private CommonResource resource_;
		private int threadsCount_;
    private int id_;

    public CounterThread(CommonResource res, int threadsCount, int id) {
      resource_ = res;
			id_ = id;
			threadsCount_ = threadsCount;
			id_ = 0;
	  }

		public void setId(int id) {
		    id_ = id;
		}

    @Override
    public void run() {
        int sum = 0;
				int threadsLength = 1 + (resource_.size() / threadsCount_);
				int startThreadPosition = threadsCount_ * id_;
        for (int i = startThreadPosition;
             (i < resource_.size()) && (i < startThreadPosition + threadsLength -1); ++i) {
				    sum += resource_.getData()[i];
				}
        printResult(sum, startThreadPosition, startThreadPosition + threadsLength - 1);
		}

		private void printResult(int number, int startPos, int endPos) {
		    System.out.printf("%s: from %d to %d sum is %d\n", Thread.currentThread().getName(),
                           startPos, endPos, number);
		}

}

