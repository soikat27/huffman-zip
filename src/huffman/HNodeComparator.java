package huffman;

import java.util.Comparator;

/**
 * Comparator for HNode objects used in Huffman Tree construction.
 * 
 * <p>
 * Nodes are compared first by their frequency (ascending). If frequencies
 * are equal, nodes are compared lexicographically by their symbols.
 * This ensures consistent ordering in a priority queue for Huffman encoding.
 * </p>
 */
public class HNodeComparator implements Comparator<HNode> {
    
    /**
     * Compares two HNode objects.
     * 
     * @param a first HNode
     * @param b second HNode
     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second
     */
    @Override
    public int compare(HNode a, HNode b) 
	{
        int freqCompare = Integer.compare(a.getFrequency(), b.getFrequency());
        
        if (freqCompare != 0) 
        {
        	return freqCompare;
        }
        
        return a.getSymbols().compareTo(b.getSymbols());
    }
}
