

public class Obstacle extends Entity implements IEntity, Immovable {

    private static final char DEFAULT_ICON = '#';

    public Obstacle() { super(DEFAULT_ICON); }

    public Obstacle(char icon) { super(icon); }

    public Obstacle(Position position, char icon) { super(position, icon); }
}
