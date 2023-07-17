import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Player {

    File fila = new File("Date Player.txt");

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
        try {
            File temp = File.createTempFile("TempFile", "txt");//creates a normal file
            Scanner scanner = new Scanner(temp);
            Encoder bitcoin = new Encoder();

            try {//if the text in the file isn't associated with a save it restarts
            FileWriter fr = new FileWriter(temp);
            fr.write(bitcoin.decryptingFile(fila));
            fr.close();

                clicks = Float.parseFloat(scanner.next());
                clickPower = Float.parseFloat(scanner.next());
                price = Integer.parseInt(scanner.next());
                scanner.nextLine();
                upgradeuri = scanner.nextLine();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NoSuchElementException e) {
                Player player = new Player();
                player.save();
            }

            temp.delete();

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();//reads doubles and aproximates them
            symbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#0.0", symbols);

            String roundedNumber = decimalFormat.format(clicks);
            clicks = Double.parseDouble(roundedNumber);

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save () {
        Encoder bitcoin = new Encoder();
        String salvare = clicks + "\n" + clickPower + "\n" + price + "\n." + upgradeuri;

        try {
            FileWriter fr = new FileWriter(fila);
            fr.write(bitcoin.cryptingText(salvare));
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}