package huffman;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

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
}
