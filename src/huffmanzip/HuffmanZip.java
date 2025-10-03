package huffmanzip;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

import huffman.HuffmanTree;
import utils.BitInputStream;
import utils.BitOutputStream;

/**
 * HuffmanZip is a utility class for compressing and decompressing files
 * using Huffman Encoding. It provides methods to
 * encode files to a compressed binary format, and decode them back to their
 * original content.
 * 
 * <pre>
 * Usage:
 * java HuffmanZip -encode &lt;fileName&gt;
 * java HuffmanZip -decode &lt;fileName&gt;
 * </pre>
 */
public class HuffmanZip {

    private static TreeMap<Character, Integer> buildFrequencies (String fileName) throws IOException
	{
		TreeMap<Character, Integer> frequencies = new TreeMap<> ();

		FileReader reader = new FileReader (fileName);

		int curChar = reader.read();

		while (curChar != -1)
		{
			char character = (char) curChar;

            frequencies.put(character, frequencies.getOrDefault(character, 0) + 1);

			curChar = reader.read();
		}

		reader.close();
		return frequencies;
	}

	/**
     * Encodes the specified file using Huffman encoding and writes the compressed
     * binary output to a file with ".hz" extension.
     * 
     * @param fileName The path of the file to encode
     * @throws IOException If there is an error reading the file or writing output
     */
    public static void encode(String fileName) throws IOException
	{
		TreeMap<Character, Integer> frequencies = buildFrequencies(fileName);
		HuffmanTree hTree = new HuffmanTree (frequencies);

		String binaryFile = fileName + ".hz";

		BitOutputStream bitOutputStream = new BitOutputStream(binaryFile);
		bitOutputStream.writeObject(frequencies);

		FileReader reader = new FileReader (fileName);

		int curChar = reader.read();

		while (curChar != -1)
		{
			char character = (char) curChar;

			hTree.writeCode(character, bitOutputStream);

			curChar = reader.read();
		}

		reader.close();
		bitOutputStream.close();
	}

	/**
     * Decodes a previously Huffman-encoded file (with ".hz" extension) and writes
     * the decoded output to a file with the original name. If a file with the
     * decoded name already exists, prompts the user to overwrite or abort.
     * 
     * @param fileName The path of the file to decode
     * @throws IOException            If there is an error reading or writing files
     * @throws ClassNotFoundException If the frequency map object cannot be read
     */
    public static void decode(String fileName) throws IOException, ClassNotFoundException
	{
		if (!fileName.endsWith(".hz"))
		{
			System.err.println("Error: not a .hz file. The program aborts!");
			return;
		}

		String decodedFile = fileName.replace(".hz", "");
        File outputFile = new File (decodedFile);

        if (outputFile.exists())
        {
            System.out.println ("A file with the name '" + decodedFile + "' already exists. Type: (y / n(or anything else))");
            System.out.println ("'y': Delete existing file and rewrite");
            System.out.println ("'n / (anything else)': abort with no action");

            Scanner sc = new Scanner (System.in);
            String response = sc.next();
        
            if (response.equalsIgnoreCase("y"))
            {
                outputFile.delete();
            }

            else
            {
                sc.close();
                return;
            }

            sc.close();
        }

		BitInputStream bitInputStream = new BitInputStream(fileName);
        @SuppressWarnings("unchecked")
		TreeMap<Character, Integer> frequencies = (TreeMap<Character, Integer>) bitInputStream.readObject();

		HuffmanTree hTree = new HuffmanTree(frequencies);

		FileWriter writer = new FileWriter(decodedFile);

		while (bitInputStream.hasNext())
		{
			char curChar = hTree.readCode(bitInputStream);
			writer.write(curChar);
		}

		writer.close();
		bitInputStream.close();
	}

    private static void printUsage() 
    {
        System.err.println("Invalid argument(s)! Please enter valid argument(s)");
        System.err.println("Usage:");
        System.err.println("      java HuffmanZip -encode <fileName>");
        System.err.println("      java HuffmanZip -decode <fileName>");
    }

	/**
     * Main entry point for HuffmanZip.
     * 
     * @param args Command-line arguments: operation (-encode or -decode) and file name
     * @throws IOException            If an I/O error occurs
     * @throws ClassNotFoundException If the frequency map cannot be read from file
     */
    public static void main (String[] args) throws IOException, ClassNotFoundException
	{
		if (args.length != 2)
		{
			printUsage();
			return;
		}

		String operation = args[0];
		String fileName = args[1];

		switch (operation.toLowerCase()) 
        {
            case "-encode": 
                encode(fileName);
                break;
            case "-decode": 
                decode(fileName);
                break;
            default: 
                printUsage();
                break;
        }
	}
}