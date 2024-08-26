package chaselogic.wave;

import chaselogic.position.Position;

public class Wave {

    private static int[][] field_;
    private static int upBorder_;
    private static int downBorder_;

    private Position position_;
    private int number_;

    public Wave(int[][] field, int up, int down) {
        field_ = field;
        ;
        upBorder_ = up;
        downBorder_ = down;
    }

    public void createWaves(Position pos, int lastNumber) {
        runWave(new Position(pos).move(Position.UP), lastNumber + 1);
        runWave(new Position(pos).move(Position.DOWN), lastNumber + 1);
        runWave(new Position(pos).move(Position.RIGHT), lastNumber + 1);
        runWave(new Position(pos).move(Position.LEFT), lastNumber + 1);
    }

    private void runWave(Position pos, int number) {
        if ((pos.x() < 0) || (pos.y() < 0)) {
            return;
        }
        if ((pos.x() >= field_.length) || (pos.y() >= field_[0].length)) {
            return;
        }
        int cell = field_[pos.x()][pos.y()];
        if ((cell > downBorder_) && (cell < upBorder_)) {
            if ((cell == 0) || (cell > number)) {
                field_[pos.x()][pos.y()] = number;
                createWaves(pos, number);
            }
        }
    }

    public int[][] getField() { return field_; }
}
