package package1.game.entity;

import package1.game.Game;
import package1.game.SoundEffect;
import package1.game.gameUtil.Movement;
import java.awt.*;
import java.util.*;

public class Ship extends Entity {

    private static final double DEF_ROTATION = -Math.PI / 20.0;
    private static final double THRUST_MAGNITUDE = 1;
    private static final double MAX_SPEED = 5;
    private boolean upPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean downPressed;
    private boolean fireBullet;
    private boolean boostPressed;
    public boolean immortal;
    private int timeImmortal;
    public boolean killerShip;
    private int timeKiller;
//    public int doubleGunBlings = 20;

    public int blastCount;
    public int blastBulletCount;


    public Ship() {
        super(new Movement(Game.WORLD_SIZE / 2, Game.DWORLD_SIZE / 2), new Movement(0.0, 0.0), 10.0);
        this.rotation = DEF_ROTATION;
        this.deadObject = false;
        this.upPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
        this.downPressed = false;
        this.boostPressed = false;
        this.fireBullet = false;
        this.immortal = true;
        this.timeImmortal = Game.Max_Immortal;
        this.killerShip = false;
        this.timeKiller = Game.Max_Killer;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setBoostPressed(boolean boostPressed){this.boostPressed = boostPressed;}

    public void setFiring(boolean fireBullet){
        this.fireBullet = fireBullet;
    }

    public void setImmortal(boolean immortal){
        this.immortal = immortal;
    }

    public void setKillerShip(boolean killerShip){
        this.killerShip = killerShip;
    }

    @Override
    public void update(Game game) {

        super.update(game);
        if(immortal){
            timeImmortal--;
            if(timeImmortal == 0){
                setImmortal(false);
                timeImmortal = game.Max_Immortal;
            }
        }
        if(killerShip){
            timeKiller--;
            if(timeKiller == 0){
                setKillerShip(false);
                timeKiller = game.Max_Killer;
            }
        }
        if (leftPressed != rightPressed) {
            rotate(leftPressed ? DEF_ROTATION : -DEF_ROTATION);
        }
        if (upPressed) {
            speed.add(new Movement(rotation).scale(THRUST_MAGNITUDE));
            if (speed.getShipMagnitude() >= MAX_SPEED * MAX_SPEED) {
                speed.controlSpeed().scale(MAX_SPEED);
            }
        }
        if (!upPressed && !downPressed){

            speed.scale(.975);
            SoundEffect.UH.stop();
        }
        if (downPressed){
            speed.add(new Movement(rotation).scale(-THRUST_MAGNITUDE/2));
            if (speed.getShipMagnitude() >= MAX_SPEED * MAX_SPEED) {
                speed.controlSpeed().scale(MAX_SPEED/2);
            }
            SoundEffect.UH.loop();
        }
        if (boostPressed && upPressed){
            speed.add(new Movement(rotation).scale(THRUST_MAGNITUDE));
            if (speed.getShipMagnitude() >= MAX_SPEED * MAX_SPEED) {
                speed.controlSpeed().scale(MAX_SPEED*1.7);
            }
        }
        if (boostPressed && downPressed){
            speed.add(new Movement(rotation).scale(-THRUST_MAGNITUDE/2));
            if (speed.getShipMagnitude() >= MAX_SPEED * MAX_SPEED) {
                speed.controlSpeed().scale(MAX_SPEED/2*1.7);
            }
        }
        if (fireBullet && game.getCombo() < game.doubleGunBlings ) {
            if (game.bulletCount == 0) {
                Bullet bullet = new Bullet(this, rotation);
                game.registerEntity(bullet);
                game.bulletCount++;
            }
        }
        if (fireBullet && game.getCombo() >= game.doubleGunBlings){
            if (game.bulletCount == 0) {
                Bullet bullet1 = new Bullet(this, rotation);
                bullet1.position.x = bullet1.position.x + 7;
                Bullet bullet2 = new Bullet(this, rotation);
                bullet2.position.x = bullet2.position.x - 15;
                game.registerEntity(bullet1);
                game.registerEntity(bullet2);
                game.bulletCount++;
            }
        }
    }


    @Override
    public void handleInterception(Game game, Entity ent){

        if(ent.getClass() == Collectable.class) {
            ent.killObject();
            game.addCombo(1);
            if(game.getCombo()%game.immortalBlings == 0){
                setImmortal(true);
                SoundEffect.SHIELD.play();
            }
            if(game.getCombo()%game.superBlings == 0){
                setKillerShip(true);
                SoundEffect.SUPER.play();
            }
            if(game.getCombo()%game.blastBlings == 0){
                SoundEffect.BLAST.play();

                blastCount++;
                System.out.println("Number of Blasts: " + blastCount);

                for(int i = 0; i <= 360; i = i + 30){

                    blastBulletCount++;
                    System.out.println("Number of Bullets: " + blastBulletCount + "\n");

                    Bullet bullet = new Bullet(this,i);
                    game.registerEntity(bullet);
                }
            }
        }
        if((ent.getClass() == killerAsteroid.class && !immortal && !killerShip) || (ent.getClass() == SmallKiller.class && !immortal && !killerShip)) {
            killObject();
            game.addDeathCount(1);
            SoundEffect.BONK.play();
            game.startGame();
        }
        if(ent.getClass() == killerAsteroid.class && killerShip) {

            SoundEffect.BREAK.play();
            ent.killObject();
            Random rand = new Random();
            double randomNumber = -3 + (3 + 3) * rand.nextDouble();
            double randomNumber2 = -3 + (3 + 3) * rand.nextDouble();
            SmallKiller a = (new SmallKiller(new Movement(ent.getPosition()), new Movement(randomNumber, randomNumber2), SmallKiller.getSize()));
            double randomNumber3 = -3 + (3 + 3) * rand.nextDouble();
            double randomNumber4 = -3 + (3 + 3) * rand.nextDouble();
            SmallKiller b = (new SmallKiller(new Movement(ent.getPosition()), new Movement(randomNumber3, randomNumber4), SmallKiller.getSize()));
//            double randomNumber5 = -3 + (3 + 3) * rand.nextDouble();
//            double randomNumber6 = -3 + (3 + 3) * rand.nextDouble();
//            killerAsteroid c = (new killerAsteroid(new Movement(rand.nextInt(1200),rand.nextInt(900)), new Movement(randomNumber5, randomNumber6), killerAsteroid.getSize()));
            game.addKillerAsteroid(game.getEntities());
            game.registerEntity(a);
            game.registerEntity(b);

//            game.registerEntity(c);
            game.addPoints(10);
        }
        if(ent.getClass() == SmallKiller.class && killerShip) {
            ent.killObject();
            game.addPoints(20);
            Collectable b = (new Collectable(this.getPosition(), this.getSpeed(), Collectable.getSize()));
            game.registerEntity(b);
        }
    }

    @Override
    public void draw(Graphics2D g, Game game) {

        Random rnd = new Random();
        int rc = rnd.nextInt(255)+1;
        int rc2 = rnd.nextInt(255)+1;
        int rc3 = rnd.nextInt(255)+1;
        Color j = new Color(rc,rc2,rc3);
        g.setColor(Color.YELLOW);
        g.drawLine(10, -4, 20, 0);
        g.drawLine(10, 4, 20, 0);
        g.setColor(Color.yellow);
        g.drawLine(-15, 15, -10, 4);
        g.drawLine(-15, 15, 4, 4);
        g.drawLine(-15, -15, -10, -4);
        g.drawLine(-15, -15, 4, -4);
        g.drawLine(-15, -15, 0, -4);
        g.drawLine(-15, -15, -6, -4);
        g.drawLine(-15, 15, 0, 4);
        g.drawLine(-15, 15, -6, 4);
        g.setColor(Color.white);
        g.fillRect(-10, -4, 20, 8);
        if(game.getCombo() >= game.doubleGunBlings){
            g.setColor(new Color(30,200,200));
            g.fillRect(-6,-10,20,2);
            g.fillRect(-6,8,20,2);
        }
        if(this.immortal){
            g.setColor (j);
            g.drawOval(-21, -21, 42, 42);
        }
        if(this.killerShip){
            g.setColor(j);
            g.drawLine(-20, -25, 30, 0);
            g.drawLine(-20, -25, -20, 25);
            g.drawLine(30, 0, -20, 25);
        }
    }
}


