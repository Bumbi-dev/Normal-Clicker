import javax.swing.*;
import java.awt.*;

public class ClickableSquare extends JPanel {
    JLabel text;

    Color color;

    String cuv;
    int x, y, width, height;

    public ClickableSquare (Color color) {
        this.color = color;
    }

    public ClickableSquare(String cuv, Color color) {
        setLayout(null);

        this.cuv = cuv;
        this.color = color;
    }

    public void recolor(Color color) {
        this.color = color;
        repaint();
    }

    @Override
    public void setBounds (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        reshape(x, y, width, height);

        //pune text pe mijloc
        text = new JLabel(cuv);
        text.setFont(new Font("Montserrat", Font.PLAIN, 20));
        text.setBounds(0, 0, getWidth(), getHeight());
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
        add(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setText(String x) {
        text.setText(x);
        repaint();
    }
}