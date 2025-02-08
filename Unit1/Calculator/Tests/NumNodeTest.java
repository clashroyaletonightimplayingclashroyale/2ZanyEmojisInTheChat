package Calculator.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import Calculator.NumNode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumNodeTest {

    @Test
    public void testConstructor_validInput() {
        String input = "123.45";
        NumNode node = new NumNode(input);
        assertEquals(123.45, node.getNumValue(), 0.01);
    }

    @Test
    public void testConstructor_invalidInput() {
        String input = "abc";
        assertThrows(NumberFormatException.class, () -> new NumNode(input));
    }

    @Test
    public void testGetNumValue() {
        NumNode node = new NumNode("123.45");
        assertEquals(123.45, node.getNumValue(), 0.01);
    }

    @Test
    public void testToString() {
        NumNode node = new NumNode("123.45");
        assertEquals("123.45", node.toString());
    }

    @Test
    public void testEquals_differentValue() {
        NumNode node1 = new NumNode("123.45");
        NumNode node2 = new NumNode("678.90");
        assertFalse(node1.equals(node2));
    }

    @Test
    public void testHashCode() {
        NumNode node = new NumNode("123.45");
        int hashCode = node.hashCode();
        assertEquals(hashCode, node.hashCode());
    }
}