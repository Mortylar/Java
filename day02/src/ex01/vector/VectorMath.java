package vector;

import java.util.ArrayList;

public class VectorMath {

    public static int scalarProduct(ArrayList<Integer> a,
                                    ArrayList<Integer> b) {
        int size = (a.size() <= b.size()) ? a.size() : b.size();
        int result = 0;
        for (int i = 0; i < size; ++i) {
            result += (a.get(i) * b.get(i));
        }
        return result;
    }

    public static double getVectorLength(ArrayList<Integer> x) {
        double squareSum = 0;
        for (int i = 0; i < x.size(); ++i) {
            squareSum += (x.get(i) * x.get(i));
        }
        return Math.sqrt(squareSum);
    }
}
