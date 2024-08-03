package cursor;

public class Cursor {

    private static final char esc_ = 0x1B;
	  private static final char up_ = 'A';
	  private static final char down_ = 'B';
	  private static final char forward_ = 'C';
	  private static final char backward_ = 'D';

	  public void moveUp(int length) {
	      System.out.printf("%c[%d%c", esc_, length, up_);
	  }

	  public void moveDown(int length) {
	      System.out.printf("%c[%d%c", esc_, length, down_);
	  }

	  public void moveForward(int length) {
	      System.out.printf("%c[%d%c", esc_, length, forward_);
	  }

	  public void moveBackward(int length) {
	      System.out.printf("%c[%d%c", esc_, length, backward_);
	  }


}
