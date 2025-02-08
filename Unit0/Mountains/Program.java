package Unit0.Mountains;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Program {
    public static void main(String[] args) throws IOException{
        Path dbPath = Paths.get("./Unit0/Mountains/Files/mountains_db.tsv");
        BufferedReader br = Files.newBufferedReader(dbPath, StandardCharsets.UTF_8);
        
        Path cleanPath = Paths.get("./Unit0/Mountains/Files/mountains_clean.tsv");
        BufferedWriter cbw = Files.newBufferedWriter(cleanPath, StandardCharsets.UTF_8);
        
        Path errPath = Paths.get("./Unit0/Mountains/Files/mountains_err.tsv");
        BufferedWriter ebw = Files.newBufferedWriter(errPath, StandardCharsets.UTF_8);
        
        //write the header line to both files 
        String header = br.readLine();
        cbw.write(header + "\n");
        ebw.write(header + "\n");

        int valid = 0;
        int corrupted = 0; 
        double smallest = Double.MAX_VALUE; 
        double greatest = Double.MIN_VALUE;

        while(br.ready()){
            String line = br.readLine();
            try{
                //create mountain object
                @SuppressWarnings("unused")
                Mountain mount = new Mountain(line);

                //iterator for counting # of valid mountains
                valid++;

                //checking whether mountain elevation is lowest or highest elevation.
                double height = mount.parseElevation();
                if( height< smallest){
                    smallest = height;
                }
                
                if(height > greatest){
                    greatest = height;
                }
                cbw.write(line + "\n");

            }catch(RuntimeException e){
                corrupted++;
                ebw.write(line + ": " + e.getMessage() + "\n");
            }
        }

        // i. Size of the original and of the cleaned database (file sizes for mountains_db.tsv and mountains_clean.tsv)
        System.out.println("Original Size: " + Files.size(dbPath) + " Cleaned Size: " + Files.size(errPath));

        // ii. The count of valid and corrupted records (lines) from the original database.
        System.out.println("Valid Records: " + valid + " Corrupted Records: " + corrupted);

        // iii. The full record for the shortest and the tallest mountains in the database.
        System.out.println("The highest mountain elevation is " + greatest + " m." + " The lowest mountain is " + smallest + " m.");

        br.close();
        cbw.close();
        ebw.close();
    
    }
}
