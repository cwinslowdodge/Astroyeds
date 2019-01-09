package package1.game.entity;

import package1.game.GamePanel;
import package1.game.Game;
import package1.game.gameUtil.Movement;
import java.awt.*;

public abstract class Entity {

    protected Movement position;
    protected Movement speed;
    protected double rotation;
    protected double magnitude;
    protected boolean deadObject;

    public Entity(Movement position, Movement speed, double magnitude) {
        this.position = position;
        this.speed = speed;
        this.magnitude = magnitude;
        this.rotation = 0.0f;
        this.deadObject = false;
    }

    public void rotate(double degree) {
        this.rotation += degree;
        this.rotation %= Math.PI * 2;
    }

    public Movement getPosition() {
        return position;
    }

    public Movement getSpeed() {
        return speed;
    }

    public double getRotation() {
        return rotation;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void killObject() {
        this.deadObject = true;
    }

    public boolean isDeadObject() {
        return deadObject;
    }

    public void update(Game game) {
        position.add(speed);
        if (position.x < 0.0f) {
            position.x += Game.WORLD_SIZE;
        }
        if (position.y < 0.0f) {
            position.y += Game.DWORLD_SIZE;
        }
        position.x %= Game.WORLD_SIZE;
        position.y %= Game.DWORLD_SIZE;
    }

    public boolean isIntercepting (Entity entity){

        double radius = entity.getMagnitude() + getMagnitude();
        return(position.getDistanceToSquared(entity.position) < radius *radius);
    }

    public abstract void handleInterception(Game game, Entity ent);

    public abstract void draw(Graphics2D g, Game game);
}
