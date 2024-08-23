public abstract class Entity implements IEntity {

    private Position position_;
    private char icon_; //TODO static

    public Entity() {
        position_ = new Position();
    }

    public Entity(char icon) {
        icon_ = icon;
    }

    public Entity(Position position, char icon) {
        position_ = position;
        icon_ = icon;
    }

    public void setIcon(icon) {
        icon_ = icon;
    }

    public char getIcon(icon) {
        return icon_;
    }

    public void setPosition(Position position) {
        position_ = position;
    }

    public Position getPosition() {
        return position_
    }
    
}
