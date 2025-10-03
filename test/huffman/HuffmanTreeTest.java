package huffman;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.BitInputStream;
import utils.BitOutputStream;

public class HuffmanTreeTest {

    private HuffmanTree tree;
    private TreeMap<Character, Integer> frequencies;

    @Before
    public void setUp() 
    {
        // Set up a sample frequency table
        frequencies = new TreeMap<>();
        frequencies.put('a', 5);
        frequencies.put('d', 9);
        frequencies.put('i', 12);
        frequencies.put('o', 13);
        frequencies.put('s', 16);

        // Construct the Huffman tree
        tree = new HuffmanTree(frequencies);
    }

    @After
    public void tearDown() 
    {
        tree = null;
        frequencies = null;
    }

    @Test
    public void testEncodeLoop() 
    {
        assertEquals("100", tree.encodeLoop('a'));
        assertEquals("101", tree.encodeLoop('d'));
        assertEquals("00", tree.encodeLoop('i'));
        assertEquals("01", tree.encodeLoop('o'));
        assertEquals("11", tree.encodeLoop('s'));
    }

    @Test
    public void testEncode() 
    {
        assertEquals("100", tree.encode('a'));
        assertEquals("101", tree.encode('d'));
        assertEquals("00", tree.encode('i'));
        assertEquals("01", tree.encode('o'));
        assertEquals("11", tree.encode('s'));
    }
    
    @Test
    public void testDecode() 
    {
        assertEquals('a', tree.decode("100"));
        assertEquals('d', tree.decode("101"));
        assertEquals('i', tree.decode("00"));
        assertEquals('o', tree.decode("01"));
        assertEquals('s', tree.decode("11"));

        // Test invalid codes
        assertEquals('\0', tree.decode("1111111111111"));
        assertEquals('\0', tree.decode("0"));
    }

    @Test
    public void testWriteCode() throws IOException
    {
        // for 1 symbol
        String fileName1 = "./test/output/testSingle.text";
        BitOutputStream bitOutputStream1 = new BitOutputStream(fileName1);

        tree.writeCode('a', bitOutputStream1);
        bitOutputStream1.close();

        // for 2(multiple) symbols
        String fileName2 = "./test/output/testTwo.text";
        BitOutputStream bitOutputStream2 = new BitOutputStream(fileName2);

        tree.writeCode('a', bitOutputStream2);
        tree.writeCode('d', bitOutputStream2);
        bitOutputStream2.close();
    }

    @Test
    public void testReadCode() throws IOException
    {
        // for 1 symbol
        String fileName1 = "./test/output/testSingle.text";
        BitInputStream bitInputStream1 = new BitInputStream(fileName1);

        assertEquals('a', tree.readCode(bitInputStream1));
        bitInputStream1.close();

        // for 2(multiple) symbol
        String fileName2 = "./test/output/testTwo.text";
        BitInputStream bitInputStream2 = new BitInputStream(fileName2);

        assertEquals('a', tree.readCode(bitInputStream2));
        assertEquals('d', tree.readCode(bitInputStream2));
        bitInputStream2.close();
    }
}
