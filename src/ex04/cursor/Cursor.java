package cursor;

public class Cursor {

  private static final char esc_ = 0x1B;
	private static final char up_ = 'A';
	private static final char down_ = 'B';
	private static final char forward_ = 'C';
	private static final char backward_ = 'D';

	public void MoveUp(int length) {
	  System.out.printf("%c[%d%c", esc_, length, up_);
	}

	public void MoveDown(int length) {
	  System.out.printf("%c[%d%c", esc_, length, down_);
	}

	public void MoveForward(int length) {
	  System.out.printf("%c[%d%c", esc_, length, forward_);
	}

	public void MoveBackward(int length) {
	  System.out.printf("%c[%d%c", esc_, length, backward_);
	}


}
