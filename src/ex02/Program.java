import number.Number;

class Program {

  public static void main(String[] args) {
		Number n = new Number();
		final int last_element = 42;
		int coffee_request_count = 0;
		int x = n.ReadNumber();

		while (x != last_element) {
		  n.SetNumber(x);
			n.SetNumber(n.DigitsSum());
			if (n.IsPrime()) ++coffee_request_count;
			x = n.ReadNumber();
		}
		System.out.printf("Count of coffee-request - %d\n", coffee_request_count);
  }
}
