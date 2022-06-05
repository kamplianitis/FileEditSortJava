package ask1;

// the libs the will be needed
import java.io.*;
import java.util.Random;



public class WriteFile {
	public static void main(String[] args) throws IOException
	{
		int disk = 0;
		int buffersize = 1000;
		for(int c=100; c>0; c--)
		{
			ByteArrayOutputStream array = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(array);
			Random num = new Random();
		
			for(int i=buffersize; i>0; i--) // read 1000 numbers at a time
			{
				int number = num.nextInt((100000-10)+1) + 10; // gives an integer between 10 and 100000
				out.writeInt(number); // writes the number into the Stream
			}
			out.close(); // closes the array i guess?
			
			byte[] buffer = array.toByteArray();		
			//now we write the array to the file 
		
			RandomAccessFile MyFile = new RandomAccessFile("try", "rw"); // we chose rw so if the file doesn't exist it creates it
			MyFile.seek(MyFile.length());
			MyFile.write(buffer);
			disk += 1;
			MyFile.close();
		}
		System.out.println("The times we used the disk are: "+disk);
		System.out.println("The file has been created...");
		
		
		// now we have to read
		
		//reads from the file and creates the files 
		int[] buffer = new int[1000]; // this is the buffer that we will need in order to read all the array
		int array[] = new int[10000];
		
		
		
		int offset=0; // the position that exists in the file
		int prosvaseis=0; // the times that we reached the disk
		
		for(int i =10; i>0; i--) // first for loop for the file declaration 
		{	
			int offsets = 0; // this is needed to reset every time i reach the margin of 10000 numbers so it can write the numbers properly
			int pos=0; // the position of the array
			for(int a=10; a>0; a--) // this is how we create each file 
			{
				buffer = Utilities.readIntArrayFromDisk("try", offset, buffer.length); // with this function i take the 1000 numbes
				prosvaseis = prosvaseis +1;
				for(int v =0; v<buffer.length; v++)
				{
					array[(pos+v)] = buffer[v];
				}
				pos += buffer.length;
				offset += buffer.length; // increase the number of offset so i can write in the right position of the file
			}
			MergeSort ob = new MergeSort(); // call the class object so i can call the function to sort them
			ob.sort(array, 0, array.length-1); // here i sort the array and now i have to write it into the file
			// now i have to tranfer again all the files to the buffer and write it to the file
			pos =0;
			for(int a=10; a>0; a--) // this is how we create each file 
			{
				for(int v =0; v<buffer.length; v++)
				{
					buffer[v] = array[pos+v];
				}
				
				pos += buffer.length;
				Utilities.writeIntArrayToDisk("file"+i, offsets, buffer); // i write the buffer to the file(i)(based of the number of the times that has been done)
				prosvaseis = prosvaseis+1;
				offsets += buffer.length;
			}
		}
		System.out.print("reached the disk " + prosvaseis+ " times.\n");
		System.out.println("10 files created..");
	
		
		
		
		
		
		// now we have to merge all the files
	
		prosvaseis=0;
		
		// we need a two dimensional array whose collums going to be the bufferx (x goes from 0 to 9 so the ten files) and the rows going to every element of each buffer
		
		int [][] mergeArray = new int [10][1000];
		
		
		// now he have to fill the table with all the elements that we are going to need
		// so we need 2 for loops. one that will count to 10(that will give us the file)
		
		for(int counter=0; counter<10; counter++)
		{
			// now i need a temp buffer so that i can transfer my data afterwards to the big table
			int[] temp = new int[1000];
			temp = Utilities.readIntArrayFromDisk("file"+(counter+1), 0, 1000); // now i have the elements in the temp buffer .. all i need to do is transfer each of them to the mergeArray
			prosvaseis++;
			for(int j=0; j<1000; j++)
			{
				mergeArray[counter][j] = temp[j];
			}
		}
		// now all the elements of the table are full so i have to create a new table that will have the ordered numbers and another one that will keep the position of each row of the table
		
		
		int[] positions = {0,0,0,0,0,0,0,0,0,0,0}; // one position for each row. all the positions have to start from the 0 and they will have to grow if the min is that number
		int[] offsets = {1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000}; // i have read the first 1000 numbers so all the offsets will be to 1k
		int min;
		int finaloffset=0;
		int bufferselection;
		
		
		// now i have to have a for loop that writes to the final file every time the finalbuffer is full
			for(int c=0; c<100; c++)
			{
				int[] finalbuffer = new int[1000]; // the amount of elements that i can write with one write at the final file
				//inside i have to make a for loop that will define the element of the finalbuffer
			
				for(int i=0; i<1000; i++) // i have to fill the min here
				{
				
					min = 1000000000; // set up the min first
					bufferselection =0; // this will keep the position that will have the min number
				
					for(int counter=0; counter<10; counter++) // this helps me find the minimum among the 10 numbers
					{
						if(min > mergeArray[counter][positions[counter]] )
						{
							min = mergeArray[counter][positions[counter]];
							bufferselection=counter; // gives the num of the buffer that is the min
						}
					}
					finalbuffer[i] = min; // bring the min into the position of the buffer
					positions[bufferselection]++; 
					
					if(positions[bufferselection] == 1000)
					{
						positions[bufferselection] =0; // i have to reset the position of the buffer
					
						// now i have to read again
					
						if(offsets[bufferselection] < 10000)
						{
							int temp[] = Utilities.readIntArrayFromDisk("file"+(bufferselection+1), offsets[bufferselection], 1000);
							prosvaseis++;
							for(int a1=0; a1<1000; a1++)
							{
								mergeArray[bufferselection][a1] = temp[a1]; 
							}
							offsets[bufferselection]= offsets[bufferselection]+1000;
						}
						else
						{
							for(int a1=0; a1<1000; a1++)
							{
								mergeArray[bufferselection][a1] = 1000000000; 
							}
							Utilities.deleteFile("file"+(bufferselection+1));
						}
					}
				}
				Utilities.writeIntArrayToDisk("final", finaloffset, finalbuffer);// write the final file
				finaloffset = finaloffset + finalbuffer.length;
				prosvaseis++;
			}
		
		System.out.println("You've made it....  "+prosvaseis+" in the disk");
	
	}	
}	