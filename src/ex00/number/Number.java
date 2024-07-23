package number;

public class Number {

  private int number_ = 479599;

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
