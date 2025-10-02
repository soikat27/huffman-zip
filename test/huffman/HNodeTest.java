package huffman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HNodeTest {
    
    @Test
    public void testIsLeaf() 
    {
        HNode leaf = new HNode('a', 5);
        HNode parent = new HNode(leaf, new HNode('b', 3));

        assertTrue(leaf.isLeaf());
        assertFalse(parent.isLeaf());
    }

    @Test
    public void testContains() 
    {
        HNode leaf = new HNode('a', 5);
        HNode parent = new HNode(leaf, new HNode('b', 3));

        // Leaf contains only its symbol
        assertTrue(leaf.contains('a'));
        assertFalse(leaf.contains('b'));

        // Parent contains symbols from both children
        assertTrue(parent.contains('a'));
        assertTrue(parent.contains('b'));
        assertFalse(parent.contains('c'));
    }

    @Test
    public void testGetSymbol() 
    {
        HNode leaf = new HNode('a', 5);
        HNode parent = new HNode(leaf, new HNode('b', 3));

        // Leaf returns its symbol
        assertEquals('a', leaf.getSymbol());

        // Parent returns '\0' for non-leaf
        assertEquals('\0', parent.getSymbol());
    }

    @Test
    public void testGetters() {
        HNode leaf = new HNode('a', 5);
        HNode right = new HNode('b', 3);
        HNode parent = new HNode(leaf, right);

        // basic getters
        assertEquals(leaf, parent.getLeft());
        assertEquals(right, parent.getRight());
        assertEquals("ab", parent.getSymbols());
        assertEquals(8, parent.getFrequency());
    }

    @Test
    public void testToString() 
    {
        HNode leaf = new HNode('a', 5);
        HNode parent = new HNode(leaf, new HNode('b', 3));

        assertEquals("a:5", leaf.toString());
        assertEquals("ab:8", parent.toString());
    }
}
