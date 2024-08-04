import number.Number;

class Program {

    private static final int START_CORRECT_VALUE = 2;
		private static final int ILLEGAL_ARGUMENT_ERROR_CODE = -1;

    public static void main(String[] args) {
        Number n = new Number();
        int a = n.readNumber();

        if (a <= START_CORRECT_VALUE) { 
            System.err.println("Illegal Argument");
            System.exit(ILLEGAL_ARGUMENT_ERROR_CODE);
        } else {
            boolean isPrime;
            n.setNumber(a);
            isPrime = n.isPrime();
            System.out.print(isPrime);
            System.out.print(" ");
            n.writeNumber(n.getIterationCount());
        }
    }
}
