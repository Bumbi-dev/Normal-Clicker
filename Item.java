import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Item extends JPanel {

    Border border;
    ClickableSquare buton;
    JLabel desc;
    Color butonColor;
    Color borderColor = Culori.border;

    String name = "";
    boolean isBought = false;
    int price;

    public Item(Color color, int prais, String name) {
        setLayout(null);
        this.butonColor = color;
        this.name = name;
        this.price = prais;

        if(name.equals("")) {
            prais = 0;//face pretu invisibil
            borderColor = Culori.fundal;
        }

        border =  new Border();

        if(prais != 0)
            buton = new ClickableSquare(String.valueOf(prais), color);
        else
            buton = new ClickableSquare(color);

        desc = new JLabel(name); desc.setFont(new Font("Montserrat", Font.PLAIN, 20));
        desc.setHorizontalAlignment(SwingConstants.CENTER);

        if(!name.equals("")) {
            add(buton);
            add(border);
        }
        if(!name.equals("bonus"))
            add(desc);

        /**** Functionalitate ****/
        buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;
                if (!butonColor.equals(Culori.fundal))// da efectu de apasare
                    buton.recolor(butonColor.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {buton.recolor(butonColor);}
        });
    }

    public void recolor(Color color) {
        this.butonColor = color;
        buton.recolor(color);
        repaint();
    }

    public void setPrice(int price) {
        buton.setText(String.valueOf(price));
        this.price = price;
    }

    public void addText(String text) {
        text = desc.getText() + text;
        desc.setText(text);
        add(desc);
        name = text;
        repaint();
    }

    public void setText(String text) {
        desc.setText(text);
        add(desc);
        name = text;
        repaint();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        x = x - x % 4 + 3;//algoritm pentru aliniere, sa fie uniforma marginea
        y = y - y % 4 + 1;
        width = width - width % 4 + 1;
        height = height - height % 4 + 1;

        reshape(x, y , width, height);

        border.setBounds(0, 30, width, height - 30); //2 pixeli grosime
        buton.setBounds(2, 32, width - 4, height - 34);
        desc.setBounds(0, 0, width, 30);

        if(name.equals("bonus")) {
            remove(desc);
            desc.setVisible(false);
            border.setBounds(0, 0, width, height - 28);
            buton.setBounds(2, 3, width - 4, height - 33);
        }
    }
}