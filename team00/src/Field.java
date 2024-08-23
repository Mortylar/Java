import java.util.Random;

public class Field {

    private int[][] field_;
    private int size_;
    private char empty_ = ' ';

    public Field(int size) { createField(size); }

    public Field(int size, char empty) {
        empty_ = empty;
        createField(size);
    }

    private void createField(int size) {
        size_ = size;
        field_ = new int[size_][size_];
        for (int i = 0; i < size_; ++i) {
            for (int j = 0; j < size_; ++j) {
                field_[i][j] = empty_;
            }
        }
    }

    public void generateEntitiesPosition(Entity[] entity) {
        int entitiesCount = entity.length;
        int index = 0;
        Random random = new Random();
        while (index < entitiesCount) {
            for (int i = 0; (i < size_) && (index < entitiesCount); ++i) {
                for (int j = 0; (j < size_) && (index < entitiesCount); ++j) {
                    int randomNumber = random.nextInt(100);
                    if ((randomNumber == 0) && isEmpty((char)field_[i][j])) {
                        field_[i][j] = entity[index].getIcon();
                        entity[index].setPosition(new Position(i, j));
                        ++index;
                    }
                }
            }
        }
    }

    public boolean isEmpty(char place) { return place == empty_; }

    public void print() {
        for (int i = 0; i < size_; ++i) {
            for (int j = 0; j < size_; ++j) {
                System.out.print((char)field_[i][j]);
            }
            System.out.print("\n");
        }
    }
}
