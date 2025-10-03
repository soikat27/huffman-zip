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

    public static void decode(String fileName) throws IOException, ClassNotFoundException
	{
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
}