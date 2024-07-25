//import statistic.Statistic;

class test {

  public static void SetCursorPosition(int x, int y) {
    final char escCode = 0x1B;
    System.out.print(String.format("%c[%d;%df", escCode, y, x));
	}

  public static void main(String[] args) {
		System.out.print("AA\n");
		char esc = 0x1B;
		char letter = 'A';
		System.out.printf(("%c[%c"), esc, letter);
		//SetCursorPosition(5,17);
		System.out.print("B");
  }
}
