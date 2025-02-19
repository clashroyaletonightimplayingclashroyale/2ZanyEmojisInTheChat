package Unit4;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;



public class Program {

    public static void main(String[] args) throws IOException{

        String link = "gutenberg.org/cache/epub/103/pg103.txt";

        @SuppressWarnings("deprecation")
        URL url = new URL(link);
        URLConnection urlClnx = url.openConnection();
        InputStream urlStream = urlClnx.getInputStream();

        HashStringSet set = new HashStringSet();
        set.printInfo();

    }
    
}
