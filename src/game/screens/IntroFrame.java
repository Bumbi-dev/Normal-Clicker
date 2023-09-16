package game.screens;

import game.gameplay.ClickerFrame;
import game.gameplay.Player;
import game.usefullclases.Sounds;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IntroFrame extends JFrame { //Intro scene, where you choose to continue, or start a new save
    // Interface
    JButton Continue, newGame;
    JLabel intro;
    JPanel mp, bp;

    public IntroFrame() {
        // Initialization
        super("Intro");
        setVisible(true);
        setResizable(false);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Image icon = null;//sets the Icon
        try {
            icon = ImageIO.read(new File("Assets\\Icon.png"));
        } catch (IOException e) {
            System.out.println("Icon exception in Clicker Frame");
        }
        setIconImage(icon);

        intro = new JLabel();
        intro.setFont(new Font("Montserrat", Font.PLAIN, 24));

        newGame = new JButton(); newGame.setFont(new Font("Montserrat", Font.PLAIN, 22));
        Continue = new JButton(); Continue.setFont(new Font("Montserrat", Font.PLAIN, 22));

        Continue.setFocusPainted(false); Continue.setFocusable(false);//prevent buttons from highlighting
        newGame.setFocusPainted(false); newGame.setFocusable(false);

        mp = new JPanel();
        mp.setLayout(new GridBagLayout());

        bp = new JPanel(); bp.setBounds(110, 200, 480, 270);
        bp.setLayout(null);

        // The Introduction
        add(mp);
        mp.add(intro);

        script();

        mp.setBounds(0, 0, 585, 100);
        Continue.setBounds(100, 180, 150, 80);
        newGame.setBounds(350, 180, 150, 80);

        bp.add(Continue);
        bp.add(newGame);
        add(bp);

        Continue.setText("Continue");
        newGame.setText("New Game");

        File fila = new File("Assets\\Date Player.txt");

        Continue.addActionListener(e -> {
            if(!fila.exists())
                return;
            dispose();

            Player player = new Player();
            player.loadProgress();

            new ClickerFrame();
        });

        newGame.addActionListener(e -> {
            if(fila.exists()) {
                String[] Options = {"Yes", "No"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure?", " Start a New Save",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[1]);
                if (PromptResult != 0)
                    return;
            }

            dispose();
            Player player = new Player();
            player.save();

            new ClickerFrame();
        });
    }
    
    private void script() {
        Thread soundInitThread = new Thread(Sounds::initialize);//intitializing sounds
        soundInitThread.start();

        try {
            typing("Loading.", "..", 3);

            typing("Welcome to Normal Clicker", 0.75f);

            Thread.sleep(1000);
        }
        catch (InterruptedException ie) {
            System.out.println("Intro script exception");
        }
    }

    //constantPart displays instantly while repeatedPart has a typewriter effect, after it's typed it repeats for _times_ times
    private void typing(String constantPart, String repeatedPart, int times) throws InterruptedException {
        intro.setText(constantPart);

        final int delay = 525;
        //shows the repeatedPart with a typing effect after the constantPart
        for (int i = 0; i <= repeatedPart.length(); i++, Thread.sleep(delay))
            intro.setText(constantPart + repeatedPart.substring(0, i));

        if (times > 1) {
            Thread.sleep(600);
            typing(constantPart, repeatedPart, times - 1);
        }
        else {
            Thread.sleep(1000);
            intro.setText("");
        }
    }
    
    private void typing (String message, float speed) throws InterruptedException {
        int delay = (int) (100 / speed); // Delay between each character in milliseconds

        mp.setLayout(new FlowLayout());

        for (int i = 1; i <= message.length(); i++, Thread.sleep(delay)) {
            intro.setText(message.substring(0, i));
            Sounds.playTyping();
        }
    }
    
}