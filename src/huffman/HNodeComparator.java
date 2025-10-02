package huffman;

import java.util.Comparator;

public class HNodeComparator implements Comparator<HNode> {
    
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
