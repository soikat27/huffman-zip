package huffman;

public class HNode {
    
    private HNode left, right;
	
	private String symbols;
	private int    frequency;

    // Leaf constructor
    public HNode (char c, int f)
	{
		left  = null;
		right = null;
		
		symbols = "" + c;
		frequency = f;
	}

    // Parent constructor
    public HNode (HNode left, HNode right)
	{
		this.left  = left;
		this.right = right;
		
		symbols   = left.symbols + right.symbols;
		frequency = left.frequency + right.frequency;
	}
    
    // ----- GETTER METHODS -----
    public HNode getLeft() 
    {
        return left;
    }
    
    public HNode getRight() 
    {
        return right;
    }
    
    public String getSymbols() 
    {
        return symbols;
    }
    
    public int getFrequency() 
    {
        return frequency;
    }
}
