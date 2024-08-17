
class Program {

    private static final String ARG_PREFIX = "--threadsCount=";
    private static final int ARG_COUNT = 1;
    private static final int ARG_INDEX = 0;

    public static void main(String[] args) {
        if (args.length != ARG_COUNT) {
            System.err.printf("Incorrect arguments count. Expected one.\n");
            System.exit(-1);
        }

        if (args[ARG_INDEX].length() <= ARG_PREFIX.length()) {
            System.err.printf(
                "Illegal argument. Expect: --threadsCount=<int>.\n");
            System.exit(-1);
        }

        int threadsCount = 0;
        try {
            threadsCount = Integer.parseInt(
                args[ARG_INDEX].substring(ARG_PREFIX.length()));
        } catch (NumberFormatException e) {
            System.out.printf("Argument is not number - %s.\n", e.getMessage());
            System.exit(-1);
        }

        ThreadsService service = new ThreadsService(threadsCount);
        service.run();
    }
}
