package ask1;

import java.io.IOException;
import java.util.Random;

public class Search {

	public static void main(String[] args) throws IOException 
	{
		int success[] = new int[20];
		int fail[] = new int[20];
		int f =0; // the numbers that will have to keep how many numbers i have already in the table
		int s=0; // the times that i have successfull search 
		int mo=0; // the sum of the succefull or fail searches
		
		while(s<20 || f<20)
		{
		int disks=0; // the first thing that i have to do is that
		boolean found = false;
		
		// now the linear search
		
		int buffer[] = new int[1000]; // i create my buffer
		Random num = new Random();
		// i have to generate a number each time between the margin that i have set
		int key = num.nextInt((100000-10)+1) + 10;
		System.out.println(key);
		int times=0; // the times that the buffer has to load
		
		try 
		{
			while(times <100 && found == false)
				{
					buffer = Utilities.readIntArrayFromDisk("final", (times*buffer.length), 1000); // read from the file
					disks++; // add disk reach
					for(int i=0; i<1000; i++) // begin the for loop so i will search the buffer at first and one by one buffers the whole file
					{
						if(key == buffer[i] && found == false)
						{
							// the first prints are just for confirmation
							System.out.println("Found");
							System.out.println(disks);
							found = true; // i make the boolean var true so that if the key has been found the first time, the search will stop.
							if(s<20) // this has been made so i take the first 20 successful searches and put all their numbers of acces into an array so i can calculate the sum in the end
							{
								success[s] = disks;
								s++;
							}
						}
					}
					times += 1;
				}
			if(found == false) // same for the fail access
			{
				System.out.println("Not Found");
				System.out.println(disks);
				if(f<20)
				{
					fail[f]=disks;
					f++;
				}
			} 
		}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		
		}
		// here i calculate the sum of the successful accesses and then the fail accesses. then all i have to do i mo/20;
		for(s=0; s<20; s++)
		{
			mo = mo + success[s];
		}
		System.out.println(mo);
		mo=0;
		for(f=0; f<20; f++)
			mo = mo + fail[f];
		System.out.println(mo);
	}
}
