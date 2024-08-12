import file.FileService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

class Program {

    private static final String EXIT_STRING = "42";
    private static Scanner scanner_;

    public static void main(String[] args) {
        scanner_ = new Scanner(System.in);
        String answer = new String(scanner_.nextLine());
        while (!answer.equals(EXIT_STRING)) {
            try {
                FileService service = new FileService(answer);
                service.findFileType();
                if (null == service.getFileTypeOrReturnNull()) {
                    System.out.printf("UNDEFINED\n");
                } else {
                    service.writeFileType();
                    System.out.printf("PROCESSED\n");
                }
            } catch (FileNotFoundException e) {
                System.err.printf("File %s not found or is not a file\n",
                                  answer);
            } catch (IOException e) {
                System.err.printf("File is broken\n");
            }
            answer = scanner_.nextLine();
        }
    }
}
