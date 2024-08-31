package game.logic.entity;

import game.logic.position.Position;

public class Player extends Entity implements IEntity, Moveable {

    private static final char DEFAULT_ICON = '@';

    private static char target_;

    private String name_ = "Pavel Durov";

    public Player() { super(DEFAULT_ICON); }

    public Player(char icon) { super(icon); }

    public Player(Position position, char icon) { super(position, icon); }

    public void setTarget(char targetIcon) { target_ = targetIcon; }

    public char getTargetIcon() { return target_; }

    public String getName() { return name_; }

    @Override
    public void move(Position newPosition) {
        setPosition(newPosition);
    }
}
