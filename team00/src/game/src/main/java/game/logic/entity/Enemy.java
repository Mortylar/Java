package game.logic.entity;

import game.logic.position.Position;

public class Enemy extends Entity implements IEntity, Moveable {

    private static final char DEFAULT_ICON = 'X';

    private static char target_;

    public Enemy() { super(DEFAULT_ICON); }

    public Enemy(char icon) { super(icon); }

    public Enemy(Position position, char icon) { super(position, icon); }

    public void setTarget(char targetIcon) { target_ = targetIcon; }

    public char getTargetIcon() { return target_; }

    @Override
    public void move() {}
}
