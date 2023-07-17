import javax.swing.*;
import java.awt.*;

public class Counter extends JLabel {
    MyConstants m = new MyConstants();
    int x, y, width, height;

    public Counter(double clicks) {
        setFont(new Font("Montserrat", Font.PLAIN, 20));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVisible(false);

        update(clicks);
    }

    public void update(double clicks) {
        if((clicks <= 0 && clicks > -0.099) || (clicks >= 0 && clicks < 0.099))//it appears some 23e132.2 number if not
            clicks = 0;

        String copy = Integer.toString((int) clicks);
        String customClicks = copy.substring(0, 1);

        for (int i = 1; i < copy.length(); i++) {
            if ((copy.length() - i) % 3 == 0)
                customClicks += ".";
            customClicks += copy.substring(i, i + 1);
        }

        String numberString = Double.toString(clicks);
        int decimalIndex = numberString.indexOf('.');
        String decimalPart = numberString.substring(decimalIndex + 1, decimalIndex + 2);


        if (!(decimalPart.length() == 1 && decimalPart.charAt(0) == '0'))
            customClicks += "," + decimalPart;

        if (customClicks.indexOf("-") == 0)
            if (customClicks.charAt(1) == '.')
                customClicks = "-" + customClicks.substring(2);

        setText("Count = " + customClicks);

        setVisible(!customClicks.equals("0"));//if value = 0 doesnt show
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

    public void update(int x1, int y1) {
        super.setBounds(m.panelVariableX + x - x1, m.panelVariableY + y - y1, width, height);
    }
}