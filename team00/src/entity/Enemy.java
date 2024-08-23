

public class Enemy extends Entity implements IEntity, Moveable {

    private static final char DEFAULT_ICON = 'X';

    public Enemy() { super(DEFAULT_ICON); }

    public Enemy(char icon) { super(icon); }

    public Enemy(Position position, char icon) { super(position, icon); }

    @Override
    public void move() {}
}
