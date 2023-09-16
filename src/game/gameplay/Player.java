package game.gameplay;

import game.usefullclases.HexEncoderDecoder;
import game.usefullclases.Sounds;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Player {//Gets, sets the variables / progress of the player

    File fila = new File("Assets\\Date Player.txt");

    String upgradeuri;
    double clicks, clickPower;
    int price;

    public Player (double clicks, double clickPower, int price, String upgradeuri) {
        this.clicks = clicks;
        this.clickPower = clickPower;
        this.price = price;
        this.upgradeuri = upgradeuri;
    }

    public Player () {//new player
        clicks = 0;
        clickPower = 1;
        price = 100;
        upgradeuri = ".";
    }

    public void loadProgress () {
        HexEncoderDecoder bitcoin = new HexEncoderDecoder();

        try {//if the text in the file isn't associated with a save it restarts
            raw = bitcoin.decryptingFile(fila);

            clicks = Double.parseDouble(getFromSave());
            clickPower = Float.parseFloat(getFromSave());
            price = Integer.parseInt(getFromSave());
            upgradeuri = getFromSave();
        } catch (Exception e) {
            System.out.println("Saving file changed, reset progress");
            Player player = new Player();
            player.save();
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();//reads doubles and approximates them
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#0.0", symbols);

        String roundedNumber = decimalFormat.format(clicks);
        clicks = Double.parseDouble(roundedNumber);
    }

    String raw;
    private String getFromSave() {//gets the next variable in the String
        for(int i = 0; i < raw.length(); i++) {
            if(raw.charAt(i) == '\n') {
                String temp = raw.substring(0, i);
                raw = raw.substring(i + 1);
                return temp;
            }
        }
        return raw;
    }

    public void save () {
        HexEncoderDecoder bitcoin = new HexEncoderDecoder();
        String salvare = clicks + "\n" + clickPower + "\n" + price + "\n" + upgradeuri;

        try {
            FileWriter fr = new FileWriter(fila);
            fr.write(bitcoin.cryptingText(salvare));
            fr.close();
        } catch (IOException e) {
            System.out.println("Player saving exception");
        }
    }
}