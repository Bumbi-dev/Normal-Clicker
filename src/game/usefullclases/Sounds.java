package game.usefullclases;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Sounds {
    //List of the sounds
    private static final File click = new File("Assets\\Click.wav");
    private static final File money = new File("Assets\\Money.wav");

    private static final File typing1 = new File("Assets\\Typing1.wav");
    private static final File typing2 = new File("Assets\\Typing2.wav");
    private static final File typing3 = new File("Assets\\Typing3.wav");

    static File[] files = {click, money, typing1, typing2, typing3};

    private static AudioInputStream clickStream;
    private static AudioInputStream moneyStream;
    private static AudioInputStream typingStream;
    private static final AudioInputStream[] streams = {clickStream, moneyStream, typingStream};

    private static Clip clickClip;
    private static Clip moneyClip;
    private static Clip typingClip;
    private static final Clip[] clips = {clickClip, moneyClip, typingClip};

    public static void initialize() {//initializes the sounds
        try {
            for (int i = 0; i < streams.length; i++) {
                streams[i] = AudioSystem.getAudioInputStream(files[i]);

                clips[i] = AudioSystem.getClip();
                clips[i].open(streams[i]);
                clips[i].setFramePosition(990000);
                clips[i].start();
            }
        }
        catch(Exception ex) {
            System.out.println("Sound initialization exception");
        }
    }

    public static void playClick() {//Plays click sound

        if(clips[0].isRunning()) {//if it s running it will play the sound over it
            try {
                streams[0] = AudioSystem.getAudioInputStream(files[0]);
                clips[0] = AudioSystem.getClip();
                clips[0].open(streams[0]);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                System.out.println("CLick sound exception");
            }
        }
        clips[0].setFramePosition(0);//start the clip from the beginning and plays it
        clips[0].start();
    }

    public static void playMoney() {//Plays money sound
        if(clips[1].isRunning()) {
            try {
                streams[1] = AudioSystem.getAudioInputStream(files[1]);
                clips[1] = AudioSystem.getClip();
                clips[1].open(streams[1]);
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                System.out.println("Money sound exception");
            }
        }
        clips[1].setFramePosition(0);//start the clip from the beginning and plays it
        clips[1].start();
    }

    private static int prevType = -1;
    private static final Random random = new Random();
    public static void playTyping() {
        if(prevType != -1 && random.nextInt(1, 5) <= 2)//plays only 3 out of 5 times, and the first time always
            return;

        int currentType;
        do
            currentType = random.nextInt(2, 5);//gets a random value different that the last one, so that another sound plays
        while(prevType == currentType);
        prevType = currentType;

        try {
            streams[2] = AudioSystem.getAudioInputStream(files[currentType]);
            clips[2] = AudioSystem.getClip();
            clips[2].open(streams[2]);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Typing sound exception");
        }

        clips[2].setFramePosition(0);//start the clip from the beginning and plays it
        clips[2].start();
    }
}
