package package1.game;
import java.io.*;
import javax.sound.sampled.*;


public enum SoundEffect {

    SHOT("sounds/shot2.wav"),       // bullet
    THRUST("sounds/thrust.wav"),   // thrusters
    BREAK("sounds/break.wav"),     // asteroid crumble
    BONK("sounds/bonk.wav"),       // ship death
    BLING("sounds/bling.wav"),     // collected shiny
    BACKGROUND1("sounds/back.wav"),     // background music
    BACKGROUND2("sounds/back1.wav"),
    BACKGROUND3("sounds/back2.wav"),
    UH("sounds/uh.wav"),           // reverse
    COMBO("sounds/combo.wav"),
    SHIELD("sounds/shield.wav"),
    SUPER("sounds/pinch.wav"),
    BLAST("sounds/shot1.wav");
    // Nested class for specifying volume
    public enum Volume {
        MUTE, LOW
    }

    public static Volume volume = Volume.LOW;

    private Clip clip;

    SoundEffect(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
         }
    }

    public void loop(){
        if(volume != Volume.MUTE) {
            clip.loop(200);
        }
    }

    public void stop(){
        clip.stop();
    }
    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
}
