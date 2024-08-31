package game.logic.field;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;
import static com.diogonunes.jcolor.Command.CLEAR_SCREEN;

import com.diogonunes.jcolor.AnsiFormat;
import game.logic.configuration.Configuration;
import game.logic.entity.Entity;
import game.logic.position.Position;
import java.util.Random;

public class Field {

    private int[][] field_;
    Configuration conf_;

    public Field(Configuration configuration) {
        conf_ = configuration;
        createField(conf_.getFieldSize());
    }

    private void createField(int size) {
        field_ = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                field_[i][j] = conf_.getEmptyIcon();
            }
        }
    }

    public void generateEntitiesPosition(Entity[] entity) {
        int entitiesCount = entity.length;
        int index = 0;
        Random random = new Random();
        while (index < entitiesCount) {
            for (int i = 0;
                 (i < conf_.getFieldSize()) && (index < entitiesCount); ++i) {
                for (int j = 0;
                     (j < conf_.getFieldSize()) && (index < entitiesCount);
                     ++j) {
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

    public boolean isEmpty(char place) { return place == conf_.getEmptyIcon(); }

    public void clearPosition(Position pos) {
        field_[pos.x()][pos.y()] = conf_.getEmptyIcon();
    }

    public void setEntity(Entity entity) {
        Position pos = entity.getPosition();
        field_[pos.x()][pos.y()] = entity.getIcon();
    }

    public boolean checkPosition(Position pos) {
        if ((pos.x() < 0) || (pos.x() >= conf_.getFieldSize())) {
            return false;
        }
        if ((pos.y() < 0) || (pos.y() >= conf_.getFieldSize())) {
            return false;
        }
        return isEmpty((char)field_[pos.x()][pos.y()]);
    }

    public boolean checkPosition(Position pos, char targetIcon) {
        if ((pos.x() < 0) || (pos.x() >= conf_.getFieldSize())) {
            return false;
        }
        if ((pos.y() < 0) || (pos.y() >= conf_.getFieldSize())) {
            return false;
        }
        return (targetIcon == field_[pos.x()][pos.y()]);
    }

    public int size() { return conf_.getFieldSize(); }

    public int[][] getCopy() {
        int size = conf_.getFieldSize();
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                copy[i][j] = field_[i][j];
            }
        }
        return copy;
    }

    public void clear() {
        for (int i = 0; i < conf_.getFieldSize(); ++i) {
            for (int j = 0; j < conf_.getFieldSize(); ++j) {
                field_[i][j] = conf_.getEmptyIcon();
            }
        }
    }

    public void print() {
        AnsiFormat playerColor = getColorAttribute(conf_.getPlayerColor());
        AnsiFormat enemyColor = getColorAttribute(conf_.getEnemyColor());
        AnsiFormat wallColor = getColorAttribute(conf_.getWallColor());
        AnsiFormat goalColor = getColorAttribute(conf_.getGoalColor());
        AnsiFormat emptyColor = getColorAttribute(conf_.getEmptyColor());

        for (int i = 0; i < conf_.getFieldSize(); ++i) {
            for (int j = 0; j < conf_.getFieldSize(); ++j) {
                if (conf_.getEmptyIcon() == field_[i][j]) {
                    System.out.print(colorize(
                        new String("" + (char)field_[i][j]), emptyColor));
                } else if (conf_.getEnemyIcon() == field_[i][j]) {
                    System.out.print(colorize(
                        new String("" + (char)field_[i][j]), enemyColor));
                } else if (conf_.getWallIcon() == field_[i][j]) {
                    System.out.print(colorize(
                        new String("" + (char)field_[i][j]), wallColor));
                } else if (conf_.getPlayerIcon() == field_[i][j]) {
                    System.out.print(colorize(
                        new String("" + (char)field_[i][j]), playerColor));
                } else if (conf_.getGoalIcon() == field_[i][j]) {
                    System.out.print(colorize(
                        new String("" + (char)field_[i][j]), goalColor));
                } else {
                    System.out.print("AAA"); // TODO remove
                }
            }
            System.out.print("\n");
        }
    }

    private AnsiFormat getColorAttribute(String color) {
        if (conf_.WHITE_COLOR.equals(color)) {
            return new AnsiFormat(BLACK_TEXT(), WHITE_BACK());
        }
        if (conf_.BLACK_COLOR.equals(color)) {
            return new AnsiFormat(BLACK_TEXT(), BLACK_BACK());
        }
        if (conf_.GREEN_COLOR.equals(color)) {
            return new AnsiFormat(BLACK_TEXT(), GREEN_BACK());
        }
        if (conf_.BLUE_COLOR.equals(color)) {
            return new AnsiFormat(BLACK_TEXT(), BLUE_BACK());
        }
        if (conf_.RED_COLOR.equals(color)) {
            return new AnsiFormat(BLACK_TEXT(), RED_BACK());
        }
        if (conf_.YELLOW_COLOR.equals(color)) {
            return new AnsiFormat(BLACK_TEXT(), YELLOW_BACK());
        }
        if (conf_.CYAN_COLOR.equals(color)) {
            return new AnsiFormat(BLACK_TEXT(), CYAN_BACK());
        }
        if (conf_.MAGENTA_COLOR.equals(color)) {
            return new AnsiFormat(BLACK_TEXT(), MAGENTA_BACK());
        }
        System.err.printf("color %s do not supported.\n", color);
        System.err.printf("Use one of then:\n");
        System.err.printf(
            "WHITE\nBLACK\nGREEN\nBLUE\nRED\nYELLOW\nCYAN\nMAGENTA\n");
        System.exit(-1);
        return null;
    }

    public void clearScreen() {
        final char ESC = 27;
        System.out.printf("%c[H%c[2J", ESC, ESC);
    }
}
