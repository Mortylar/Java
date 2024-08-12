// import file.FileService;
import java.io.FileNotFoundException;
import java.io.IOException;

class Program {

    private static final int FILES_COUNT = 2;

    public static void main(String[] args) {
        if (args.length != FILES_COUNT) {
            System.err.printf("Illegal arguments count - expected 2 files\n");
            System.exit(-1);
        }

        AnalizeService service = new AnalizeService(args[0], args[1]);
        service.runAnalize();
        service.printReport();
    }
}
