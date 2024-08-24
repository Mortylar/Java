public class Position {

    public int x_ = 0;
    public int y_ = 0;

    public Position() {}

    public Position(Position position) {
        x_ = position.x_;
        y_ = position.y_;
    }

    public Position(int x, int y) {
        x_ = x;
        y_ = y;
    }

    public void set(int x, int y) {
        x_ = x;
        y_ = y;
    }

    public Position move(int dx, int dy) {
        x_ += dx;
        y_ += dy;
        return this;
    }

    public Position move(Position direction) {
        x_ += direction.x_;
        y_ += direction.y_;
        return this;
    }

    public int x() { return x_; }

    public int y() { return y_; }
}
