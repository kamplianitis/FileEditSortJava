package ask1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Utilities {
	/**
	 * return a byte array of size 4, that correspond to the given int
	 * @param i
	 * @return
	 */
	public static byte[] intToBytes( final int i ) {
	    ByteBuffer bb = ByteBuffer.allocate(2); 
	    bb.putInt(i); 
	    return bb.array();
	}
	
	/**
	 * returns the int value that corresponds to the first 4 bytes of buffer
	 * @param buffer
	 * @return
	 */
	public static int bytesToInt( final byte[] buffer ) {
		return bytesToInt(buffer, 0);
	}
	
	/**
	 * returns the integer value that corresponds to the 4 bytes of buffer starting at byte offset
	 * https://docs.oracle.com/javase/9/docs/api/java/nio/ByteBuffer.html
	 * @param buffer
	 * @return
	 */
	public static int  bytesToInt( final byte[] buffer, final int offset ) {
	    ByteBuffer bb = ByteBuffer.wrap(buffer); 
	    return bb.getInt(offset); 
	}
	/**
	 * fills array of integers dstArray with integers that correspond to the bytes in byteBuffer
	 * @param byteBuffer array of bytes as input
	 * @param dstArray array of integers that will be filled. Must be allocated before calling this method
	 * https://docs.oracle.com/javase/9/docs/api/java/nio/ByteBuffer.html
	 */
	public static void bytesToIntArray(final byte[] byteBuffer, int[] dstArray) {
        IntBuffer intBuf = ByteBuffer.wrap(byteBuffer).asIntBuffer();
        // fills dstArray with ints. Uses all the bytes in byteBuffer. 
        // There is a version of this method that uses onlya subset of the bytes of byteBuffer
        intBuf.get(dstArray); 
	}
	
	/**
	 * returns an array of bytes that correspond to the given array of integers
	 * @param intArray array of ints as input
	 * @return array of bytes that correspond to the given input
	 * https://docs.oracle.com/javase/9/docs/api/java/nio/ByteBuffer.html
	 */
	public static byte[] intArrayToBytes(final int[] intArray) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(intArray.length * 4);        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(intArray);
        byte[] byteArray = byteBuffer.array();
        return byteArray;
	}	

	
	/**
	 * Reads from given file numberOfIntsToRead integers, starting at the intOffset'th integer (intOffset*4) byte.
	 * Returns array of read integers  
	 * @param filename
	 * @param intOffset
	 * @param numberOfIntsToRead
	 * @return
	 * @throws IOException
	 * 
	 * Example: will read the 9th, 10th, 11th and 12th integer from filename (start at offset 8)
	 * int intArray[] = Utils.readIntArrayFromDisk(filename, 8, 4);
	 * for (int count=0; count<4;count++) {
	 * 	System.err.println(intArray[count]);
	 * }
	 */
	public static int[] readIntArrayFromDisk(String filename, long intOffset, int numberOfIntsToRead) throws IOException {
		RandomAccessFile randomAccessFile = null;
		byte[] byteBuffer = new byte[numberOfIntsToRead*4];
		int bytesRead;
		try {
			randomAccessFile = new RandomAccessFile(filename, "r");
			randomAccessFile.seek(intOffset*4);
			bytesRead = randomAccessFile.read(byteBuffer, 0, byteBuffer.length); // 0 is the offset in array byteBuffer
			randomAccessFile.close();
			randomAccessFile = null;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		int[] intArray = new int[numberOfIntsToRead];

		Utilities.bytesToIntArray(byteBuffer, intArray);
		// intArray contains now the read bytes in byteBuffer as integers
		// does not handle cases where bytesRead is less than the number of intended reads 

		return intArray;
	}


	/**
	 * Write to file filename the integers contained in intArray, starting at integer position intOffset = byte position intOffset*4 
	 * @param filename
	 * @param intOffset
	 * @param intArray
	 * @throws IOException
	 * 
	 * Example: will write the 5th and 6th integer to filename (starts at offset 4)
	 * int[] intArray = new int[2];
	 * intArray[0] =4;
	 * intArray[1] =5;
	 * Utils.writeIntArrayToDisk("test", 4, intArray);
	 */
	public static void writeIntArrayToDisk(String filename, long intOffset, int[] intArray) throws IOException {
		RandomAccessFile randomAccessFile = null;
		byte[] byteBuffer = Utilities.intArrayToBytes(intArray);
		
		try {
			randomAccessFile = new RandomAccessFile(filename, "rw");
			randomAccessFile.seek(intOffset*4);
			randomAccessFile.write(byteBuffer);
			randomAccessFile.close();
			randomAccessFile = null;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	public static void deleteFile(String file) throws FileNotFoundException
	{
		File RAFile = new File(file);
		
		
		if(RAFile.delete())
		{
			System.out.println("deleted");
		}
	}
}
