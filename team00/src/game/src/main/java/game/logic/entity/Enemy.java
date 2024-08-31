package game.logic.entity;

import game.logic.position.Position;
import java.util.Random;

public class Enemy extends Entity implements IEntity, Moveable {

    private static final String[] NAMES = new String[] {
        "Vladimir Putin", "Emmanuel Macron",     "Alexey Panin",
        "Joe Biden",      "Valentin Steinmeier", "Barak Obama",
        "Olaf Scholz",    "Andrzej Duda",        "Alexsander Stubb"};

    private static final char DEFAULT_ICON = 'X';

    private static char target_;
    private String name_;

    { name_ = getRandomName(); }

    public Enemy() { super(DEFAULT_ICON); }

    public Enemy(char icon) { super(icon); }

    public Enemy(Position position, char icon) { super(position, icon); }

    public void setTarget(char targetIcon) { target_ = targetIcon; }

    public char getTargetIcon() { return target_; }

    public String getName() { return name_; }

    private String getRandomName() {
        return NAMES[new Random().nextInt(NAMES.length)];
    }

    @Override
    public void move(Position newPosition) {
        setPosition(newPosition);
    }
}
