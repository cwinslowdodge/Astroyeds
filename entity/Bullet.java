package package1.game.entity;

import package1.game.Game;
import package1.game.gameUtil.Movement;
import java.awt.*;

public class Bullet extends Entity {

    private static final int MAX_LIFE = 50;
    private int lifespan;
    protected static int size = 4;

    public Bullet(Entity owner, double angle){
        super(new Movement(owner.position), new Movement(angle).scale(6.75),2.0);
        this.rotation = 5.0f;
        this.lifespan = MAX_LIFE;
        this.speed.addSpeed(owner.speed);
    }

    public void update(Game game){
        super.update(game);
        this.lifespan--;
        if(lifespan <= 0){
            killObject();
        }
    }

    @Override
    public void handleInterception(Game game, Entity ent){
        if(ent.getClass() == killerAsteroid.class){
            killObject();
            ent.killObject();
        }
    }

    public void draw(Graphics2D g, Game game) {

        g.setColor (Color.orange);
        g.draw3DRect (size/2, size/2 ,size,size, true);
        g.fill3DRect (size/2, size/2 ,size,size, true);
    }
}

