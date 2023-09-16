package game;

import game.usefullclases.Constants;

import javax.swing.*;
import java.awt.*;

public class ClickableSquare extends JPanel {//The clickable part of a button
    Color color;

    String cuv;
    int x, y, width, height;

    public ClickableSquare (Color color) {
        this.color = color;
    }

    public ClickableSquare(String cuv, Color color) {
        setLayout(null);

        this.cuv = cuv;//storing variables
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
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (cuv == null || cuv.isEmpty())
            return;

        //antialiasing for the text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Montserrat", Font.PLAIN, 20));

        FontMetrics fm = g2d.getFontMetrics();//Aligns text in the middle
        int textWidth = fm.stringWidth(cuv);
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + fm.getAscent();

        g2d.setColor(Color.BLACK);
        g2d.drawString(cuv, x, y);
    }

    public void setText(String x) {
        cuv = x;
        repaint();
    }

    public void update(int x1, int y1) {
        super.setBounds(Constants.panelVariableX + x - x1, Constants.panelVariableY + y - y1, width, height);
    }
}