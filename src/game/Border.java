package game;

import game.usefullclases.Culori;
import game.usefullclases.Constants;

import javax.swing.*;
import java.awt.*;

public class Border extends JPanel {//Border for buttons
    public Color color = Culori.border;

    int x, y, width, height;

    public Border() {
        setLayout(null);
        setVisible(true);
    }

    public Border(ClickableSquare cs, Color color) {
        setLayout(null);
        setVisible(true);

        this.color = color;

        int x = cs.x - 1; //1 pixel thicker than the button
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
        this.x = x; //stores the variables
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

    public void update(int x1, int y1) { //aligns the component
        super.setBounds(Constants.panelVariableX + x - x1, Constants.panelVariableY + y - y1, width, height);
    }
}