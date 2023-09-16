package game.usefullclases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HexEncoderDecoder {//"Encrypts" / "Decrypts" the save in hex

    public String cryptingText(String text) {//returns the text in hex
        char []ch = text.toCharArray();
        StringBuilder sb = new StringBuilder();

        for(char c : ch)
            sb.append(Integer.toHexString(c));

        return sb.toString();
    }

    public String decryptingFile(File fila) throws FileNotFoundException {//returns text from file in hex
        String text = fileToString(fila);
        char []ch = text.toCharArray();
        text = "";
        int i = 0;

        while (i < ch.length) {
            String cuv;
            if (ch[i] == 'a') {
                text += '\n';
                i++;
            } else {
                cuv = ""+ ch[i] + ""+ ch[++i];
                i++;
                text += (char) Integer.parseInt(cuv, 16);
            }
        }

        return text;
    }

    String fileToString(File fila) throws FileNotFoundException {//returns the contents of a file as a String
        Scanner scanner = new Scanner(fila);
        String result = "";

        while(scanner.hasNext()){
            result += scanner.next();
        }

        return result;
    }
}