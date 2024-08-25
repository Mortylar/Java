

public class WaveManager {

    private static final int START_MARKER = 1;
    private static final int END_MARKER = -1;
    private static final int EMPTY_MARKER = 0;

    private int start_;
    private int end_;
    private int[] obstacle_;
    private int border_;

    private Position pos_;

    private int[][] field_;

    public WaveManager(int[][] field) { field_ = field; }

    public void configure(int start, int end, int[] obstacle) {
        start_ = start;
        end_ = end;
        obstacle_ = obstacle;
        border_ = field_.length * field_[0].length + 1;
        marking();
    }

    public int[][] run() {
        Wave wave = new Wave(field_, border_, END_MARKER);
        wave.createWaves(pos_, START_MARKER);
        return wave.getField();
    }

    private void marking() {
        markObstacles();
        for (int i = 0; i < field_.length; ++i) {
            for (int j = 0; j < field_[0].length; ++j) {
                if (start_ == field_[i][j]) {
                    field_[i][j] = START_MARKER;
                    pos_ = new Position(i, j);
                } else if (end_ == field_[i][j]) {
                    field_[i][j] = END_MARKER;
                } else if (field_[i][j] != border_) {
                    field_[i][j] = EMPTY_MARKER;
                }
            }
        }
    }

    private void markObstacles() {
        for (int ind = 0; ind < obstacle_.length; ++ind) {
            for (int i = 0; i < field_.length; ++i) {
                for (int j = 0; j < field_[0].length; ++j) {
                    if (obstacle_[ind] == field_[i][j]) {
                        field_[i][j] = border_;
                    }
                }
            }
        }
    }
}
