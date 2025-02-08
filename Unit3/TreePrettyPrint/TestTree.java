package Unit3.TreePrettyPrint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

public class TestTree {
    public static void main(String[] args) {  
        // int[] array = {7,4,10,2,6,8,12, 9, 11, 15};

        // for(int i = 0; i < 10; i++){
        //    tree.addValue(array[i]); 
        // }
    }
    
    public IntTree tree = new IntTree();


    @BeforeEach
    public void add(){
        tree.addValue(2);
        tree.addValue(1);
        tree.addValue(4);
        tree.addValue(3);
        tree.addValue(5);
    }

    @Test
    public void testPreOrder(){
        String x = tree.toPreOrderString();
        assertEquals("[2]([1]()())([4]([3]()())([5]()()))", x);
    }
    @Test
    public void testInOrder(){
        String x = tree.toInOrderString();
        assertEquals("(()[1]())[2]((()[3]())[4](()[5]()))", x);
    }
    @Test
    public void testPostOrder(){
        String x = tree.toPostOrderString();
        assertEquals("(()()[1])((()()[3])(()()[5])[4])[2]", x);
    }

    @Test
    public void testAddValueEmpty() {
        IntTree tree = new IntTree();
        tree.addValue(5);
        assertNotNull(tree.overallRoot);
        assertEquals(5, tree.overallRoot.data);
    }
    @Test
    public void testAddValueLeft() {
        IntTree tree = new IntTree();
        tree.addValue(5);
        tree.addValue(3);
        assertNotNull(tree.overallRoot.left);
        assertEquals(3, tree.overallRoot.left.data);
    }

    @Test
    public void testAddValueRight() {
        IntTree tree = new IntTree();
        tree.addValue(5);
        tree.addValue(7);
        assertNotNull(tree.overallRoot.right);
        assertEquals(7, tree.overallRoot.right.data);
    }


}
