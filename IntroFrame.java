import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class IntroFrame extends JFrame {
    // Interface
    JButton Continue, newGame;
    JLabel intro;
    JPanel mp, bp;
    boolean stai = true;

    public IntroFrame() throws InterruptedException {
        // Initialization
        super("Intro");
        setVisible(true);
        setResizable(false);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        intro = new JLabel();
        intro.setFont(new Font("Montserrat", Font.PLAIN, 24));

        newGame = new JButton(); newGame.setFont(new Font("Montserrat", Font.PLAIN, 22));
        Continue = new JButton(); Continue.setFont(new Font("Montserrat", Font.PLAIN, 22));

        Continue.setFocusPainted(false);
        newGame.setFocusPainted(false);

        mp = new JPanel();
        mp.setLayout(new GridBagLayout());

        bp = new JPanel(); bp.setBounds(110, 200, 480, 270);
        bp.setLayout(null);


        // Introducerea

        add(mp);
        mp.add(intro);
        typing("Loading.", "..", 2);

        while(stai) {System.out.print(' '); System.out.print('\b');}
        Thread.sleep(1050);

        mp.setBounds(0, 0, 585, 100);
        Continue.setBounds(100, 180, 150, 80);
        newGame.setBounds(350, 180, 150, 80);

        bp.add(Continue);
        bp.add(newGame);
        add(bp);

        Continue.setText("Continue");
        newGame.setText("New Game");

        File fila = new File("Date Player.txt");

        Continue.addActionListener(e -> {
            if(!fila.exists())
                return;
            dispose();

            Player player = new Player();
            player.loadProgress();

            new ClickerFrame();
        });

        newGame.addActionListener(e -> {
            if(fila.exists()) {
                String[] Optiuni = {"Da", "Nu"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Esti sigur?", " Start a New Save",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Optiuni, Optiuni[1]);
                if (PromptResult != 0) {
                    return;
                }
            }

            dispose();
            Player player = new Player();
            player.save();

            new ClickerFrame();
        });
    }

    public void typing( String message, float speed) {
        int delay = (int) (100 / speed); // Delay between each character in milliseconds

        Timer timer = new Timer(delay, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                mp.setLayout(new FlowLayout());
                if (index < message.length()) {
                    intro.setText(message.substring(0, index + 1));
                    index++;
                } else {
                    stai = false;
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    public void typing(String first, String cuv, int times) {//secventa de inceput
        intro.setText(first);
        Timer timer = new Timer(525, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count == cuv.length()) {
                    if (times > 0)
                        typing( first, cuv, times - 1);
                    else {
                        intro.setText("");
                        typing("Welcome to Normal Clicker", 0.75f);
                    }
                    ((Timer) e.getSource()).stop();
                }
                intro.setText(first + cuv.substring(0, count));
                count++;
            }
        });
        timer.start();
    }
}