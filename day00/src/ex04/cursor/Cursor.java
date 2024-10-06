package cursor;

public class Cursor {

    private static final char ESC = 0x1B;
    private static final char UP = 'A';
    private static final char DOWN = 'B';
    private static final char FORWARD = 'C';
    private static final char BACKWARD = 'D';

    public void moveUp(int length) {
        System.out.printf("%c[%d%c", ESC, length, UP);
    }

    public void moveDown(int length) {
        System.out.printf("%c[%d%c", ESC, length, DOWN);
    }

    public void moveForward(int length) {
        System.out.printf("%c[%d%c", ESC, length, FORWARD);
    }

    public void moveBackward(int length) {
        System.out.printf("%c[%d%c", ESC, length, BACKWARD);
    }
}
