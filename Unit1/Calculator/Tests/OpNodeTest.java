package Calculator.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Calculator.*;
import Calculator.OpNode.OpCode;

public class OpNodeTest {

    @Test
    public void ctorTest() {

        OpCode[] opCodes = {
                OpCode.ADDITION,
                OpCode.SUBTRACTION,
                OpCode.MULTIPLICATION,
                OpCode.DIVISION,
                OpCode.MODULO,
                OpCode.POWER
        };

        String[] rawContents = { "+", "-", "*", "/", "%", "^" };

        for (int i = 0; i < rawContents.length; i++) {

            OpNode opNode = new OpNode(rawContents[i]);
            assertEquals(opCodes[i], opNode.getOpCode());

        }
        try {
            OpNode op = new OpNode("ASDF");
        } catch (Exception e) {
            assertEquals("Unable to Parse OpCode: ASDF", e.getMessage());
        }
    }

    @Test
    public void testConstructor_validInput() {
        String input = "+";
        OpNode opNode = new OpNode(input);
        assertEquals(OpCode.ADDITION, opNode.getOpCode());
    }

    @Test
    public void testConstructor_invalidInput() {
        String input = "ASDF";
        assertThrows(RuntimeException.class, () -> new OpNode(input));
    }

    @Test
    public void testGetOpCode_addition() {
        OpNode opNode = new OpNode("+");
        assertEquals(OpCode.ADDITION, opNode.getOpCode());
    }

    @Test
    public void testGetOpCode_subtraction() {
        OpNode opNode = new OpNode("-");
        assertEquals(OpCode.SUBTRACTION, opNode.getOpCode());
    }

    @Test
    public void testGetOpCode_multiplication() {
        OpNode opNode = new OpNode("*");
        assertEquals(OpCode.MULTIPLICATION, opNode.getOpCode());
    }

    @Test
    public void testGetOpCode_division() {
        OpNode opNode = new OpNode("/");
        assertEquals(OpCode.DIVISION, opNode.getOpCode());
    }

    @Test
    public void testGetOpCode_modulo() {
        OpNode opNode = new OpNode("%");
        assertEquals(OpCode.MODULO, opNode.getOpCode());
    }

    @Test
    public void testGetOpCode_power() {
        OpNode opNode = new OpNode("^");
        assertEquals(OpCode.POWER, opNode.getOpCode());
    }

    @Test
    public void testToString_defaultDelimiter() {
        OpNode opNode = new OpNode("+");
        assertEquals("+", opNode.toString());
    }

    @Test
    public void testToString_customDelimiter() {
        OpNode opNode = new OpNode("+");
        assertEquals("+", opNode.toString(","));
    }

    @Test
    public void testEquals_differentValue() {
        OpNode opNode1 = new OpNode("+");
        OpNode opNode2 = new OpNode("-");
        assertFalse(opNode1.equals(opNode2));
    }

    @Test
    public void testHashCode() {
        OpNode opNode = new OpNode("+");
        int hashCode = opNode.hashCode();
        assertEquals(hashCode, opNode.hashCode());
    }
}
