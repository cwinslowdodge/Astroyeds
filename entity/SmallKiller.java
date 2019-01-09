package package1.game.entity;

import package1.game.Game;
import java.awt.*;
import java.util.Random;
import package1.game.gameUtil.Movement;

public class SmallKiller extends Entity{

    protected static int size = 30;
    Random rand = new Random();
    double randNum = -100 + (100 + 100) * rand.nextDouble();
    private static final double DEF_ROTATION = -Math.PI ;

    public SmallKiller(Movement position, Movement speed, double magnitude){
        super(position, speed, magnitude);
//        this.deadObject = false;
        this.position = position;
        this.speed = speed;
        this.magnitude = magnitude;
//        this.rotation = 5.0f;
    }

    public static int getSize(){
        return size;
    }

    @Override
    public void handleInterception(Game game, Entity ent){

        if(ent.getClass() == Bullet.class){
            killObject();
            ent.killObject();
            game.addPoints(20);
            Collectable b = (new Collectable(this.getPosition(), this.getSpeed(), Collectable.getSize()));
            game.registerEntity(b);
        }
    }

    public void draw(Graphics2D g, Game game) {

        g.setColor(Color.black);
//        g.setColor (new Color(0,20,200));
        g.draw3DRect (-size/2,-size/2,size,size, true);
        g.fill3DRect (-size/2,-size/2,size,size, true);
        rotate(-DEF_ROTATION/randNum);
    }
}
