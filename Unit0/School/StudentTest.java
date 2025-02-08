package Unit0.School;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class StudentTest {
    
    private  Student s;

    @BeforeEach
    public void setUpTests(){
        s = new Student(10,"Ishan Sharma" , 1048420);
    }

    @Test
    public void testConstructor(){   
        assertEquals(1048420, s.getID()); 
        assertEquals("Ishan Sharma", s.getName());
        assertEquals(10, s.getGrade());
    }

    @Test
    public void testSetName(){
        assertEquals("Ishan Sharma", s.getName());
        s.setName("Iyas");
        assertEquals("Iyas", s.getName());
    }

    @Test
    public void testIsValid(){
        assertTrue(s.isValid());

        s = new Student(13, "Ishan Sharma" , 1048420); 
        assertEquals(false, s.isValid());

        s = new Student(12,null , 1048420); 
        assertFalse(s.isValid());

        s = new Student(12,"Ishan Sharma" , -10); 
        assertFalse(s.isValid());

        s = new Student(12, "    " , 1048420); 
        assertEquals(false, s.isValid());        
    }

    @Test
    public void testToString(){
        assertEquals("id: 1048420, name:Ishan Sharma, grade:10", s.toString());
    }


}
