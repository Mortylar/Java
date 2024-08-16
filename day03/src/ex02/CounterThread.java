import java.lang.Runnable;
import java.lang.Thread;

public class CounterThread implements Runnable {

    private CommonResource resource_;
    private int startIndex_;
    private int endIndex_;

    public CounterThread(CommonResource res, int startIndex, int endIndex) {
        resource_ = res;
        startIndex_ = startIndex;
        ;
        endIndex_ = endIndex;
        ;
    }

    @Override
    public void run() {
        int sum = 0;
        int size = resource_.size();
        for (int i = startIndex_; (i < size) && (i <= endIndex_); ++i) {
            sum += resource_.getData()[i];
        }
        resource_.addSum(sum);
        printResult(sum, (startIndex_ < size ? startIndex_ : (size - 1)),
                    (endIndex_ < size ? endIndex_ : (size - 1)));
    }

    private void printResult(int number, int startPos, int endPos) {
        System.out.printf("%s: from %d to %d sum is %d\n",
                          Thread.currentThread().getName(), startPos, endPos,
                          number);
    }
}
