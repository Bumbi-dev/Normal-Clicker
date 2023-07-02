import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Player {

    File fila = new File("Date Player.txt");

    String upgradeuri;
    double clicks, clickPower;
    int pretu;

    public Player (double clicks, double clickPower, int pretu, String upgradeuri) {
        this.clicks = clicks;
        this.clickPower = clickPower;
        this.pretu = pretu;
        this.upgradeuri = upgradeuri;
    }

    public Player () {//inceputu
        clicks = 0;
        clickPower = 1;
        pretu = 100;
        upgradeuri = ".";
    }

    public void loadProgress () {
        try {
            File temp = File.createTempFile("TempFile", "txt");//creaza fisier temp care nu ii "criptat"
            Scanner scanner = new Scanner(temp);
            Encoder bitcoin = new Encoder();

            try {//daca se schimba ceva prin fisier => restart progress
            FileWriter fr = new FileWriter(temp);
            fr.write(bitcoin.decryptingFile(fila));
            fr.close();

                clicks = Float.parseFloat(scanner.next());
                clickPower = Float.parseFloat(scanner.next());
                pretu = Integer.parseInt(scanner.next());
                scanner.nextLine();
                upgradeuri = scanner.nextLine();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NoSuchElementException e) {
                Player player = new Player();
                player.save();
            }

            temp.delete();

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();//citeste doubles si le rotunjeste
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
        String salvare = clicks + "\n" + clickPower + "\n" + pretu + "\n." + upgradeuri;

        try {
            FileWriter fr = new FileWriter(fila);
            fr.write(bitcoin.cryptingText(salvare));
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
