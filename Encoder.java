import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Encoder {

    public String cryptingText(String text) {
        char []ch = text.toCharArray();
        StringBuilder sb = new StringBuilder();

        for(char c : ch)
            sb.append(Integer.toHexString(c));

        return sb.toString();
    }

    public String decryptingFile(File fila) throws FileNotFoundException {
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

    String fileToString(File fila) throws FileNotFoundException {
        Scanner scanner = new Scanner(fila);
        String res = "";

        while(scanner.hasNext()){
            res += scanner.next();
        }

        return res;
    }
}
