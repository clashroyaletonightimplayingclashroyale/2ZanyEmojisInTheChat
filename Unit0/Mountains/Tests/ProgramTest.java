package Unit0.Mountains.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import Unit0.Mountains.Mountain;

public class ProgramTest {
    
    @Test 
    public void testCheckValid(){
        String valid = "Honduras	Volcano	Las Minas Peak	14.539319	-88.691108	2849 m";
        Mountain mount = new Mountain(valid);

        //test long lat and elevation
        assertEquals(true, mount.parseLat());
        assertEquals(true, mount.parseLong());
        assertEquals(2849.0, mount.parseElevation());
        

        //fail bad number of records
        String badNumberOfRecords = "Honduras Volcano Las Minas Peak	14.539319	-88.691108	2849 m";
        try{
            Mountain m1 = new Mountain(badNumberOfRecords);
        }catch(RuntimeException e){
            assertEquals("Incorrect Number of Records - 4", e.getMessage());
        }

        //fail incorrect latitude
        String badLat = "Honduras\tVolcano\tLas Minas Peak\t-99\t-88.691108\t2849 m";
        try{
            Mountain m1 = new Mountain(badLat);
        }catch(RuntimeException e){
            assertEquals("Latitude out of range - -99.0", e.getMessage());
        }

        //fail incorrect longitude
        String badLong = "Honduras\tVolcano\tLas Minas Peak\t30\t181\t2849 m";
        try{
            Mountain m1 = new Mountain(badLong);
        }catch(RuntimeException e){
            assertEquals("Longitude out of range - 181.0", e.getMessage());
        }

        //fail incorrect elevation
        String badElevation = "Honduras\tVolcano\tLas Minas Peak\t30\t111\t-138 m";
        try{
            Mountain m1 = new Mountain(badElevation);
        }catch(RuntimeException e){
            assertEquals("Elevation out of range - -138.0", e.getMessage());
        }

    }
}
