/*
Name: Jenny Spicer
CSCI 4401/5401
Fall 2023
Extra Credit Assignment

Due: Tuesday, 11/28 @ 6:00pm (before we meet for the last lecture)

- This extra credit assignment contains a total of 25 points that can be applied to your final exam grade. Since this is extra credit, no partial credit for problems. 
- The problems you will solve all have ToDo: notes. 
- You can create new variables, but you cannot hardcode values. Rather than hardcoding values, use the global variables.
- The global variables and the arguments passed to the methods are example test values. You code should not be built to work for these specific values. I.e., it should still work if other test values were used. 
- Do not modify the current print statements. If you add print statements for testing & debugging, please remove them before submitting.
- Submit: this modified file containing your solutions.
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class HW5{ //don't rename
	/*Part I: Main Memory Variables */
	public static int[] memoryHoles = {10, 4, 22, 18, 7, 9, 12, 15}; //List of holes in memory for contiguous allocation
	public static int pageSize = (int) Math.pow(2, 10) * 4; //Page Size is 4KB
	public static int[][] pageTable =  { {0, 3}, {1, 4}, {2, 6}, {3, 2} }; //Page Table is (Page#, Frame#)
	public static int ptrSize = 2; //Pointer size is 2 bytes

	/*Part II: Virtual Memory Variables */
	public static int[] refString = {1, 2, 3, 4 ,1 ,2, 5, 1, 2, 3, 4, 5};

	/*Part III: File System Variables */
	public static int directPtrs = 20; //20 direct pointers


	/*Part IV: Mass Storage Variables */
	public static int[] requests = {86, 1470, 913, 1774, 948, 1509, 1022, 1750, 130};
	public static int max = 4999;
	public static int min = 0;
	


	public static void main(String args[]){
		part1test();
		part2test();
		part3test();
		part4test();
	}

	/********************************************************************************************
	Part I: Main Memory
		- contiguousAllocation()
		- paging()
		- addressMapping()
	********************************************************************************************/

	public static void part1test(){
		/*	This is for testing your code. 
			DO NOT modify this method
		*/
		
		printSectionHeader("Part I: Main Memory");

		//Test contiguousAllocation()
		System.out.println("contiguousAllocation() [2 points]");
		System.out.println(String.format("\t%1$12s%2$12s%3$12s%4$12s", "Request(MB)", "FF Slot(MB)", "BF Slot(MB)", "WF Slot(MB)"));
		contiguousAllocation(12);
		contiguousAllocation(10);
		contiguousAllocation(9);

		//Test paging()
		System.out.println("\npaging() [2 points]");
		System.out.println(String.format("\t%1$10s%2$8s%3$8s", "Address", "Page#", "Offset"));
		paging(10275);
		paging(30600);
		paging(36543);

		//Test addressMapping()
		System.out.println("\naddressMapping() [2 points]");
		System.out.println(String.format("\t%1$10s%2$8s%3$8s%4$17s", "Address", "Frame#", "Offset", "PhysicalAddress"));
		addressMapping(10275);
		addressMapping(8600);
		addressMapping(6500);		
	}

	public static void contiguousAllocation(int requestSize){
		/*	Consider a swapping system in which memory consists of the hole sizes @memoryHoles in memory. 
			Write code that determines what a segment request @requestSize will take for first-fit @firstFit, 
			best-fit @bestFit, and worst-fit @worstFit.
		*/
		int firstFit = 0;
		int bestFit = 0;
		int worstFit = 0;
		
		//ToDo: add your code to calculate @firstFit
		for(int i=0; i<memoryHoles.length; i++){
			if(memoryHoles[i] >= requestSize){
				firstFit = i;
				break;
			}
		}

		//ToDo: add your code to calculate @bestFit
		int min = 999999;
		for(int i=0; i<memoryHoles.length; i++){
			if( (memoryHoles[i]<min)&&(memoryHoles[i] >= requestSize) ){
				min = memoryHoles[i];
				bestFit = i;
			}
		}

		//ToDo: add your code to calculate @worsFit
		int max = 0;
		for(int i=0; i<memoryHoles.length; i++){
			//same as best fit, but with max instead
			if( (memoryHoles[i]>max)&&(memoryHoles[i] >= requestSize) ){
				max = memoryHoles[i];
				worstFit = i;
			}
		}
		
		System.out.println(String.format("\t%1$12s%2$12s%3$12s%4$12s", requestSize, firstFit, bestFit, worstFit)); //Do not modify		
	}

	public static void paging(int addrRef) {
		/*	Assuming a 4 KB (@pageSize), calculate the page number @pageNbr and offset @offset for a given 
			address reference @addrRef.
		*/
		int pageNbr = 0;
		int offset = 0;

		//ToDo: add your code to calculate the page number @pageNbr
		pageNbr = addrRef/pageSize;

		//ToDo: add your code to calculate the offset @offset
		offset = addrRef%pageSize;

		System.out.println(String.format("\t%1$10s%2$8s%3$8s", addrRef, pageNbr, offset));		
	}

	public static void addressMapping(int addr){
		/*	Assuming a 4 KB (@pageSize) and a frame size of 4 KB (@pageSize), calculate the frame number @frameNbr 
			and the physical address @phyAddr given a logical address @addr and the page table @pageTable
			for
		*/
		int pageNbr = 0;
		int offset = 0;
		int frameNbr = 0;
		int phyAddr = 0;

		//ToDo: add your code to calculate the frameNbr @frameNbr
		pageNbr = addr/pageSize;
		frameNbr = pageTable[pageNbr][1];

		//ToDo: add your code to calculate the offset @offset
		offset = addr % pageSize;

		//ToDo: add your code to calculate the physical address @phyAddr
		phyAddr = (frameNbr*pageSize) + offset;

		System.out.println(String.format("\t%1$10s%2$8s%3$8s%4$17s", addr, frameNbr, offset, phyAddr));		
	}

	/********************************************************************************************
	Part II: Virtual Memory
		- fifoPageReplacement()
		- lruPageReplacement()
	********************************************************************************************/

	public static void part2test(){
		printSectionHeader("Part II: Virtual Memory");


		//Test contiguousAllocation()
		System.out.println("fifoPageReplacement() [3 points]");
		fifoPageReplacement(3);
		fifoPageReplacement(4);
		System.out.println("lruPageReplacement() [3 points]");
		lruPageReplacement(3);
		lruPageReplacement(4);
	}


	public static void fifoPageReplacement(int frames) {
		/* Consider the sequence of page accesses @refString. Your system has @frames number of frames. Write the first-in first-out page replacement algorithm to calculate the number of page faults @faults that will occur.
		*/
		int faults = 0;
		
		//ToDo: add your code to calculate the faults @faults
		ArrayList<Integer> faultsList = new ArrayList<>();
		LinkedList<Integer> fifo = new LinkedList<>();

		for(int i=0; i<refString.length; i++){
			if(faultsList.size() < frames){
				//page fault
				if(!faultsList.contains(refString[i])){
					faultsList.add(refString[i]);
					fifo.add(refString[i]);
					faults++;
				}
			}
			else{
				if(!faultsList.contains(refString[i])){
					int temp = fifo.getFirst();
					faultsList.remove(temp);
					faultsList.add(refString[i]);
					fifo.add(refString[i]);
					faults++;
				}
			}
		}

		
		System.out.println("\tFIFO Faults: " + faults);
	}

	
	public static void lruPageReplacement(int frames) {
		/* Consider the sequence of page accesses @refString. Your system has @frames number of frames. Write the least recently used page replacement algorithm to calculate the number of page faults @faults that will occur.
		*/

		int faults = 0;

		//ToDo: add your code to calculate the faults @faults
		ArrayList<Integer> pages = new ArrayList<>();

		for(int i : refString){
			if(!pages.contains(i)){
				if(pages.size() == frames){
					pages.remove(0);
					pages.add(frames-1, i);
				}
				else{
					pages.add(i);
				}
				faults++;
			}
			
			else{
				pages.remove((Object)i);
				pages.add(pages.size(), i);

			}
		}
		
		

		System.out.println("\tLRU Faults: " + faults);
	}

	/********************************************************************************************
	Part III: File Systems
		- inode1()
		- inode2()
	********************************************************************************************/

	public static void part3test(){
		printSectionHeader("Part III: File Systems");

		System.out.println("inode1() [2 points]");
		inode1();
		System.out.println("inode2() [2 points]");
		inode2();
	}

	public static void inode1(){
		/* Assuming a 4 KB disk block size (@pageSize), calculate the largest file size @fileSize for an i-node that contains 20 direct pointers @directPtrs and one single indirect pointer. Make sure to convert your file size to GB.*/

		double fileSize = 0;

		//ToDo: calculate the max file size @fileSize
		int singleIndirectPtrs = 1;
		int blockinBytes = pageSize* (int)Math.pow(2, 10);
		int block = blockinBytes/pageSize;//4*1024=4096 bytes ; 4096bytes/4bytes= block size
		
		//Calculate max size then convert to GB
		//file size = pointers * block size, then /1024 for bytes -> MB and /1024 for MB -> GB
		fileSize = directPtrs + (int)Math.pow(singleIndirectPtrs,1) * block;
		fileSize = fileSize * (blockinBytes/1024/1024);

		System.out.println(String.format("\tI-Node 1 File Size: %,.2f GB", fileSize));
	}

	public static void inode2(){
		/* Assuming a 4 KB disk block size (@pageSize), calculate the largest file size @fileSize for an i-node that contains 20 direct pointers @directPtrs, one single indirect pointer, and one double indirect pointer. Make sure to convert your file size to GB.*/

		double fileSize = 0;

		//ToDo: calculate the max file size @fileSize
		int singleIndirectPtrs = 1;
		int doubleIndirectPtrs = 1;
		int blockinBytes = pageSize * 1024;
		int pointers = 1024/pageSize;//4*1024=4096 bytes ; 4096bytes/4bytes= block size
		
		//Calculate max size then convert to GB
		//file size = pointers * block size, then /1024 for bytes -> MB and /1024 for MB -> GB
		fileSize = directPtrs + (int)Math.pow(singleIndirectPtrs,1) + (int)Math.pow(doubleIndirectPtrs,2) * pointers;
		fileSize = fileSize * (blockinBytes/1024/1024);

		System.out.println(String.format("\tI-Node 2 File Size: %,.2f GB", fileSize));
	}

	/********************************************************************************************
	Part IV: Mass Storage
		- fcfsScheduling()
		- scanScheduling()
		- lookScheduling()
		- sstfScheduling()
	********************************************************************************************/

	public static void part4test(){
		printSectionHeader("Part IV: Disk Scheduling");

		//Test fcfsScheduling()
		System.out.println("fcfsScheduling() [2 points]");
		fcfsScheduling(143);

		//Test scanScheduling()
		System.out.println("scanScheduling() [2 points]");
		scanScheduling(143);

		//Test lookScheduling()
		System.out.println("lookScheduling() [2 points]");
		lookScheduling(143);

		//Test sstfScheduling()
		System.out.println("sstfScheduling() [3 points]");
		sstfScheduling(143);
	}

	public static void fcfsScheduling(int start){
		/*	Suppose a disk drive has 5000 cylinders numbered 0 – 4999. The drive is currently serving a request 
			at cylinder @start, and the previous request was at cylinder 125. The queue of pending requests, in FIFO order, is @requests. Starting from the current head position, calculate the total distance 
			(in cylinders) that the disk arm moves to satisfy all the pending requests for first-come first-serve @fcfsMovements.
		*/

		int fcfsMovements = 0;

		//ToDo: add your code to calculate @fcfsMovements
		for(int i=0; i<requests.length; i++){
			int current = requests[i];
			fcfsMovements += Math.abs(current-start);
			start = current;
		}

		System.out.println(String.format("\tFCFS Movements: %s", fcfsMovements));
	}


	public static void scanScheduling(int start){
		/*	Suppose a disk drive has 5000 cylinders numbered 0 – 4999. The drive is currently serving a request 
			at cylinder @start, and the previous request was at cylinder 125. The queue of pending requests, in FIFO order, is @requests. Starting from the current head position, calculate the total distance 
			(in cylinders) that the disk arm moves to satisfy all the pending requests for the SCAN algorithm @scanMovements.
		*/		

		int scanMovements = 0;					
		
		//ToDo: add your code to calculate @scanMovements
		int[] left = new int[requests.length];
		int[] right = new int[requests.length];
		int leftIndex = 0;
		int rightIndex = 0;

		for(int request : requests){
			if(request < start){
				left[leftIndex++] = request;
			}
			else{
				right[rightIndex++] = request;
			}
		}

		for(int i=0; i < leftIndex; i++){
			scanMovements += Math.abs(start-left[i]);
			start = left[i];
		}
		for(int i=0; i < rightIndex; i++){
			scanMovements += Math.abs(start-right[i]);
			start = right[i];
		}


		System.out.println(String.format("\tSCAN Movements: %s", scanMovements));
	}

	public static void lookScheduling(int start){
		/*	Suppose a disk drive has 5000 cylinders numbered 0 – 4999. The drive is currently serving a request 
			at cylinder @start, and the previous request was at cylinder 125. The queue of pending requests, in FIFO order, is @requests. Starting from the current head position, calculate the total distance 
			(in cylinders) that the disk arm moves to satisfy all the pending requests for the LOOK algorithm @lookMovements.
		*/		
		int lookMovements = 0;	

		//ToDo: add your code to calculate @lookMovements		
		

		System.out.println(String.format("\tLOOK Movements: %s", lookMovements));
		
	}

	public static void sstfScheduling(int start) {
		/*	Suppose a disk drive has 5000 cylinders numbered 0 – 4999. The drive is currently serving a request 
			at cylinder @start, and the previous request was at cylinder 125. The queue of pending requests, in FIFO order, is @requests. Starting from the current head position, calculate the total distance 
			(in cylinders) that the disk arm moves to satisfy all the pending requests for the Shortest-seek-time-first algorithm @sstfMovements.
		*/
	
		int sstfMovements = 0;
		
		//ToDo: add your code to calculate @sstfMovements
		ArrayList<Integer> left = new ArrayList<>();
		ArrayList<Integer> right = new ArrayList<>();

		for(int request : requests){
			if(request < start){
				left.add(request);
			}
			else{
				right.add(request);
			}
		}

		
		
		System.out.println(String.format("\tSSTF Movements: %s", sstfMovements));
	}


	public static void printSectionHeader(String sectionName) {
		System.out.println("\n" + "-".repeat(25));
		System.out.println(String.format("%1$-25s", sectionName));
		System.out.println("-".repeat(25));
	}
}