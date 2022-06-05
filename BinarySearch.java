package ask1;

import java.io.IOException;
import java.util.Random;

public class BinarySearch 
{	
	public static void main(String[] args) throws IOException 
	{
		int success[] = new int[20];
		int fail[] = new int[20];
		int f =0; // the numbers that will have to keep how many numbers i have already in the table
		int s=0;
		int mo=0;
		
		
		while(s<20 || f<20)
		{
			

			// create the random number
			Random numb = new Random();
			int key = numb.nextInt((100000-10)+1) + 10; // i create the random key
			System.out.println(key);
			
			int reader[] = new int [1000]; // here i have the buffer
			int start=0; // the first "buffer"
			int end=100; // the last "buffer"
			int disk=0; // the amounts of the disk reach
			boolean found = false; // this var is used as a tool to stop the loop process
			
		while(start<=end && found == false)
		{
			int middle = (start+end)/2; // there i find the mid array that i have to reach 
			reader = Utilities.readIntArrayFromDisk("final", middle*reader.length, reader.length); // i reach all the numbers of the ordered file from the offset that is given by the mid number * 1000 which is the amount of the array ints available
			disk++; // increase the disk reaches
			// now i take the possible cases
			
			if(reader[reader.length-1]<key) // check if the last int of the byte is smaller than the key so that i dont read the 1k numbs and i automatically go to the next one
				start = middle +1; // my mid position of the element plus one is going to be my new start now if the number is smaller
			else if(reader[0]>key) // same though but this time for the case the fist int of the buffer is greater
				end = middle -1; // same thought for the case that the number is higher
			else // if the prev cases are not valid the only options are either the numb is inside the buffer or non-existing
			{
				for(int i =0; i<1000; i++) // now i check the page so that i make sure that the numb is in the buffer or that is non-existing
				{
					if (reader[i] == key && found == false) // i have the found == false cause i want the search to stop in the first number and dont care if there is a second one
					{
						System.out.println("Found");
						System.out.println(disk);
						found = true; // i make the found true
						if(s<20)
						{
							success[s] = disk;
							s++;
						}
					}
				}
				if(found == false)
				{
					System.out.println("Not Found");
					System.out.println(disk);
					found = true; // i make found true so i will stop the while loop earlier than waiting for the low to go higher than the high 
					if(f<20)
					{
						fail[f] = disk;
						f++;
					}
				}
			}
		}
	}
		// again i calculate the sum of the accesses . and again all i have to do is mo/2
		for(s=0; s<20; s++)
		{
			System.out.print(success[s]+" ");
			mo = mo+ success[s];
			
		}
		System.out.println(mo);
		System.out.println("\n");
		mo=0;
		for(f=0; f<20; f++)
		{
			System.out.print(fail[f]+" ");
			mo = mo+ fail[f];
		}

		System.out.println(mo);
	}
}
