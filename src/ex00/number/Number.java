package number;

import java.util.Scanner;

public class Number {

  private int number_;

	public Number() {
	  SetNumber(0);
	}

	public Number(int number) {
	  SetNumber(number);
	}

	public void SetNumber(int number) {
	  number_ = number;
	}

	public int ReadNumber() {
	  Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}

	public void WriteNumber(int number) {
	  System.out.println(number);
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
