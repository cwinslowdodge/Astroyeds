package package1.game.entity;

import package1.game.Game;
import java.awt.*;
import java.util.Random;
import package1.game.SoundEffect;
import package1.game.gameUtil.Movement;

public class Collectable extends Entity{

    protected static int size = 20;
    private static final double DEF_ROTATION = -Math.PI / 5.0;

    public Collectable(Movement position, Movement speed, double magnitude){
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

        if(ent.getClass() == Ship.class){
            killObject();
            SoundEffect.BLING.play();
            game.addPoints(50);
            if(game.getCombo() % 35 == 0){
                SoundEffect.COMBO.play();
            }
        }

    }

    public void draw(Graphics2D g, Game game) {

        Random rnd = new Random();
        int rc = rnd.nextInt(255)+1;
        int rc2 = rnd.nextInt(255)+1;
        int rc3 = rnd.nextInt(255)+1;
        Color j = new Color(rc,rc2,rc3);
        g.setColor (j);
        g.draw3DRect (-size/2,-size/2,size,size, true);
        g.fill3DRect (-size/2,-size/2,size,size, true);
        rotate(DEF_ROTATION);
    }
}
