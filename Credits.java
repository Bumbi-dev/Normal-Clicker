import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.RenderingHints;

public class Credits extends JPanel implements ActionListener {

    Timer timer = new Timer(20, this);

    String text;
    int textY = 400;
    JFrame window;

    public Credits(int ending) {

        window = new JFrame("Credits");
        window.add(this);
        window.setResizable(false);
        window.setSize(600, 400);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);


        text =  "CONGRATULATIONS!\n\n" +
                "You finished the first ENDING\n\n\n" +
                "\"A Normal Clicker\"\n" +
                "made by Manu\n\n\n" +
                "Tested by:\n" +
                "Dani\n\n\n\n\n" +
                "Thank you for playing!";

        repaint();

        Player player = new Player();
        player.save();
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

    public static void main(String []args) {
        System.setProperty("sun.java2d.opengl", "true"); //hardware acceleration for smoother scrolling
        SwingUtilities.invokeLater(() -> new Credits(1));
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
