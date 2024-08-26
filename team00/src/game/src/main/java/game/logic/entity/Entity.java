package game.logic.entity;

import game.logic.position.Position;

public abstract class Entity implements IEntity {

    private Position position_;
    private char icon_;

    public Entity() { position_ = new Position(); }

    public Entity(char icon) { icon_ = icon; }

    public Entity(Position position, char icon) {
        position_ = position;
        icon_ = icon;
    }

    public void setIcon(char icon) { icon_ = icon; }

    public char getIcon() { return icon_; }

    public void setPosition(Position position) { position_ = position; }

    public Position getPosition() { return position_; }
}
