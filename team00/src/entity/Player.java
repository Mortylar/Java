

public class Player extends Entity implements IEntity, Moveable {

    private static final char DEFAULT_ICON = '@';

    public Player() { super(DEFAULT_ICON); }

    public Player(char icon) { super(icon); }

    public Player(Position position, char icon) { super(position, icon); }

    @Override
    public void move() {}
}
