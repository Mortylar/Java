

public class Wave {

    private static final Position up = new Position(0, 1);
    private static final Position down = new Position(0, -1);
    private static final Position right = new Position(1, 0);
    private static final Position left = new Position(-1, 0);

    private static int[][] field_;
    private static int upBorder_;
    private static int downBorder_;

    private Position position_;
    private int number_;

    public Wave(int[][] field, int up, int down) {
        field_ = field;;
        upBorder_ = up;
        downBorder_ = down;
    }

    public void createWaves(Position pos, int lastNumber) {
        runWave(new Position(pos).move(up), lastNumber + 1);
        runWave(new Position(pos).move(down), lastNumber + 1);
        runWave(new Position(pos).move(right), lastNumber + 1);
        runWave(new Position(pos).move(left), lastNumber + 1);
    }
 
    private void runWave(Position pos, int number) {
        //System.out.printf("\nx = %d\ny = %d\n", pos.x(), pos.y());
        if ((pos.x() < 0) || (pos.y() < 0)) {
            //System.out.printf("\nAAA\n");
            return;
        }
        if ((pos.x() >= field_.length) || (pos.y() >= field_[0].length)) {
            //System.out.printf("\nBBBB\n");
            return;
        }
        int cell = field_[pos.x()][pos.y()];
       // System.out.printf("\ncell = %d\ndown = %d\nup = %d\nnumber = %d\n",
       //                    cell, downBorder_, upBorder_, number);
        if ((cell > downBorder_) && (cell < upBorder_)) {
            if ((cell == 0) || (cell > number)) {
            //System.out.printf("\nTHIS number = %d || %d\n", number, cell);
            field_[pos.x()][pos.y()] = number;
            createWaves(pos, number);
            }
        } 
    }

    public int[][] getField() {
        return field_;
    }

}
