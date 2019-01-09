package package1.game;

import com.sun.org.apache.xpath.internal.operations.Mod;
import package1.game.entity.*;
import package1.game.gameUtil.Movement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Game extends JFrame{
    protected int numCollectable = 25;
    protected int numKillerAstoids = 10;
    public int bulletCount = 0;
    int numCollected = 0;
    int numDeaths = 0;
    public static int FPS = 60;
    private static final long FRAME_TIME = (long)(1000000000.0 / FPS);
    private GamePanel gamePanel;
    private List<Entity> entities;
    private List<Entity> pendingEntities;
    private Ship rocketShip;
    private int score;
    private int highScore = 0;
    private JMenuBar menuBar;
    private JMenu File, Help, Options, Music, Custom, Mode;
    private JMenuItem pause, restart, exit, inst, mute, on, song1, song2, song3,off, blings, asteroids, iLength, sLength, bJems, iJems,sJems,demiGod, easy, med, hard, insane;
    public static final int WORLD_SIZE = 1900;
    public static final int DWORLD_SIZE = 950;

    public static int Max_Immortal = 240;
    public static int Max_Killer = 240;
    public int immortalBlings = 15;
    public int superBlings = 25;
    public int blastBlings = 10;
    public int doubleGunBlings = 20;

    public Game() {
        super("ASTROYED");
        SoundEffect.BACKGROUND1.loop();
        SoundEffect.init();
        setPreferredSize(new Dimension(WORLD_SIZE,DWORLD_SIZE + 50));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        add(this.gamePanel = new GamePanel(this), BorderLayout.CENTER);
        menu();
        add(menuBar, BorderLayout.NORTH);
        actionListeners();

        addKeyListener(new KeyAdapter() { /******************************************************************
         * keyPressed method determines what keys are being pressed in
         * combination with others to set the rocket ship in a direction.
         * @param e
         *****************************************************************/
        @Override
        public void keyPressed(KeyEvent e) {

            int code = e.getKeyCode();
            if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W){
                rocketShip.setUpPressed(true);
                SoundEffect.THRUST.loop();
            }
            if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){
                rocketShip.setDownPressed(true);
            }
            if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){
                rocketShip.setLeftPressed(true);
            }
            if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D){
                rocketShip.setRightPressed(true);
            }
            if(code == KeyEvent.VK_SPACE){
                rocketShip.setFiring(true);
                SoundEffect.SHOT.play();
            }
            if(code == KeyEvent.VK_SHIFT){
                rocketShip.setBoostPressed(true);
            }
        }
            @Override
            public void keyReleased(KeyEvent e) {

                int code = e.getKeyCode();
                if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W){
                    rocketShip.setUpPressed(false);
                    SoundEffect.THRUST.stop();
                }
                if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){
                    rocketShip.setDownPressed(false);
                }
                if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){
                    rocketShip.setLeftPressed(false);
                }
                if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D){
                    rocketShip.setRightPressed(false);
                }
                if (code == KeyEvent.VK_SPACE){
                    rocketShip.setFiring(false);
                    bulletCount = 0;
                }
                if(code == KeyEvent.VK_SHIFT){
                    rocketShip.setBoostPressed(false);
                }
            }
        });
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void actionListeners(){
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FPS = 0;
            }
        });
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        inst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FPS = 0;
                JOptionPane.showMessageDialog(null, "                     Instructions\n\n" +
                        "Things to know:\n" +
                        "     Be AWESOME and have a GREAT time doing it.\n" +
                        "     You only have one life to make your mark.\n" +
                        "     You are invulnerable for a few seconds at the beginning.\n" +
                        "     Collect the bling to make the cool things happen.\n" +
                        "     Figure out how many blings make cool things.\n\n" +
                        "Press:\n" +
                        "     UP = Forward (based on your orientation)\n" +
                        "     Down = Backwards\n" +
                        "     Right = Rotate Clockwise\n" +
                        "     Left = Rotate Counter Clockwise\n" +
                        "     Space = Fire Cannon\n\n" +
                        "Abilities:\n" +
                        "     Circle around ship = immortal\n" +
                        "     Triangle around ship = SUPER SHIP!!!\n" +
                        "     Double Guns at 20 blings\n\n\n" +
                        "***Some custom changes won't be affected until the next round***");
            }
        });
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.volume = SoundEffect.Volume.MUTE;
                SoundEffect.BACKGROUND1.stop();
                SoundEffect.BACKGROUND2.stop();
                SoundEffect.BACKGROUND3.stop();
            }
        });
        on.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.volume = SoundEffect.Volume.LOW;
                SoundEffect.BACKGROUND1.loop();
            }
        });
        song1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.BACKGROUND1.loop();
                SoundEffect.BACKGROUND2.stop();
                SoundEffect.BACKGROUND3.stop();
            }
        });
        song2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.BACKGROUND2.loop();
                SoundEffect.BACKGROUND1.stop();
                SoundEffect.BACKGROUND3.stop();}
        });
        song3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.BACKGROUND3.loop();
                SoundEffect.BACKGROUND2.stop();
                SoundEffect.BACKGROUND1.stop();}
        });
        off.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.BACKGROUND2.stop();
                SoundEffect.BACKGROUND1.stop();
                SoundEffect.BACKGROUND3.stop();
            }
        });
        blings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String blings = JOptionPane.showInputDialog("# of Blings:");
                numCollectable = Integer.parseInt(blings);
            }
        });
        asteroids.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String killer = JOptionPane.showInputDialog("# of Asteroids:");
                if(Integer.parseInt(killer) > 100)
                    JOptionPane.showMessageDialog(null, "How good do you think your computer is?\n" +
                                                        "Or maybe we just suck at programming.\n" +
                                                        "Anyway, try a smaller number. ");
                else
                    numKillerAstoids = Integer.parseInt(killer);
            }
        });
        iJems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String immortalBlings1 = JOptionPane.showInputDialog("# to collect for Immortal: ");
                immortalBlings = Integer.parseInt(immortalBlings1);
            }
        });
        iLength.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String immortal = JOptionPane.showInputDialog("Immortal Timer (in seconds):");
                Max_Immortal = Integer.parseInt(immortal)*60;
            }
        });
        sJems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String superBlings1 = JOptionPane.showInputDialog("# to collect for Super Ship: ");
                superBlings = Integer.parseInt(superBlings1);
            }
        });
        sLength.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String superShip = JOptionPane.showInputDialog("Super Ship Timer: (in seconds)");
                Max_Killer = Integer.parseInt(superShip)*60;
            }
        });
        bJems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String blastBlings1 = JOptionPane.showInputDialog("# to collect for Blast: ");
                blastBlings = Integer.parseInt(blastBlings1);
            }
        });
        demiGod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numCollectable = 100;
                blastBlings = 4;
                numKillerAstoids = 10;
                immortalBlings = 5;
                Max_Immortal = 2000;
                superBlings = 7;
                Max_Killer = 1000;
            }
        });
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numCollectable = 5;
                blastBlings = 5;
                numKillerAstoids = 5;
                immortalBlings = 1;
                superBlings = 15;
                Max_Immortal = 200;
                Max_Killer = 1000;
            }
        });
        med.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numCollectable = 20;
                numKillerAstoids = 15;
                blastBlings = 7;
                superBlings = 16;
                immortalBlings = 12;
                Max_Immortal = 500;
                Max_Killer = 500;
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numCollectable = 10;
                numKillerAstoids = 20;
                blastBlings = 10;
                superBlings = 20;
                immortalBlings = 15;
                Max_Immortal = 350;
                Max_Killer = 350;
            }
        });
        insane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numCollectable = 0;
                numKillerAstoids = 50;
                blastBlings = 20;
                superBlings = 50;
                immortalBlings = 30;
                Max_Immortal = 100;
                Max_Killer = 100;
            }
        });
    }

    public void menu(){

        File = new JMenu("File");
        Help = new JMenu("Help");
        Options = new JMenu("Sound");
        Music = new JMenu("Music");
        Custom = new JMenu("");
        Mode = new JMenu("Mode");

        pause = new JMenuItem("Pause");
        restart = new JMenuItem("Restart");
        exit = new JMenuItem("Exit");
        inst = new JMenuItem("Instructions");
        mute = new JMenuItem("Mute");
        on = new JMenuItem("On");
        song1 = new JMenuItem("Song 1");
        song2 = new JMenuItem("Song 2");
        song3 = new JMenuItem("Song 3");
        off = new JMenuItem("Off");
        blings = new JMenuItem("Number of Blings");
        asteroids = new JMenuItem("Number of Asteroids");
        iLength = new JMenuItem("Immortal Duration");
        sLength = new JMenuItem("Super Ship Duration");
        bJems = new JMenuItem("Get Blast");
        iJems = new JMenuItem("Get Immortal");
        sJems = new JMenuItem("Get Super Ship");
        easy = new JMenuItem("Noob");
        med = new JMenuItem("Teen");
        hard = new JMenuItem("80's kid");
        insane = new JMenuItem("Foolish");
        demiGod = new JMenuItem("Demigod");

        menuBar = new JMenuBar();
        menuBar.add(File);
        menuBar.add(Mode);
        menuBar.add(Music);
        menuBar.add(Options);
        menuBar.add(Help);
        menuBar.add(Custom);
        File.add(pause);
        File.add(restart);
        File.add(exit);
        Options.add(mute);
        Options.add(on);
        Music.add(song1);
        Music.add(song2);
        Music.add(song3);
        Music.add(off);
        Help.add(inst);
        Custom.add(asteroids);
        Custom.add(blings);
        Custom.add(bJems);
        Custom.add(iJems);
        Custom.add(sJems);
        Custom.add(iLength);
        Custom.add(sLength);
        Mode.add(demiGod);
        Mode.add(easy);
        Mode.add(med);
        Mode.add(hard);
        Mode.add(insane);

    }

    public void addPoints(int amount){
        score += amount;
    }

    public void addCombo(int combo){
        numCollected += combo;
    }

    public int getCombo(){
        return numCollected;
    }

    public int getScore(){
        return score;
    }

    public int getHighScore(){
        return highScore;
    }

    public void addDeathCount(int amount){
        numDeaths += amount;
    }

    public int getDeathCount(){
        return numDeaths;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addAsteroid(List<Entity> entities) {
        Random rand = new Random();
        double randomNumber = -3 + (3 + 3) * rand.nextDouble();
        double randomNumber2 = -3 + (3 + 3) * rand.nextDouble();
        pendingEntities.add(new Collectable(new Movement(rand.nextInt(WORLD_SIZE),rand.nextInt(DWORLD_SIZE)), new Movement(randomNumber,randomNumber2), Collectable.getSize()));
    }

    public void addKillerAsteroid(List<Entity> entities) {
        Random rand = new Random();
        double randomNumber = -3 + (3 + 3) * rand.nextDouble();
        double randomNumber2 = -3 + (3 + 3) * rand.nextDouble();
        killerAsteroid a = (new killerAsteroid(new Movement(rand.nextInt(WORLD_SIZE), rand.nextInt(DWORLD_SIZE)), new Movement(randomNumber, randomNumber2), killerAsteroid.getSize()));
        pendingEntities.add(a);
    }

    public void registerEntity(Entity entity) {
        pendingEntities.add(entity);
    }

    public void startGame() {

        entities = new LinkedList<Entity>();
        pendingEntities = new ArrayList<Entity>();
        rocketShip = new Ship();
        if(score > highScore){
            highScore = score;
        }
        score = 0;
        numCollected = 0;
        pendingEntities.add(rocketShip);
        for(int i = 0; i < numCollectable; i++)
        {
            addAsteroid(pendingEntities);
        }
        for(int i = 0; i < numKillerAstoids; i++){
            addKillerAsteroid(pendingEntities);
        }
        while(true) {
            long start = System.nanoTime();
            updateGame();
            gamePanel.repaint();
            long delta = FRAME_TIME - (System.nanoTime() - start);
            if(delta > 0) {
                try {
                    Thread.sleep(delta / 1000000L, (int) delta % 1000000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            for(Entity entity : entities) {
                entity.update(this);
            }
        }
    }

    private void resetGame(){
        numDeaths = 0;
        numCollected = 0;
        highScore = 0;
        score = 0;
    }

    private void updateGame() {
        entities.addAll(pendingEntities);
        pendingEntities.clear();
        for (int i = 0; i < entities.size(); i++) {
            Entity a = entities.get(i);
            for (int j = 0; j < entities.size(); j++) {
                Entity b = entities.get(j);
                if (i != j && a.isIntercepting(b)) {
                    a.handleInterception(this, b);
                }
            }
        }
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isDeadObject()) {
                entities.remove(iterator);
                iterator.remove();
            }
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Game game = new Game();
        game.startGame();
    }
}
