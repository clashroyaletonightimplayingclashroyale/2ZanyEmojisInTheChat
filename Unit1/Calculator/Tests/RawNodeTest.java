package Calculator.Tests;

import Calculator.RawNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RawNodeTest {

    private RawNode a;
    private RawNode b;
    private RawNode c;
    private RawNode d;

    @BeforeEach
    public void setup() {
        a = new RawNode("a");
        b = new RawNode("b");
        c = new RawNode("c");
        d = new RawNode("d");

    }

    @Test
    public void cTorTest() {
        RawNode n = new RawNode("hello");
        assertEquals("hello", n.getRawContent());
        assertEquals(null, n.getNext());
        assertEquals(null, n.getPrev());
    }

    @Test
    public void addNextTest() {
        a.addNext(b);
        a.addNext(c);
        assertEquals("c", a.getNext().getRawContent());
        assertEquals(null, a.getPrev());
        assertEquals("a", c.getPrev().getRawContent());
       
    }

    @Test
    public void insertNextTest() {
        a.insertNext(b);
        a.insertNext(c);
        assertEquals("c", a.getNext().getRawContent());
        assertEquals(null, a.getPrev());
        assertEquals("a", c.getPrev().getRawContent());
        assertEquals("b", c.getNext().getRawContent());
        assertEquals("c", b.getPrev().getRawContent());
        assertEquals(null, b.getNext());

    }

    @Test
    public void addTailAndToStringTest() {
        a.addTail(b);
        a.addTail(c);
        a.addTail(d);
        assertEquals("a b c d", a.toString(" "));
        assertEquals("a\nb\nc\nd", a.toString("\n"));
    }

}
