package cpp_cs1;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Dictionary;
//import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author miguel
 *
 */
public class VSP {
	// best, first, worst fit
	int parameter;
	
	int sizeArray = 0;
	
	// object array of processes from text file
	Process processes[];
	
	// will have total number of processes from text file
	int totalProcess; 
	
	// will increment based on current process in memory
	int currentProcess; 
	
	// memory manager 
	int memories[];
	
	// keeps track of overall time of process in memory
	int virtualTime;
	
	// movingProcess() function values
	int idMemory = 0;
	int occurrences = 0;
	
	// memoryManagerPrint() values
	int startIndex = 0;
	int endIndex = 0;
	
	// movingProcess() function values
	int totalProcessTime = 0;
	
	// remove process function
	int idCount = 0;
	int currentIndex = 0;
	int process = 0;
	int currentId = 0;
	
	int count = 0;
	
	// will keep track of queues entering the 
	Queue<Integer> inputQueue;
	
//	Dictionary<Integer, Integer> processTime;
	Map<Integer, Integer> processTime;

	ArrayList<Integer> processTracker;
	ArrayList<Integer> idTracker;
	
	
	// keeps track of the process that will go into the memory
//	int idMemory;
//	int occurrences;
	
	final int MAX_TIME = 100000;
	
	
	/**
	 * default constructor
	 */
	public VSP(int parameter, Process[] process, int[] memory) {
		// first-fit (1) best-fit (2) worst-fit (3)
		parameter = this.parameter;
		
		// copies all the contents of the process objects
		// into the processes objects
		processes = new Process[process.length];
		for(int i = 0; i < process.length; i++) {
			processes[i] = process[i];
		}
		
		// sets array to 0
		memories = new int[memory.length];
		for(int i = 0; i < memory.length; i++) {
			memories[i] = 0;
		}
		
		sizeArray = memory.length;
		
		// keeps track of number of processes that will go through the memory
		currentProcess = 0;
		
		// sets the virtual time to zero
		virtualTime = 0;
		
		// instantiates a new queue for processes to go to
		inputQueue = new LinkedList<Integer>();
		
		// will keep track of the total time process will be in memory
//		processTime = new HashMap<Integer, Integer>();
		
		
		// have one arraylist for process time
		// second array list for id tracking
		processTracker = new ArrayList<Integer>();
		idTracker = new ArrayList<Integer>();
	}
	
	/**
	 * will start performing VSP on data processes provided
	 */
	public void startVSP(int policyParameter){
		
		firstFit(processes);
	}
	
	public void firstFit(Process[] process) {
		// currentProcess = increments as processes go through memory
		// inputQueue processes IDs that go into memory
		
		totalProcess = process.length - 1;
		
		boolean loop = true;
//		while(loop) {
		for(int i = 0; i < 1005; i++) { // TESTING PURPOSES
			
			// compares virtual time with arrival time of process
			// if true, then enters queue to be entered
			if(virtualTime == process[currentProcess].getArrivalTime()) {
				System.out.println("\nt=" + virtualTime);
				
				inputQueue.add(process[currentProcess].getId()); // adds process id to queue
				currentProcess++; // increments process to next available processor
				processArrival(); // prints process to terminal
				
				// CHECKS IF THERE IS A SECOND PROCESS ARRIVING AT SAME TIME
				if(virtualTime == process[currentProcess].getArrivalTime()) {
					inputQueue.add(process[currentProcess].getId());
					currentProcess++;
					
					processArrival(); // prints arrival time
				}
			}
			
			// checks if queue is empty 
			// if not then moves to memory manager
			// and prints to terminal
			if(!inputQueue.isEmpty()) {
//				movingProcess(currentProcess, inputQueue, process, memories);
				movingProcess(); // memories
				
			} 
			
			
			// checks if a process has been completed
			// check dictionary, if process has been completed
//			if(processTracker.contains(virtualTime)) { // OG
			while(processTracker.contains(virtualTime)){
				
				System.out.println("t=" + virtualTime);
////				System.out.println("LEAVE MEMORY");
//				count++;
//				System.out.println(count);
//				
				removingProcess();
			}
			
//			memoryFull();
			

			// exits loop once all processes are completed
			if(currentProcess == totalProcess) {
				System.out.println("All processes have been completed");
				loop = false;
				
			}
			virtualTime++;
			// exits loop until time max time has been achieved
			if(virtualTime == MAX_TIME) {
				System.out.println("Time has reached maximum limit");
				loop = false;
//				break;
			}
		}
		
		System.out.println("memory queue to leave: " + processTracker);
		System.out.println("id tracker queue: " + idTracker);
		
	}
	
	/**
	 * will remove the process from the memory manager once the time 
	 * has been fulfilled by the process
	 */
	public void removingProcess() {
		
		int time = virtualTime;
		
		// process 1 example
		currentIndex = processTracker.indexOf(time);
		process = processTracker.get(currentIndex); 
//		System.out.println("process: " + process);
		
		currentId = idTracker.get(currentIndex);
		
		System.out.println("Process " + currentId + " complete");
		
//		
//		// MAP
////		idNumber = processTime.get(virtualTime);
//		
		// set the value of the id number to zero so it can be empty again
		for(int i = 0; i < memories.length; i++) {
			if(memories[i] == currentId) {
				memories[i] = 0;
			}
		}
//		
//		// call the updated memory map
//		// when the process has finished running
		memoryManagerPrint();
//		
//		// might need to change depending on location of value/pointer that
//		// needs to be deleted
		processTracker.remove(0);
		idTracker.remove(0);
	}
	
	/**
	 * will move the process into the the memory manager
	 * it will move from the queue into the memory manager
	 * and print to terminal
	 * 
	 * @param inputQueue
	 * @param process
	 * @param memoriess
	 */
	public void movingProcess() {
		
		// stores value of id before getting removed from queue for local reference
		idMemory = inputQueue.peek();
		
		System.out.println("MM moves Process " + idMemory + " to memory");
		
		// will remove id from queue into the memory manager
		inputQueue.remove();
		
		System.out.println("Input Queue: " + inputQueue);
		
		// looks for empty spaces in memory and adds it to next available location
		// 0 is the empty space
		occurrences = firstOccurrence(0);
		
		// process address space it takes up
		for(int i = occurrences; i < processes[idMemory - 1].getAddressSpace() + occurrences; i++) {
			memories[i] = idMemory;
		}
		
		// adds the time of virtual time and process to be referenced when it is time to leave memory
		// for the map
//		processTime.put((process[idMemory - 1].getLifeTime() + virtualTime), (idMemory - 1)); // OG
		
		// replaces dictionary
		// 0 = virtualTime + lifeTime
		totalProcessTime = processes[idMemory - 1].getLifeTime() + virtualTime;
		processTracker.add(totalProcessTime);
		idTracker.add(idMemory);
		
		// prints out the contents of the memory manager
		memoryManagerPrint();
		
	}
	
	
	/**
	 * keeps track of all the processes in the memory manager array
	 * will also print out the current processes currently in the memory manager
	 */
	public void memoryManagerPrint() {
		System.out.println("Memory Map: ");
		// startIndex
		// endIndex
		
		boolean loop = true;
		
		int processValue = 0;
		
//		System.out.println("array length: " + memory.length);
		
		for(int i = 0; i < memories.length; i++) {
			loop = true;
			processValue = memories[i];
			startIndex = i;
			
			if(i == memories.length - 1) {
				loop = false;
			}
			while(loop) {
				if((processValue == memories[i]) && (i < memories.length - 1)) { // - 1
					i++;
				} 
				else {
					i--;
					endIndex = i;
					loop = false;
				}
			}
			
			// if process in array is 0, then it is considered "empty" and
			// will be called a hole for printing purposes
			if(processValue == 0) {
				System.out.println("\t" + startIndex + "-" + endIndex + ": Hole ");
			} else {
				System.out.println("\t" + startIndex + "-" + endIndex + ": Process " + processValue);
			}
			
		}
		
	}
	/**
	 * prints arrival times into queue and prints description
	 * 
	 * @param currentProcess
	 * @param inputQueue
	 */
	public void processArrival() {
		System.out.println("Process " + currentProcess + " arrives");
		System.out.println("Input Queue: " + inputQueue);
	}
	
	/**
	 * 
	 * @param memory
	 * @param sizeArray
	 * @param findElement
	 * @return
	 */
	public int firstOccurrence(int findElement) {
		int start = -1;
		
		for (int i = 0; i < sizeArray; i++) {
			if(memories[i] == findElement) {
				start = i;
				break;
			}
		}
		
		return start;
	}
	
	public void memoryFull(){
		int count = 0;
		for(int i = 0; i < sizeArray; i++) {
			if(memories[i] == 0) {
				count++;
			}
		}
		
		System.out.println("Memory is full");
	}
	

}
