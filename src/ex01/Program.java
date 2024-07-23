import number.Number;

class Program {

  public static void main(String[] args) {
		Number n = new Number();
		int a = n.ReadNumber();
    if (a <= 1) { 
		  System.err.println("Illegal Argument");
			System.exit(-1);
		} else {
		  n.SetNumber(a);
			boolean is_prime = n.IsPrime();
			System.out.print(is_prime);
			System.out.print(" ");
			n.WriteNumber(n.GetIterationCount());
    }
  }
}
