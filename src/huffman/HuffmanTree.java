package huffman;

import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import utils.*;

public class HuffmanTree {
    
    private HNode root;

    // Huffman Tree construction
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
