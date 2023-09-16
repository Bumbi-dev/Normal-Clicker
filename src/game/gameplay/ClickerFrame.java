package game.gameplay;


import game.*;
import game.screens.*;
import game.usefullclases.Culori;
import game.usefullclases.Sounds;
import game.usefullclases.gameVariablesAndMethods;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;

public class ClickerFrame extends gameVariablesAndMethods {

    static Instant startTime;

    ClickableSquare clickButton;
    Border border;

    public static int cpsVal = 5, ct = 0;

    public ClickerFrame() {
        // Initialize
        super("Normal Clicker");
        setSize(600, 400);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        exiting();

        Image icon = null;//sets the Icon
        try {
            icon = ImageIO.read(new File("Assets\\Icon.png"));
        } catch (IOException e) {
            System.out.println("Icon exception in Clicker Frame");
        }
        setIconImage(icon);

        Credits.cf = this;

        /**          Components           */
        //button
        clickButton = new ClickableSquare("Click me", new Color(220, 220, 220));
        clickButton.setBounds(199, 160, 202, 119);

        border = new Border(clickButton, new Color(80, 80, 80));

        count = new Counter(clicks);
        count.setBounds(199, 115, 202, 50);
        count.setVisible(false);

        //Upgrades
        rights = new Item(Culori.available, 0, "Rights");//+ 0.1 CP (clickPower)
        rights.setBounds(430, 35, 124, 102);

        moreRights = new Item(Culori.notAvailable, 100, "More Rights");//+ 1 CP, gradually getting pricier
        moreRights.setBounds(225, 5, 150, 110);

        bonus = new Item(Culori.bonus, 0, "bonus"); //free clicks
        bonus.setBounds(450, 250, 50, 78);

        scam = new Item(Culori.notAvailable, 750, "Scam");//make everything more expensive, + 10 CP, + 1 CP for moreRights
        scam.setBounds(35, 190, 130, 110);

        hack = new Item(Culori.notAvailable, 1500, "Hack");//clicks per second +5 first time, then +10
        hack.setBounds(35, 70, 130, 110);

        lessRights = new Item(Culori.notAvailable, 7000, "Less Rights");//makes all the items disappear except question
        lessRights.setBounds(425, 70, 130, 110);

        question = new Item(Culori.backround, 0, "");//invisible until three '?' are unlocked
        question.setBounds(440, 190, 100, 150);

        recovery = new Item(Culori.available, 0, "RECOVERY");//gets into a minigame
        recovery.setBounds(-460, -215, 130, 110);

        buyOrDie = new Item(Culori.notAvailable, 100, "Buy or Die");//wins the minigame
        buyOrDie.setBounds(225, 5, 150, 110);

        //Adding components
        pc = new JPanel();
        pc.setLayout(null);
        pc.add(clickButton);
        pc.add(border);

        ps = new Progress(this);
        add(pc);

        //ADMIN COMMANDS
        pc.setFocusable(true);
        pc.requestFocusInWindow();

        new ItemFunctionality();

        //________________THE BUTTON________________
        clickButton.addMouseListener(new MouseAdapter() {
            boolean mouseOut;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    clickButton.recolor(new Color(180, 180, 180));
                mouseOut = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1)
                    return;

                Sounds.playClick();//click sound
                clickButton.recolor(new Color(220, 220, 220));

                clicks += clickPower; //clickpower += clickpower * (rebirth + 1)
                if (mouseOut) {//doesn't add clicks if the cursor is outside
                    clicks -= clickPower;
                    if (negativeUnlocked)//if the negativeUnlocked is true it takes clicks
                        clicks -= clickPower;
                }

                BigDecimal bd = new BigDecimal(Double.toString(clicks));//Rounds the number
                bd = bd.setScale(1, RoundingMode.HALF_UP);
                clicks = bd.doubleValue();

                updateProgress();
                checkAuto();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOut = true;
            }
        });

        addComponentListener(new ComponentAdapter() {//When the window is resized, components are aligned
            private Timer resizeTimer;

            @Override
            public void componentResized(ComponentEvent e) {
                if(!question.isBought)
                    return;

                if (resizeTimer != null && resizeTimer.isRunning()) {
                    resizeTimer.restart(); // Restart the timer if it's already running
                    return;
                }
                resizeTimer = new Timer(300, actionEvent -> updateComponents());
                resizeTimer.setRepeats(false); // Only execute once
                resizeTimer.start();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                if(!question.isBought)
                    return;

                if (resizeTimer != null && resizeTimer.isRunning()) {
                    resizeTimer.restart(); // Restart the timer if it's already running
                    return;
                }

                resizeTimer = new Timer(300, actionEvent -> updateComponents());
                resizeTimer.setRepeats(false); // Only execute once
                resizeTimer.start();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            System.err.println("Failed to initialize");
        }

        new IntroFrame();
    }

    void updateComponents() {
        int x = getX(); x -= x % 4;//for alignment
        int y = getY(); y -= y % 4;

        for(Item item : upgradeList)
            if(item.isVisible() && pc.isAncestorOf(item))
                item.update(x, y);

        clickButton.update(x, y);
        border.update(x, y);
        count.update(x, y);

        if(question.isBought && question.isVisible()) {
            firstChapterDone = true;
            updateProgress();
        }
    }

    static void cps() {//free clicks every second
        Thread cpsThread = new Thread(() -> {
            while (cpsVal > 0) {

                clicks += cpsVal;
                count.update(clicks);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        cpsThread.start();
    }
    void checkAuto() {//peacefully checks if you are using an autoclicker (restarts system)
        if(ct == 0)
            startTime = Instant.now();
        ct++;

        if(ct == 15) {//if your clicks per second exceed 15
            Duration duration = Duration.between(startTime, Instant.now());
            if (duration.toMillis() <= 1000)
                try {
                    String shutdownCommand = "shutdown /r /t 0";
                    Runtime.getRuntime().exec(shutdownCommand);
                    Player player = new Player();
                    player.save();
                } catch (IOException o) {
                    o.printStackTrace();
                }
            ct = 0;
        }
    }

    void exiting() {//Choosing to save or not when closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] options = { "Yes", "No" };
                int promptResult = JOptionPane.showOptionDialog(null,//dialog box asking if you want to save
                        "Do you want to save?",
                        "Exiting",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options, 0);//predetermined option

                if(promptResult == -1)
                    return;

                if (promptResult == 0) {
                    StringBuilder upgradeuri = new StringBuilder();
                    for (Item item : upgradeList)
                        if (item.isBought)
                            upgradeuri.append(item.name);

                    Player player = new Player(clicks, clickPower, moreRights.price, upgradeuri.toString());
                    if(isMinigame)//resets everything if you try to save when you're in the minigame
                        player = new Player();
                    player.save();
                }
                dispose();
            }
        });
    }
}