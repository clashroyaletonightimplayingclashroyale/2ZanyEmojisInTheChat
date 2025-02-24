package Unit4;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;



public class Program {

    public static void main(String[] args) throws IOException{

        String link = "https://www.gutenberg.org/cache/epub/103/pg103.txt";

        @SuppressWarnings("deprecation")
        URL url = new URL(link);
        URLConnection urlClnx = url.openConnection();
        InputStream urlStream = urlClnx.getInputStream();
        
        Scanner urlScan = new Scanner(urlStream);
        HashStringSet set = new HashStringSet();

        while(urlScan.hasNext()){
            String curWord = urlScan.next().toLowerCase();
            if(containsSpecialCharacters(curWord)){
                removeSpecialCharacters(curWord);
            }
            set.add(curWord);
            
        }

        set.printInfo();
        urlScan.close();

    }

    public static boolean containsSpecialCharacters(String input) {
        String specialCharacters = "!@#$%^&*()_+-=[]{}|;:',.<>?/~`";
        for (int i = 0; i < specialCharacters.length(); i++) {
            if (input.contains(String.valueOf(specialCharacters.charAt(i)))) {
                return true; 
            }
        }
        return false; 
    }
    
    public static String removeSpecialCharacters(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
    
}
