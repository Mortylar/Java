public class Position {

    public int x_ = 0;
    public int y_ = 0;

    public Position() {}

    public Position(int x, int y) {
        x_ = x;
        y_ = y;
    }

    public void set(int x, int y) {
        x_ = x;
        y_ = y;
    }

    public void move(int dx, int dy) {
        x_ += dx;
        y_ += dy;
    }

    public int x() { return x_; }

    public int y() { return y_; }
}
