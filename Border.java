import javax.swing.*;
import java.awt.*;

public class Border extends JPanel {
    Color color = Culori.border;

    MyConstants m = new MyConstants();

    int x, y, width, height;

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
    public void setBounds (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        reshape(x, y, width, height);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void update(int x1, int y1) {
        super.setBounds(m.panelVariableX + x - x1, m.panelVariableY + y - y1, width, height);
    }
}