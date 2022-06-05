package ask1;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class ToCsv {

	// reads given filename and transforms every 4 bytes to java integer
	// creates a csv file (text file that can be opened with text editor or MS Excel) with one line per integer
	// each line has the sequence of the integer in the file, the hex value and the decimal value
	public static void main(String[] args) throws IOException {
		/*if (args.length == 0) {
			System.err.println("Missing parameter inputfilename");
			System.err.println("e.g. java sk.domes201820191.ToCsv filename");
			return;			
		}*/
		String filename = "final";
		
		
		int bufferSizeInts = 2; // we will read up to 2*4=8 bytes each time
		int[] intArray = new int[bufferSizeInts];
		FileInputStream fis = null;
		DataInputStream dis = null;
		File outFile = null;
		PrintWriter outPrintWriter = null;
        try {
		
			fis = new FileInputStream(filename);
			dis = new DataInputStream(fis);
	
			int bytesRead = 1; // to initially to get into the while loop
			int globalCount = 0;
			outFile = new File(filename+".csv");
			outPrintWriter = new PrintWriter(outFile);
			outPrintWriter.println("AA;HEX;DECIMAL"); // write headers to the start of the text file

			while (bytesRead > 0) {
				byte[] byteBuffer = new byte[intArray.length * 4];
				bytesRead = dis.read(byteBuffer, 0, byteBuffer.length);
				Utilities.bytesToIntArray(byteBuffer, intArray);
				if (bytesRead < 0) {
					break;
				}
				for (int count=0; count<intArray.length;count++) { // write one line per read integer to the text file, counter,hexvalue,decvalue
					outPrintWriter.println(globalCount + ";\"" +Integer.toHexString(intArray[count]) + "\";" +intArray[count]);
					globalCount++;
				}
			}
		}
        catch (Exception ex) {
            System.err.println("Error while opening files or reading streams");
        	ex.printStackTrace();
        }
        finally {
            try {
                if (outPrintWriter != null) {
                	outPrintWriter.close();
                }
                if (dis != null) {
                	dis.close();
                }
                if (fis != null) {
                	fis.close();
                }
            }
            catch (Exception ex) {
                System.err.println("Error while closing streams");
            	ex.printStackTrace();
            }
        }
		
	}

}
