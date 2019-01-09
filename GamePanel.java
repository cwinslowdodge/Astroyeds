package package1.game;

import package1.game.entity.Entity;
import package1.game.gameUtil.Movement;
import java.awt.*;
import javax.swing.JPanel;
import java.awt.geom.AffineTransform;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GamePanel extends JPanel{

    private Game game;

    public GamePanel(Game game) {

        this.game = game;
        setPreferredSize(new Dimension(Game.WORLD_SIZE, Game.DWORLD_SIZE));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setColor(Color.BLACK); //Set the draw color to white.
        AffineTransform identity = g2d.getTransform();
        g.setColor(Color.white);
        g.drawString("Score: " + game.getScore(), Game.WORLD_SIZE/2, 20);
        g.drawString("High Score: " + game.getHighScore(), Game.WORLD_SIZE-(175), 20);
        g.drawString("Blings: " + game.getCombo(), Game.WORLD_SIZE/2 +(125), 20);
        g.drawString("Deaths: " + game.getDeathCount(), 5, 20);
        Iterator<Entity> iter = game.getEntities().iterator();
        while(iter.hasNext()) {
            Entity entity = iter.next();
            Movement position = entity.getPosition();
            drawEntity(g2d, entity, position.x, position.y);
            g2d.setTransform(identity);
            }
    }

    private void drawEntity(Graphics2D g2d, Entity entity, double x, double y) {

        g2d.translate(x, y);
        double rotation = entity.getRotation();
        if(rotation != 0.0f) {
            g2d.rotate(entity.getRotation());
        }
        entity.draw(g2d, game);
    }
}
