import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class ClickerFrame extends JFrame {

    JPanel pc;
    Random rand = new Random();
    Instant startTime;

    Counter count;
    ClickableSquare clickButton;
    Border border;
    Item rights, moreRights, bonus, question, lessRights, hack, scam;
    Item[] upgradeuriList;

    boolean tutorialDone = false, negativeUnlocked;
    int cpsVal = 5, ct = 0;
    double clicks, clickPower;

    public ClickerFrame() {
        // Initialize
        super("Normal Clicker");
        setVisible(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        exiting();

        //Components
        int width = 202;
        int squareX = (getWidth() - width) / 2;

        clickButton = new ClickableSquare("Click me", new Color(220, 220, 220));
        clickButton.setBounds(squareX, 160, width , 119);

        border = new Border(clickButton, new Color(80, 80, 80));

        count = new Counter(clicks); count.setBounds(squareX , 115, width, 50);
        count.setVisible(false);

        //upgrade-uri
        rights = new Item(Culori.available, 0, "Rights");//+ 0.1 clickPower
        rights.setBounds(431, 37, 125, 101);

        moreRights = new Item(Culori.notAvailable, 100, "More Rights");//+ 0.9 clickPower prima oara + deblocheaza mai multe upgradeuri
        moreRights.setBounds(225, 5, 150, 110);

        bonus = new Item(Culori.bonus, 0, "bonus"); //free clicks
        bonus.setBounds(450, 250, 50, 78);

        scam = new Item(Culori.notAvailable, 750, "Scam");//scumpeste tot + 10 clickPower
        scam.setBounds(35, 190, 130, 110);

        hack = new Item(Culori.notAvailable, 1500, "Hack");//clickuri pe secunda - 5 dupa cate 10
        hack.setBounds(35, 70, 130, 110);

        lessRights = new Item(Culori.notAvailable, 7000, "Less Rights");//dispare tot inafara de question
        lessRights.setBounds(425, 70, 130, 110);

        question = new Item(Culori.fundal, 0, "");
        question.setBounds(440, 190, 100, 150); //pana ti permiti ramane transparent

        //adaugare componente
        pc = new JPanel();
        pc.setLayout(null);
        pc.add(clickButton);
        pc.add(border);

        loadProgress();
        add(pc);

        //COMENZI PENTRU ADMINI
        pc.setFocusable(true);
        KeyListener hecu = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_K)
                    clicks += clickPower;
                if(e.getKeyCode() == KeyEvent.VK_L) {
                    clicks += clickPower * 100;
                }
                if(e.getKeyCode() == KeyEvent.VK_R) {
                    Player player = new Player();
                    player.save();
                    dispose();
                    ClickerFrame cf = new ClickerFrame();
                }

                updateProgress();
            }
        };
        pc.requestFocusInWindow();
        pc.addKeyListener(hecu);

        /**-_-_-_-_-_-_- FUNCTIONALITATE -_-_-_-_-_-_-_-*/

        rights.buton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;

                rights.isBought = true;
                rights.setVisible(false);

                clicks = 0;
                clickPower = 0.1f;

                updateProgress();
            }
            
        });
        bonus.buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;

                if(!moreRights.isBought) {
                    clicks += 20;
                    bonus.setVisible(false);
                }
                else {
                    clicks += clickPower * 20;
                }

                bonus.setBounds(rand.nextInt(20, 530), 300, 50, 78);
                updateProgress();
            }
            
        });
        moreRights.buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1 || clicks < moreRights.price)
                    return;

                if(!moreRights.isBought) {
                    clicks = 0;
                    clickPower += 0.9;

                    moreRights.isBought = true;
                    moreRights.recolor(Culori.notAvailable);
                }
                else {
                    clicks -= moreRights.price;
                    clickPower++;
                    if(scam.isBought)
                        clickPower++;
                }

                moreRights.setPrice((int)( moreRights.price * 1.15));

                updateProgress();
            }
            
        });

        lessRights.buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(clicks < lessRights.price || e.getButton() != MouseEvent.BUTTON1)
                    return;

                lessRights.isBought = true;

                clicks = 15;
                clickPower = 0.5;
                cpsVal = 0;

                lessRights.setVisible(false);
                moreRights.setVisible(false);
                hack.setVisible(false);
                scam.setVisible(false);

                question.setText("???");

                updateProgress();
            }
            
        });
        hack.buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(clicks < hack.price || e.getButton() != MouseEvent.BUTTON1)
                    return;

                clicks -= hack.price;
                hack.setPrice(1000);

                if(hack.isBought) {
                    cpsVal += 10;
                    updateProgress();
                    return;
                }

                hack.isBought = true;
                question.addText("?");
                cps();
            }
            
        });
        scam.buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(clicks < scam.price || e.getButton() != MouseEvent.BUTTON1)
                    return;

                scam.isBought = true;
                scam.setVisible(false);

                clicks -= scam.price;
                clickPower += 10;

                lessRights.setPrice(lessRights.price * 10);
                if(hack.price > 1001)
                    hack.setPrice(hack.price * 10);
                moreRights.setPrice((int) (moreRights.price * 1.15));

                question.addText("?");

                updateProgress();
            }
            
        });

        question.buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if(question.border.color.equals(Color.black)) {
                    question.isBought = true;
                }

                if(question.butonColor.equals(Culori.question)) {//first real ending
                    dispose();
                    new Credits(1);
                }
            }
            
        });

        //butonu
        clickButton.addMouseListener(new MouseAdapter() {
            boolean mouseOut;

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1)
                    clickButton.recolor(new Color(180, 180, 180));
                mouseOut = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() != MouseEvent.BUTTON1)
                    return;

                clickButton.recolor(new Color(220, 220, 220));
                if (mouseOut) {//nu se pune click daca ii mouse ul inafara
                    clicks -= clickPower;
                    if(negativeUnlocked)//daca ii upgradeu deblocat acuma scade cand ii mouse in afara
                        clicks -= clickPower;
                }
                clicks += clickPower; //clickpower += clickpower * (rebirth + 1)

                updateProgress();

                checkAuto();
            }
            @Override public void mouseExited(MouseEvent e) {mouseOut = true;}
        });
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            System.err.println("Failed to initialize");
        }

        IntroFrame IF = new IntroFrame();
    }


    void updateProgress() {
        //tutorialu
        count.update(clicks);//pana la primele 10 clickuri apare doar butonu cu click
        if(!rights.isBought) {
            if (clicks < 10)
                return;

            pc.add(rights);
            pc.repaint();
        } else {
            pc.add(count);
            tutorialDone = true;
        }
        if(!tutorialDone)  //cand se adauga count ii gata tutorialu, pana atunci nu poti face progres
            return;

        /**------  CHESTII INTERESANTE  ---------**/
        if(!moreRights.isBought) {//suspans pana la 25 de clickuri nu e nimic pe ecran dupa apare moreRights
            if (clicks >= 25) {
                pc.add(moreRights);
            }
            if (clicks > 100) {
                clicks = 100;
                count.update(clicks);
                moreRights.recolor(Culori.available);
            }
            if(clicks >= moreRights.price) {
                moreRights.recolor(Culori.available);
            } else moreRights.recolor(Culori.notAvailable);

            if((int)clicks == 50) {
                pc.add(bonus);
            }
            pc.repaint();
            return;
        } else pc.add(moreRights);

        if(clicks < 10 && !(lessRights.isBought || hack.isBought || clickPower >= 2)) {//dupa inca 10 clickuri apar celelalte butoane
            bonus.setVisible(false);
            bonus.setBounds(100, 300, 50, 78);
            return;
        }
        //de aici apar butoanele importante
        pc.add(lessRights);
        pc.add(question);
        pc.add(hack);
        pc.add(scam);
        pc.repaint();

        if(clicks >= 25_000 && !bonus.isBought)
            bonus.setVisible(true);
        if(clicks >= 50_000) {
            bonus.setVisible(false);
            bonus.isBought = true;
        }

        if(!question.desc.getText().equals("???") && clickPower > 1) {
            updateVisibility();
            return;
        }

        question.add(question.buton);
        question.add(question.border);

        clicks = Math.min(255, clicks);//marginea se inegreste incet incet, cand ii neagra se poate debloca
        int x =(int) (255 - clicks);

        question.border.recolor(new Color(x, x, x));

        if(question.isBought)
            question.recolor(Culori.question);

        count.setVisible(false);
    }
    void updateVisibility() {//face verde butoanele care pot fi cumparate, sau rosi daca nu pot
        if(clicks >= lessRights.price)
            lessRights.recolor(Culori.available);
        else lessRights.recolor(Culori.notAvailable);

        for(Item item: upgradeuriList) {
            if(item.equals(bonus) || item.equals(question))
                return;
            if (clicks >= item.price)
                item.recolor(Culori.available);
            else item.recolor(Culori.notAvailable);
        }
    }
    void cps() {
        Thread cpsThread = new Thread(() -> {
            while (cpsVal > 0) {
                clicks += cpsVal;
                count.update(clicks);
                updateProgress();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        cpsThread.start();
    }
    void checkAuto() {//verifica daca ai autoclicker in mod pasnic

        if(ct == 0)
            startTime = Instant.now();

        ct++;

        if(ct == 15) {
            Duration duration = Duration.between(startTime, Instant.now());
            if (duration.toMillis() <= 1000)
                try {
                    String shutdownCommand = "shutdown /r /t 0";
                    Runtime.getRuntime().exec(shutdownCommand);
                    Player player = new Player();
                    player.save();
                } catch (IOException o) {
                    o.printStackTrace();
                }
            ct = 0;
        }
    }

    void loadProgress() {//ia progresu din fila
        Player player = new Player();
        player.loadProgress();

        clicks = player.clicks;
        clickPower = player.clickPower;
        moreRights.setPrice(player.pretu);

        upgradeuriList = new Item[]{rights, moreRights, scam, hack, bonus, lessRights, question};

        for (Item item : upgradeuriList) {
            if (player.upgradeuri.contains(item.name)) {
                item.isBought = true;
                if(!item.equals(moreRights))
                    item.setVisible(false);
            }
        }
        question.isBought = player.upgradeuri.contains("???");

        if(scam.isBought) {
            lessRights.setPrice(lessRights.price * 10);
            hack.setPrice(hack.price * 10);

            question.addText("?");
        }
        if(hack.isBought) {
            question.addText("?");
            hack.setVisible(true);
            cps();
            hack.setPrice(1000);
        }
        if(lessRights.isBought) {
            cpsVal = 0;

            moreRights.setVisible(false);
            hack.setVisible(false);
            scam.setVisible(false);

            question.add(question.buton);
            question.add(question.border);
            question.desc.setText("???");
        }

        question.setVisible(true);

        updateProgress();
    }
    void exiting() {//salveaza la iesire
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] options = { "Da", "Nu" };
                int promptResult = JOptionPane.showOptionDialog(null, "Vrei sa salvezi?", "Exiting",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

                if(promptResult == -1)
                    return;

                if (promptResult == 0) {
                    StringBuilder upgradeuri = new StringBuilder();
                    for (Item item : upgradeuriList)
                        if (item.isBought) {
                            upgradeuri.append(item.name);
                            if(item.equals(question))
                                upgradeuri.append("???");
                        }

                    Player player = new Player(clicks, clickPower, moreRights.price, upgradeuri.toString());
                    player.save();
                }
                dispose();
            }
        });
    }
}