import number.Number;

class Program {

    public static void main(String[] args) {
		  Number n = new Number();
		  final int lastElement = 42;
		  int coffeeRequestCount = 0;
		  int x = n.readNumber();

		  while (x != lastElement) {
		    n.setNumber(x);
			  n.setNumber(n.digitsSum());
			  if (n.isPrime()) {
					++coffeeRequestCount;
				}
			  x = n.readNumber();
		  }
		  System.out.printf("Count of coffee-request - %d\n", coffeeRequestCount);
    }
}
