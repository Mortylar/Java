// import java.io.File;
import file.FileService;
import java.io.FileNotFoundException;
import java.io.IOException;
// import java.util.Scanner;
// import java.io.FileInputStream;

class Program {

    public static void main(String[] args)
        throws FileNotFoundException, IOException {
        if (args.length != 1) {
            System.err.print("Illegal arguments count!\n");
            System.exit(-1);
        }

        FileService service = new FileService(args[0]);
        service.findFileType();
        System.out.printf("\n**%s**\n", service.getFileTypeOrReturnNull());
    }
}
