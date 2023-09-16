package game.screens;

import game.gameplay.ClickerFrame;
import game.gameplay.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Credits extends JPanel implements ActionListener {
    JFrame window;
    Timer timer = new Timer(20, this);

    public final static int NORMAL_ENDING = 1;
    public final static int DEATH = 2;
    public final static int GOOD_ENDING = 3;

    public static ClickerFrame cf;

    String text = "\n\n\n\n";
    String customLine = "";
    int textY = 400;
    int ending = -1;

    public Credits(int ending, String customLine) {
        this.ending = ending;
        this.customLine = "\n\n" + customLine;

        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(this::rollingCredits);
    }

    public Credits(int ending) {
        this.ending = ending;

        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(this::rollingCredits);
    }

    public Credits(String text) {//Custom ending first line
        this.text = text;

        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(this::rollingCredits);
    }

    private void rollingCredits() {//Displays the Credits scene
        File fila = new File("Date Player.txt");//deletes the save
        fila.delete();

        try {
            cf.dispose();//it closes the gameplay window if it wasn't already
        }catch (Exception ignored) {}

        window = new JFrame("Credits");
        window.add(this);
        window.setResizable(false);
        window.setSize(600, 400);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        switch (ending) {//unique message
            case 1 -> text = "You finished the Normal ENDING";//first ending
            case 2 -> text = "\"F\"" + customLine;//Any death ending
            case 3 -> text = "\n\nFinally... \n\n Someone finished THE GOOD ENDING \n\n\n Congratulations man!" +
                    "\n\n\nMore coming soon.";
            case -1 -> text += "";//blank message

            default -> text = "You finished ENDING: " + ending;
        }

        this.text += "\n\n\n\"A Normal Clicker\"\n\n\n" +
                "DEVELOPER:               \n" +
                "Manu\n\n\n" +
                "TESTER:                      \n" +
                "Dani\n\n\n\n\n" +
                "Thank you for playing!";

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d =  (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Montserrat", Font.PLAIN, 26));

        int y = textY;

        for(String line : text.split("\n")) {
            int stringLength = (int) g2d.getFontMetrics().getStringBounds(line, g2d).getWidth();
            int x = (getWidth() - stringLength) / 2;

            g2d.drawString(line, x, y += 28);
        }
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if(textY <= -480) {
            timer.stop();
            window.dispose();
        }

        textY--;
        repaint();
    }
}