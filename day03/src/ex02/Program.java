class Program {

    private static final String ARRAY_PREFIX = "--arraySize=";
    private static final int ARRAY_ARG_POSITION = 0;
    private static final String THREAD_PREFIX = "--threadsCount=";
    private static final int THREAD_ARG_POSITION = 1;
    private static final int ARGS_COUNT = 2;

    public static void main(String[] args) {

        if (args.length != ARGS_COUNT) {
            System.err.printf("Invalid arguments count - expected 2.\n");
            System.exit(-1);
        }

        if ((args[ARRAY_ARG_POSITION].length() <= ARRAY_PREFIX.length()) ||
            (args[THREAD_ARG_POSITION].length() <= THREAD_PREFIX.length())) {
            System.err.printf("Invalid argument.\n");
            System.err.printf("Usage --arraySize=int --threadsCount=int\n");
            System.exit(-1);
        }

        if ((!ARRAY_PREFIX.equals(args[ARRAY_ARG_POSITION].substring(
                0, ARRAY_PREFIX.length()))) ||
            (!THREAD_PREFIX.equals(args[THREAD_ARG_POSITION].substring(
                0, THREAD_PREFIX.length())))) {
            System.err.printf("Invalid argument.\n");
            System.err.printf("Usage --arraySize=int --threadsCount=int\n");
            System.exit(-1);
        }

        int arraySize = Integer.parseInt(args[ARRAY_ARG_POSITION].substring(
            ARRAY_PREFIX.length(), args[ARRAY_ARG_POSITION].length()));
        int threadsCount = Integer.parseInt(args[THREAD_ARG_POSITION].substring(
            THREAD_PREFIX.length(), args[THREAD_ARG_POSITION].length()));

        CommonResource resource = new CommonResource(arraySize);

        int sum = 0;
        for (int i = 0; i < resource.size(); ++i) {
            sum += resource.getData()[i];
        }

        System.out.printf("\nSum: %d\n", sum);

        int threadWide = 1 + (arraySize / threadsCount);
        if (arraySize == threadsCount) {
            threadWide = 1;
        }

        for (int i = 0; i < threadsCount; ++i) {
            Thread thread = new Thread(new CounterThread(
                resource, i * threadWide, ((i + 1) * threadWide) - 1));
            thread.setName("Thread " + (i + 1));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.printf("%s\n", e.getMessage());
            }
        }

        System.out.printf("Sum by threads: %d\n", resource.getSum());
    }
}
