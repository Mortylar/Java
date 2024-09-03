package game.logic.position;

public class Position {

    public static final Position UP = new Position(-1, 0);
    public static final Position DOWN = new Position(1, 0);
    public static final Position RIGHT = new Position(0, 1);
    public static final Position LEFT = new Position(0, -1);

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
