
import java.util.Random;

public class CommonResource {

    private static final int MAX_ABS_VALUE = 1000;
    private int size_;
    private Integer[] array_;
    private int sum_;

    public CommonResource(int size) {
        size_ = size;
        sum_ = 0;
        array_ = new Integer[size_];
        randomize();
    }

    public int size() { return size_; }

    public Integer[] getData() { return array_; }

    public void addSum(int number) { sum_ += number; }

    public int getSum() { return sum_; }

    private int generateNotRandomNumber() {
        return ((new Random()).nextInt() / (1 + MAX_ABS_VALUE));
    }

    private void randomize() {
        for (int i = 0; i < size_; ++i) {
            array_[i] = generateNotRandomNumber();
        }
    }
}
