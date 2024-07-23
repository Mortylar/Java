package number;

import java.util.Scanner;
import static java.lang.Math.sqrt;

public class Number {

  private int number_;
	private int iteration_count_;

	public Number() {
	  SetNumber(0);
	}

	public Number(int number) {
	  SetNumber(number);
	}

	public void SetNumber(int number) {
	  number_ = number;
		iteration_count_ = 0;
	}

	public int ReadNumber() {
	  Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}

	public void WriteNumber(int number) {
	  System.out.println(number);
	}

  public int GetIterationCount() {
	  return iteration_count_;
	}

	public boolean IsPrime() {
	  boolean is_prime = true;
		iteration_count_ = 0;
		int div = 2;

		while ((div <= sqrt(number_ )) && (is_prime)) {
			if ((number_ % div) == 0) is_prime = false;
		  ++div;
			++iteration_count_;
		}
		return is_prime;
	}

	public int DigitsSum() {
		int tmp = number_;
		int result = 0;
		while (tmp > 0) {
		  result += tmp%10;
			tmp = tmp/10;
		}
	  return result;
	}

}
