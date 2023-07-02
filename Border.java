import javax.swing.*;
import java.awt.*;

public class Border extends JPanel {
    Color color = Culori.border;

    public Border() {
        setLayout(null);
        setVisible(true);
    }

    public Border(ClickableSquare cs, Color color) {
        setLayout(null);
        setVisible(true);

        this.color = color;

        int x = cs.x - 1;
        int y = cs.y - 1;
        int width = cs.width + 2;
        int height = cs.height + 2;

        setBounds(x, y , width, height);
    }

    public void recolor(Color color) {
        this.color = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
