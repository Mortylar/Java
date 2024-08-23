

public class Target extends Entity implements IEntity, Immovable {

  private static final char DEFAULT_ICON = 'O';

  public Target() {
      super(DEFAULT_ICON);
  }

  public Target(char icon) {
      super(icon);
  }

  public Target(Position position, char icon) {
      super(position, icon);
  }



}
