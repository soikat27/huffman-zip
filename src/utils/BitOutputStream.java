// Updated: 03-29-2012

import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 *
 * The BitOutputStream allows writing individual bits to a general
 * Java OutputStream. This class is able to write a single bit to 
 * a stream (even though a byte has to be filled until the data is
 * flushed to the underlying output stream). It is also able to write
 * Serializable objects.
 * <p>
 * The code is based on the following reference:
 * <p>
 *     http://www.developer.nokia.com/Community/Wiki/Bit_Input/Output_Stream_utility_classes_for_efficient_data_transfer
 * <p>    
 * The original author is Andreas Jakl. The code has been modified
 * to include support for partial bytes at the end of the stream and
 * to allow writing of serializable objects.
 */
public class BitOutputStream {
	/**
	 * The <code>ObjectOutputStream</code> this stream is writing to.
	 */
	private ObjectOutputStream stream;

	/**
	 * The temporary buffer containing the individual bits
	 * until a byte has been completed and can be committed
	 * to the output stream.
	 */
	private int buffer = 0;

	/**
	 * Counts how many bits are left to fill the buffer.
	 */
	private int bitCount = 8;

	/**
	 * Indicates whether the method <code>writeBit</code> has been called.
	 * The method <code>writeObject</code> cannot be called after a call to <code>writeBit</code>.
	 */
	private boolean writeBitCalled = false;

	/**
	 * Creates a <code>BitOutputStream</code> that writes bits to the specified <code>OutputStream</code>.
	 * @param out the output stream to write to
	 * @throws IOException - if an I/O error occurs while writing stream header 
     * @throws SecurityException - if untrusted subclass illegally overrides security-sensitive methods 
     * @throws NullPointerException - if <code>out</code> is <code>null</code>
	 */
	public BitOutputStream(OutputStream out) throws IOException
	{
		stream = new ObjectOutputStream(out);
	}

	/**
	 * Creates a <code>BitOutputStream</code> stream that writes bits to the file with the given name.
	 * @param name the name of the file to write the bits to
	 * @throws FileNotFoundException if the file does not exist, is a directory
	 *         rather than a regular file, or for some other reason cannot be
	 *         opened for reading
	 */
	public BitOutputStream(String name) throws IOException
	{
		stream = new ObjectOutputStream(new FileOutputStream(name));
	}

	/**
	 * Writes a single bit to the stream. It will only be flushed
	 * to the underlying <code>OutputStream</code> when a byte has been completed (or when this stream is closed).
	 * @param bit the next bit to write (either 1 or 0)
	 * @throws IOException if an I/O error occurs
	 * @throws IllegalArgumentException if the bit to write is not on of the integers 1 or 0
	 */
	synchronized public void writeBit(int bit) throws IOException
	{
		if (stream == null) {
			throw new IOException("the stream is not open for writing");
		}

		if (bit != 0 && bit != 1) {
			throw new IOException(bit + " is not a bit; did you use chars '0', '1'? use ints 0, 1");
		}

		writeBitCalled = true;

		buffer = (buffer << 1) | bit;
		bitCount--;

		if (bitCount == 0) {
			flush();
		}
	}

	/**
	 * Writes an object to this stream.
	 * @param obj the object to be written to this stream
	 * @throws IOException if an I/O error occurs
	 */
	synchronized public void writeObject(Object obj) throws IOException
	{
		if (stream == null) {
			throw new IOException("the stream is not open for writing");
		}
		else if (writeBitCalled) {
			throw new IOException("cannot call writeObject after a call to writeBit");
		}

		stream.writeObject(obj);
	}

	/**
	 * Writes the current cache to this stream and resets the buffer.
	 * @throws IOException if an I/O error occurs
	 */
	private void flush() throws IOException
	{
		if (bitCount == 0)
		{
			stream.write((byte) buffer);
			bitCount = 8;
			buffer = 0;
		}
	}

	/**
	 * Flushes the data and closes the underlying output stream.
	 * @throws IOException if an I/O error occurs
	 */
	public void close() throws IOException
	{
		// store the last set of bits along shifted
		// to the most-significant bits
		if (writeBitCalled) {
			if (bitCount != 8) {
				stream.write((byte) buffer);
			}
			else {
				bitCount = 0;
			}

			// write the number of last set of saved bits at the end
			stream.write((byte) (8 - bitCount));
		}

		stream.close();
		stream = null;
	}
}
