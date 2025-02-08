package Unit0.School;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AthleteTest {

    private Athlete a;

    @BeforeEach
    public void setUpTests(){
        a = new Athlete(5.5, 69, "Golfer");
    }

    @Test
    public void testConstructor(){
        assertEquals(5.5, a.getHeight());
        assertEquals(69, a.getAge());
        assertEquals("Golfer", a.getCareer());
    }

    @Test 
    public void testMutator(){
        a.changeCareer("Basketball");
        assertEquals("Golfer", a.getCareer());
        
        a = new Athlete(5.5, 18, "Golfer");
        a.changeCareer("Basketball");
        assertEquals("Basketball", a.getCareer());
    }

    @Test
    public void testIsValid(){
        a = new Athlete(-1, 69, "Golfer");
        assertFalse(a.isValid());

        a = new Athlete(5.5, -5, "Golfer");
        assertFalse(a.isValid());

        a = new Athlete(5.5, 69, "    ");
        assertFalse(a.isValid());

        a = new Athlete(5.5, 69, "");
        assertFalse(a.isValid());
    
    }

}
