package huffman;

import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import utils.*;

/**
 * Represents a Huffman Tree for encoding and decoding characters
 * based on their frequency in a given dataset.
 * 
 * <p>
 * The tree is constructed from a map of character frequencies. Leaf nodes
 * store individual characters, while internal nodes represent merged
 * frequencies of their children. Encoding and decoding operations are
 * supported both iteratively and recursively.
 * </p>
 */
public class HuffmanTree {
    
	/** Root node of the Huffman Tree */
    private HNode root;

    /**
     * Constructs a Huffman Tree from character frequencies.
     *
     * @param frequencies map of characters to their corresponding frequencies
     */
    public HuffmanTree (TreeMap<Character, Integer> frequencies)
	{
		PriorityQueue<HNode> pQueue = new PriorityQueue<> (new HNodeComparator());
		
		for (Map.Entry<Character, Integer> entry : frequencies.entrySet())
		{
			char symbol   = entry.getKey();
            int frequency = entry.getValue();
            
            HNode hNode = new HNode(symbol, frequency);
            
            pQueue.add(hNode);
		}
		
		while (pQueue.size() > 1)
		{
			HNode left = pQueue.poll();
            HNode right = pQueue.poll();
            
            HNode parent = new HNode(left, right);
            
            pQueue.add(parent);
		}
		
		root = pQueue.poll();
	}

    // ----- LOGIC METHODS -----

	/**
     * Iteratively encodes a symbol into its binary string representation.
     *
     * @param symbol character to encode
     * @return binary string representing the symbol
     */
    public String encodeLoop (char symbol)
	{
		String encoding = "";
		HNode curHNode = root;

		while (!curHNode.isLeaf())
		{
            HNode curLeft = curHNode.getLeft();
            HNode curRight = curHNode.getRight();

			if (curLeft.contains(symbol)) 
			{
				encoding += "0";
				curHNode = curLeft;
			} 
			else
			{
				encoding += "1";
				curHNode = curRight;
			}
		}
		
		return encoding;
	}

	/**
     * Recursively encodes a symbol into its binary string representation.
     *
     * @param symbol character to encode
     * @return binary string representing the symbol
     */
    public String encode (char symbol)
	{
		return encode (symbol, root);
	}

    private String encode (char symbol, HNode curr)
	{
		if (curr.isLeaf())
		{
			return "";
		}
		
		else
		{
            HNode left = curr.getLeft();
            HNode right = curr.getRight();

			if (left.contains(symbol))
			{
				return "0" + encode (symbol, left);
			}
			
			else
			{
				return "1" + encode (symbol, right);
			}
		}
	}

	/**
     * Decodes a binary string into the corresponding character.
     *
     * @param code binary string to decode
     * @return decoded character, or '\0' if invalid
     */
    public char decode (String code)
	{
		HNode curHNode = root;

        for (int i = 0; i < code.length(); i++)
        {
            if (curHNode == null) // safety check
		    {
			    return '\0';
		    }

            else if (code.charAt(i) == '0')
            {
                curHNode = curHNode.getLeft();
            }

            else
			{
				curHNode = curHNode.getRight();
			}

        }

        return curHNode.getSymbol();
    }

	/**
     * Writes the Huffman code for a character to a BitOutputStream.
     *
     * @param symbol character to encode
     * @param stream BitOutputStream to write bits into
     * @throws IOException if an I/O error occurs
     */
    public void writeCode (char symbol, BitOutputStream stream) throws IOException
	{
		HNode curHNode = root;
		
		while (!curHNode.isLeaf())
		{
			HNode curLeft = curHNode.getLeft();
            HNode curRight = curHNode.getRight();

			if (curLeft.contains(symbol)) 
			{
	            stream.writeBit(0);
	            curHNode = curLeft;
	        }
	        else
	        {
	        	stream.writeBit(1);
	            curHNode = curRight;
	        }
		}
	}

	/**
     * Reads the next character from a BitInputStream based on Huffman coding.
     *
     * @param stream BitInputStream to read bits from
     * @return decoded character
     * @throws IOException if an I/O error occurs
     */
	public char readCode (BitInputStream stream) throws IOException
	{
		HNode curHNode = root;
		
		while (!curHNode.isLeaf())
		{
			if (stream.readBit() == 0)
			{
				curHNode = curHNode.getLeft();
			}
			
			else
			{
				curHNode = curHNode.getRight();
			}
		}
		
		return curHNode.getSymbol();
	}
}
