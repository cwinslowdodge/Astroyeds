package package1.game.entity;

import package1.game.Game;
import java.awt.*;
import java.util.Random;
import package1.game.SoundEffect;
import package1.game.gameUtil.Movement;

public class killerAsteroid extends Entity{

    protected static int size = 50;
    Random rand = new Random();
    double randNum = -200 + (200 + 200) * rand.nextDouble();
    private static final double DEF_ROTATION = -Math.PI;
    int i;

    public killerAsteroid(Movement position, Movement speed, double magnitude){
        super(position, speed, magnitude);
        this.deadObject = false;
        this.position = position;
        this.speed = speed;
        this.magnitude = magnitude;
        this.rotation = 5.0f;
    }

    public static int getSize(){
        return size;
    }


    @Override
    public void handleInterception(Game game, Entity ent){

        if(ent.getClass() == Bullet.class){

            i++;
            if(i>1){
                return;
            }
            killObject();
            ent.killObject();
            SoundEffect.BREAK.play();
            Random rand = new Random();
            double randomNumber = -3 + (3 + 3) * rand.nextDouble();
            double randomNumber2 = -3 + (3 + 3) * rand.nextDouble();
            SmallKiller a = (new SmallKiller(new Movement(ent.getPosition()), new Movement(randomNumber, randomNumber2), SmallKiller.getSize()));
            double randomNumber3 = -3 + (3 + 3) * rand.nextDouble();
            double randomNumber4 = -3 + (3 + 3) * rand.nextDouble();
            SmallKiller b = (new SmallKiller(new Movement(ent.getPosition()), new Movement(randomNumber3, randomNumber4), SmallKiller.getSize()));
            game.addKillerAsteroid(game.getEntities());
            game.registerEntity(a);
            game.registerEntity(b);
            game.addPoints(10);
        }
    }

    public void draw(Graphics2D g, Game game) {

        g.setColor (new Color(200,0,0));
        g.fill3DRect (-size/2,-size/2,size,size, true);
        rotate(DEF_ROTATION/randNum);
    }
}