// Updated: 03-29-2012

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * The BitInputStream allows reading individual bits from a general 
 * Java InputStream. This class is able to read a single bit from a
 * stream It is also able to read Serializable objects. 
 * <p>
 * The code is based on the following reference:
 * <p>
 * http://www.developer.nokia.com/Community/Wiki/Bit_Input/Output_Stream_utility_classes_for_efficient_data_transfer
 * <p>    
 * The original author is Andreas Jakl. The code has been modified
 * to include support for partial bytes at the end of the stream and
 * to allow reading of serializable objects.
 * 
 */
public class BitInputStream {
	/**
	 * The <code>ObjectInputStream</code> this stream is reading from.
	 */
	private ObjectInputStream stream;

	/**
	 * The buffer containing the current byte of this stream.
	 */
	private int buffer;

	/**
	 * The very last byte in this stream, not part of the original input.
	 * Indicates the number of significant bits saved at the very end.
	 */
	private int lastBits;

	/**
	 * The nextByte, if any, in the stream.
	 */
	private int nextByte;

    	/**
	 * Keeps track if there is a next byte in the stream.
	 */
	private boolean hasNextByte = false;

	/**
	 * Indicates whether the method <code>readBit</code> has been called.
	 * The method <code>readObject</code> cannot be called after a call to <code>readBit</code>.
	 */
	private boolean readBitCalled = false;

	/**
	 * The index of the next bit in the current byte that will be returned by <code>readBit</code>.
	 * If it's 0, the next bit will be read from the next byte of the <code>ObjectInputStream</code>.
	 */
	private int nextBit = 8;

	/**
	 * Creates a <code>BitInputStream</code> that reads bits from the specified <code>InputStream</code>.
	 * @param in the input stream to read from
	 * @throws IOException - if an I/O error occurs while writing stream header 
     * @throws SecurityException - if untrusted subclass illegally overrides security-sensitive methods 
     * @throws NullPointerException - if <code>in</code> is <code>null</code>
	 */
	public BitInputStream(InputStream in) throws IOException
	{
		stream = new ObjectInputStream(in);
	}

	/**
	 * Creates a <code>BitInputStream</code> that reads bits from the file with the given name.
	 * @param name the name of the file to read the bits from
	 * @throws IOException if an I/O error occurs
	 * @throws FileNotFoundException if the file does not exist, is a directory
	 *         rather than a regular file, or for some other reason cannot be
	 *         opened for reading.
	 */
	public BitInputStream(String name) throws IOException
	{
		stream = new ObjectInputStream(new FileInputStream(name));
	}

	/**
	 * Returns <code>true</code> if there are more bits in this stream. After a call to <code>hasNext</code>
	 * the method <code>readObject</code> should not be called.
	 * @return <code>true</code>, if there are more bits in this stream
	 */
	synchronized public boolean hasNext()
	{
		if (stream == null) {
			return false;
		}	
		else if (!readBitCalled) {
			try {
				buffer = stream.readUnsignedByte();
			}
			catch (IOException e) {
				return false;
			}
			try {
				lastBits = stream.readUnsignedByte();
			}
			catch (IOException e) {
				return false;
			}
			try {
				nextByte = stream.readUnsignedByte();
				nextBit = 8;
				hasNextByte = true;
			}
			catch (IOException e) {
				nextBit = lastBits;
				lastBits = 0;
				hasNextByte = false;
			}
			readBitCalled = true;
			return true;
		}
		else if (nextBit == 0) {
			if (!hasNextByte) {
				return false;
			}
			else {
				buffer = lastBits;
				lastBits = nextByte;
				try {
				        nextByte = stream.readUnsignedByte();
					nextBit = 8;
					hasNextByte = true;
				}
				catch (IOException e) {
					nextBit = lastBits;
					hasNextByte = false;
				}
				return true;
			}
		}
		else {
			return true;
		}
	}

	/**
	 * Reads the next bit from this stream.
	 * @return 0 if the bit is 0, 1 if the bit is 1
	 * @throws IOException if an I/O error occurs, or if attempting to read past the end of file
	 */
	synchronized public int readBit() throws IOException
	{
		if (!hasNext()) {
			throw new IOException("no bits left in the stream");
		}
		else {
			nextBit--;
			int bit = buffer & (1 << nextBit);

			bit = (bit == 0) ? 0 : 1;

			return bit;
		}
	}

	/**
	 * Reads the next object from this stream.
	 * @return the object read from this stream
	 * @throws IOException if an I/O error occurs
	 * @throws ClassNotFoundException @see ObjectInputStream#readObject
	 */
	synchronized public Object readObject() throws IOException, ClassNotFoundException
	{
		if (stream == null) {
			throw new IOException("this stream is not open for reading");
		}
		else if (readBitCalled) {
			throw new IOException("cannot call readObject after a call to readBit");
		}

		return stream.readObject();
	}

	/**
	 * Closes the underlying input stream.
	 * @throws IOException if an I/O error occurs
	 */
	public void close() throws IOException
	{
		stream.close();
		stream = null;
	}
}