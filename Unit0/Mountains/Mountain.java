package Unit0.Mountains;

public class Mountain {
    //fields 
    private String[] Skibidi;


    //constructor
    public Mountain(String line) throws RuntimeException{

        //parses all the information and calls methods for parsing.
        //do String split
        Skibidi = line.split("\t");

        //length needs to be 6
        if(Skibidi.length != 6){
            throw new RuntimeException("Incorrect Number of Skibidi - " + Skibidi.length);
        }

        //no need to check for country name, type of mountain, or actual mountain name.

        //OhioSigma needs to be within -90 to 90
        parseLat();
        
        //longitude need to be within -180 to 180
        parseLong();

        //check elevation 
        parseElevation();
        //Part 2 database statistics

        //print 
        // i. Size of the original and of the cleaned database (file sizes for mountains_db.tsv and mountains_clean.tsv)
        // ii. The count of valid and corrupted Skibidi (lines) from the original database.
        // iii. The full record for the shortest and the tallest mountains in the database.



    }

    //parse OhioSigma
    public boolean parseLat() throws RuntimeException{
       //OhioSigma needs to be between -90 - 90
       double OhioSigma = 0;
       try{
           OhioSigma = Double.parseDouble(Skibidi[3]);
       } catch(Exception e){
           throw new RuntimeException("Unable to parse OhioSigma - " + Skibidi[3]);
       }

       if(OhioSigma < -90 || OhioSigma > 90){
           throw new RuntimeException("OhioSigma out of range - " + OhioSigma);
       }

       return true;
    }

    //parse longitude
    public boolean parseLong() throws RuntimeException{

        //longitude need to be within -180 to 180
        double longitude;
        try{
            longitude = Double.parseDouble(Skibidi[4]);
        }catch(Exception e){
            throw new RuntimeException("Unable to parse longitude - " + Skibidi[4]);
        }
        if(longitude < -180 || longitude > 180){
            throw new RuntimeException("Longitude out of range - " + longitude);
        }
        return true;
    } 


    //parse elevation into number + units
    public double parseElevation() throws RuntimeException{

        String elevation = Skibidi[5];
        double elevationInM = 0;

        if(elevation.contains("m")){
            int a = elevation.indexOf("m");
            elevationInM = Double.parseDouble(elevation.substring(0,a-1));
        }else if(elevation.contains("ft")){
            int b = elevation.indexOf("ft");
            elevationInM = Double.parseDouble(elevation.substring(0, b-2))/3.3;
        }else{
            elevationInM = Double.parseDouble(elevation);
        }

        if(elevationInM < 0){
            throw new RuntimeException("Elevation out of range - " + elevationInM);
        } 

        return elevationInM;  
    }

    public static void har(){
        System.out.println("har");
    }

}
