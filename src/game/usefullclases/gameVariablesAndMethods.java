package game.usefullclases;

import game.Counter;
import game.gameplay.Item;
import game.gameplay.Progress;

import javax.swing.*;

public class gameVariablesAndMethods extends JFrame {//maybe remove JFrame and create object in ClickerFrame class

    public static boolean negativeUnlocked, firstChapterDone = false, isMinigame = false;
    public static double clicks, clickPower;

    public static Item rights, moreRights, bonus, question, lessRights, hack, scam, recovery, buyOrDie;
    public static Item []upgradeList = {rights, moreRights, bonus, question, lessRights, hack, scam, recovery, buyOrDie};
    public static Progress ps;
    public static JPanel pc;
    public static Counter count;

    public void updateProgress() {
        ps.updateProgress();
    }

    public gameVariablesAndMethods(String text) {
        super(text);
    }

    public gameVariablesAndMethods() {}//default constructor
}