import number.Number;

class Program {

    public static void main(String[] args) {
        Number n = new Number();
        int a = n.readNumber();

        if (a <= 1) { 
            System.err.println("Illegal Argument");
            System.exit(-1);
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
