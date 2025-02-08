package Unit4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestHashStringSet {
    
    @Test
    public void basicTest(){
        HashStringSet hash = new HashStringSet("FB", "eA", "Dog", "Cheese");
        assertTrue(hash.contains("FB"));
        assertTrue(hash.contains("eA"));
        assertTrue(hash.contains("Dog"));
        assertTrue(hash.contains("Cheese"));
        assertEquals(hash.size(),4);
        assertFalse(hash.contains("sigma sigma boy"));
    }

    @Test 
    public void testRemove(){
        HashStringSet hash = new HashStringSet("FB", "eA", "Dog", "Cheese");
        hash.remove("Dog");
        hash.remove("eA");
        assertTrue(hash.contains("FB"));
        assertFalse(hash.contains("eA"));
        assertFalse(hash.contains("Dog"));
        assertTrue(hash.contains("Cheese"));
        assertEquals(hash.size(),2);
        assertFalse(hash.contains("sigma sigma boy"));  
    }

    @Test 
    public void testLoadFactor(){
        HashStringSet hash = new HashStringSet("FB", "eA", "Dog", "Cheese");
        assertEquals(0.4, hash.loadFactor(), 0.000001);
    }
    
    @Test
    public void testRehash(){
        int numToAdd = 5000;
        int buckets = 10;
        double maxLoad = HashStringSet.MAX_LOAD;

        HashStringSet hash = new HashStringSet();

        for(int i = 1; i <= numToAdd; i++){
            hash.add("" + i);
            double expectedLoad = (double) i / buckets;
            if(expectedLoad > maxLoad){
                buckets*=2;
                expectedLoad = (double) i / buckets;
            }
            assertEquals(expectedLoad, hash.loadFactor(), 0.00001);
        }
        for(int i = 1; i <= numToAdd; i++){
            assertTrue(hash.contains("" +i));
        }
        assertFalse(hash.contains("" + (numToAdd+1)));
    }
}
