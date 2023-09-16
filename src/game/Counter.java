package game;

import game.usefullclases.Constants;

import javax.swing.*;
import java.awt.*;

public class Counter extends JLabel {//Displays the number of clicks you have;
    int x, y, width, height;

    public Counter(double clicks) {
        setFont(new Font("Montserrat", Font.PLAIN, 20));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVisible(false);

        update(clicks);
    }

    public void update(double clicks) {
        if((clicks <= 0 && clicks > -0.099) || (clicks >= 0 && clicks < 0.099))//solved some bugs with decimal numbers
            clicks = 0;

        String copy = Integer.toString((int) clicks);
        StringBuilder customClicks = new StringBuilder(copy.substring(0, 1));

        for (int i = 1; i < copy.length(); i++) {//puts dot before any 3 digits i.e. 1.000 or 4.420.512
            if ((copy.length() - i) % 3 == 0)
                customClicks.append(".");
            customClicks.append(copy.charAt(i));
        }

        String numberString = Double.toString(clicks);
        int decimalIndex = numberString.indexOf('.');
        String decimalPart = numberString.substring(decimalIndex + 1, decimalIndex + 2);


        if (!(decimalPart.length() == 1 && decimalPart.charAt(0) == '0'))
            customClicks.append(",").append(decimalPart);

        if (customClicks.indexOf("-") == 0)
            if (customClicks.charAt(1) == '.')
                customClicks = new StringBuilder("-" + customClicks.substring(2));

        setText("Count = " + customClicks);

        setVisible(!customClicks.toString().equals("0"));//if value = 0 doesn't show
    }

    @Override
    public void setBounds (int x, int y, int width, int height) {
        this.x = x;//stores variables
        this.y = y;
        this.width = width;
        this.height = height;

        reshape(x, y, width, height);
        repaint();
    }

    public void update(int x1, int y1) {
        super.setBounds(Constants.panelVariableX + x - x1, Constants.panelVariableY + y - y1, width, height);
    }
}