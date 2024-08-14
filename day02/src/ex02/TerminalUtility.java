
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;
import java.nio.file.NoSuchFileException;
import java.nio.file.AccessDeniedException;
import java.io.UncheckedIOException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class TerminalUtility {

    private static final String MOVE = "mv";
		private static final String LIST_FILES = "ls";
    private static final String CHANGE_DIRECTORY = "cd";
		private static final String EXIT = "exit";

		private static final int BYTES_IN_KILOBYTES = 1000;

		private Scanner scanner_;
		private Path currentDir_;

		public TerminalUtility(String path) {
        scanner_ = new Scanner(System.in);
		    currentDir_ = Paths.get(path).toAbsolutePath();
		}


		public void run() {
        printCurrentDirectory();

        String input = scanner_.nextLine();

				while (!input.equals(EXIT)) {
				    Scanner stringScanner = new Scanner(input).useDelimiter(" ");
						if (stringScanner.hasNext()) {
				        String command = stringScanner.next();

				        if (command.equals(LIST_FILES)) {
                    listFiles(stringScanner);
    				    } else if (command.equals(CHANGE_DIRECTORY)) {
		                changeDirectory(stringScanner);
				    				printCurrentDirectory();
    				    } else if (command.equals(MOVE)) {
		    		        move(stringScanner);
				        }	else if (!command.equals(EXIT)) {
				            printCommandNotFoundError(command);
				        }
						}
						input = scanner_.nextLine();
				}
		}


    private void changeDirectory(Scanner scanner) {
        String directory;
		    if (scanner.hasNext()) {
				    directory = scanner.next();
						if (scanner.hasNext()) {
						    printTooManyArgumentsError(CHANGE_DIRECTORY);
								return;
						}
						changeDirectory(Paths.get(directory));
				} else {
				    changeDirectory(Paths.get("/"));
				}
		}

    private void changeDirectory(Path path) {
		    Path localPath = getAbsolutePath(path);

				if(Files.notExists(localPath)) {
				    System.err.printf("cd: no such file or directory: %s\n", localPath.toString());
						return;
				}
				if(!(Files.isDirectory(localPath))) {
				    System.err.printf("cd: not a directory: %s\n", localPath.toString());
						return;
				}
			
				currentDir_ = localPath;
		}


		private void listFiles(Scanner scanner) {
        ArrayList<String> directory = new ArrayList<String>();
				while (scanner.hasNext()) {
				    directory.add(scanner.next());
				}
				if(directory.isEmpty()) {
				    listFiles(currentDir_);
				} else if (directory.size() == 1) {
				    listFiles(Paths.get(directory.get(0)));
				} else {
				    for (int i = 0; i < directory.size(); ++i) {
						    listManyFiles(Paths.get(directory.get(i)));
						}
				}
		}

		private void listManyFiles(Path path) {
		    Path absPath = getAbsolutePath(path);

				if (Files.notExists(absPath)) {
				    System.err.printf("ls: %s: no such file or directory\n\n", path.toString());
						return;
				}  
				System.out.printf("%s:\n", path.toString());
				listFiles(path);
				System.out.printf("\n");
		}

		private void listFiles(Path path) {	
		    Path absPath = getAbsolutePath(path);

				if (Files.notExists(absPath)) {
				    System.err.printf("ls: %s: no such file or directory\n\n", path.toString());
						return;
				}

				try (DirectoryStream<Path> files = Files.newDirectoryStream(absPath)) {
				    for (Path file: files) {
						    System.out.printf("%s ", file.getFileName().toString());
								try {
								    System.out.printf("%d KB", getFileSize(file));
								} catch (IOException | UncheckedIOException e) {
                    System.out.printf("??? KB");
								}
								System.out.print("\n");
						}
				} catch (IOException e) {
				    //e.printStackTrace(); //TODO remove
						System.out.printf("ls: %s: Permission denied\n", absPath.getFileName().toString());
				}
		}

		private void move(Scanner scanner) {
        String firstFile;
				String secondFile;

        if (!(scanner.hasNext())) {
				    System.err.print("mv: no arguments found\n");
						return;
				}

				firstFile = scanner.next();
				
				if (!scanner.hasNext()) {
				    System.err.print("mv: get one argument - expected two\n");
						return;
				}

				secondFile = scanner.next();

				if (scanner.hasNext()) {
				    System.err.print("mv: too many arguments\n");
						return;
				}
				move(getAbsolutePath(Paths.get(firstFile)), getAbsolutePath(Paths.get(secondFile)));
		}

		private void move(Path source, Path target) {
        if(Files.notExists(source)) {
				    System.err.printf("mv: %s: no such file or directory\n", source.getFileName().toString());
						return;
				}
				try {
            Files.move(source, target);
				} catch (IOException e) {
				    e.printStackTrace();
				}
		}


    private long getFileSize(Path path) throws NoSuchFileException, IOException {
		    return (Files.walk(path).filter(file->file.toFile().isFile()).mapToLong(file->file.toFile().length()).sum()) / BYTES_IN_KILOBYTES;
		}

    private Path getAbsolutePath(Path almostAbsolutePath) {
		    if (almostAbsolutePath.isAbsolute()) {
				    return almostAbsolutePath;
				}
				return currentDir_.resolve(almostAbsolutePath.toString()).normalize();
		}

		private void printCommandNotFoundError(String command) {
		    System.err.printf("command not found: %s\n", command);
		}
   
	  private void printTooManyArgumentsError(String command) {
		    System.err.printf("%s: too many arguments\n", command);
		}

		private void printCurrentDirectory() {
        if (currentDir_.toString().equals("/")) {    
		        System.out.printf("%s\n", currentDir_.toString());
				} else {
		        System.out.printf("%s/\n", currentDir_.toString());
        }
		}
}

