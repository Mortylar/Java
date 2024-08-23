

public class Obstacle extends Entity implements IEntity, Immovable {

    private static final char DEFAULT_ICON = '#';

    public Obstacle() {
        super(DEFAULT_ICON);
    }

    public Target(char icon) {
        super(icon);
    }

    public Target(Position position, char icon) {
        super(position, icon);
    }

}
