
import file.FileAnalizator;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AnalizeService {

    private String file1_;
    private String file2_;
    private FileAnalizator analizator_;
    private double similarity_;

    public AnalizeService(String file1, String file2) {
        file1_ = new String(file1);
        file2_ = new String(file2);
    }

    public void runAnalize() {
        analizator_ = new FileAnalizator(file1_, file2_);
        try {
            analizator_.analyze();
        } catch (FileNotFoundException e) {
            System.err.printf("File %s or %s not found.\n", file1_, file2_);
            System.exit(-1);
        } catch (IOException e) {
            System.err.printf("Can't read file %s or %s\n", file1_, file2_);
            System.exit(-1);
        }

        int scalarProduct = vector.VectorMath.scalarProduct(
            analizator_.getFirstVector(), analizator_.getSecondVector());
        double lengthProduct =
            vector.VectorMath.getVectorLength(analizator_.getFirstVector()) *
            vector.VectorMath.getVectorLength(analizator_.getSecondVector());

        similarity_ = scalarProduct / lengthProduct;
    }

    public void printReport() {
        System.out.printf("Similarity = %.2f\n", similarity_);
    }
}
