package game.logic.field;

import java.util.Random;

import game.logic.entity.Entity;
import game.logic.position.Position;

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

    public boolean isEmpty(char place) {
        // System.out.printf("\nEMPTY FALSE place  a%ca empty  a%ca\n", place,
        // empty_);

        return place == empty_;
    }

    public void clearPosition(Position pos) {
        field_[pos.x()][pos.y()] = empty_;
    }

    public void setEntity(Entity entity) {
        Position pos = entity.getPosition();
        field_[pos.x()][pos.y()] = entity.getIcon();
    }

    public boolean checkPosition(Position pos) {
        if ((pos.x() < 0) || (pos.x() >= size_)) {
            return false;
        }
        if ((pos.y() < 0) || (pos.y() >= size_)) {
            return false;
        }
        return isEmpty((char)field_[pos.x()][pos.y()]);
    }

    public boolean checkPosition(Position pos, char targetIcon) {
        if ((pos.x() < 0) || (pos.x() >= size_)) {
            return false;
        }
        if ((pos.y() < 0) || (pos.y() >= size_)) {
            return false;
        }
        return (targetIcon == field_[pos.x()][pos.y()]);
    }

    public int size() { return size_; }

    public int[][] getCopy() {
        int[][] copy = new int[size_][size_];
        for (int i = 0; i < size_; ++i) {
            for (int j = 0; j < size_; ++j) {
                copy[i][j] = field_[i][j];
            }
        }
        return copy;
    }

    public void clear() {
        for (int i = 0; i < size_; ++i) {
            for (int j = 0; j < size_; ++j) {
                field_[i][j] = empty_;
            }
        }
    }

    public void print() {
        System.out.print("========================\n");
        for (int i = 0; i < size_; ++i) {
            System.out.print("||");
            for (int j = 0; j < size_; ++j) {
                System.out.print((char)field_[i][j]);
            }
            System.out.print("||\n");
        }
        System.out.print("========================\n");
    }
}
