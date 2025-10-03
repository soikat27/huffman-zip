package huffman;

/**
 * Represents a node in a Huffman Tree.
 * A node can be either a leaf (holding a single symbol) or an internal parent node
 * (holding combined symbols from its children).
 */
public class HNode {
    
	/** Child node. Null if this is a leaf node. */
    private HNode left, right;
	
	/** The symbols contained in this node. */
	private String symbols;
	/** The cumulative frequency of the symbols in this node. */
	private int    frequency;

    /**
     * Constructs a leaf node with a single character and its frequency.
     *
     * @param c the character stored in this leaf node
     * @param f the frequency of the character
     */
    public HNode (char c, int f)
	{
		left  = null;
		right = null;
		
		symbols = "" + c;
		frequency = f;
	}

    /**
     * Constructs an internal parent node by combining two child nodes.
     * The symbols and frequency are the sum of the children's symbols and frequencies.
     *
     * @param left  the left child node
     * @param right the right child node
     */
    public HNode (HNode left, HNode right)
	{
		this.left  = left;
		this.right = right;
		
		symbols   = left.symbols + right.symbols;
		frequency = left.frequency + right.frequency;
	}
    
    // ----- GETTER METHODS -----

	/**
     * Returns the left child of this node.
     * 
     * @return the left child node, or null if this is a leaf
     */
    public HNode getLeft() 
    {
        return left;
    }
    
	/**
     * Returns the right child of this node.
     * 
     * @return the right child node, or null if this is a leaf
     */
    public HNode getRight() 
    {
        return right;
    }
    
	/**
     * Returns the symbols stored in this node.
     * 
     * @return the symbols as a string
     */
    public String getSymbols() 
    {
        return symbols;
    }
    
	/**
     * Returns the cumulative frequency of symbols in this node.
     * 
     * @return the frequency
     */
    public int getFrequency() 
    {
        return frequency;
    }

    // ----- LOGIC METHODS -----

	/**
     * Checks if this node is a leaf node (has no children).
     *
     * @return true if leaf node, false otherwise
     */
    public boolean isLeaf ()
	{
		return left == null && right == null;
	}

	/**
     * Checks if this node contains a specific character.
     *
     * @param ch the character to check
     * @return true if the character exists in this node's symbols
     */
    public boolean contains (char ch)
	{
		return symbols.contains("" + ch);
	}

	/**
     * Returns the symbol of this node if it is a leaf.
     * 
     * @return the single character if leaf, or '\0' if not a leaf
     */
    public char getSymbol ()
	{
		if (!isLeaf())
		{
			return '\0';
		}
		
		return symbols.charAt(0);
	}

	/**
     * Returns a string representation of this node in the format:
     * symbols:frequency
     *
     * @return a string representing the node
     */
    @Override
    public String toString ()
	{
		return symbols + ":" + frequency;
	}
}
