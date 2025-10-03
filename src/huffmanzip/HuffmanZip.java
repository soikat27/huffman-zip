package huffmanzip;

import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import huffman.HuffmanTree;
import utils.BitOutputStream;

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
}