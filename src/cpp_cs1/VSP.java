/**
 * 
 */
package cpp_cs1;

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
	
	// will keep track of queues entering the 
	Queue<Integer> inputQueue;
	
//	Dictionary<Integer, Integer> processTime;
	Map<Integer, Integer> processTime;
	
	
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
		
		// copies the memory information to be used in VSP
		memories = new int[memory.length];
		for(int i = 0; i < memory.length; i++) {
			memories[i] = memory[i];
		}
		
		// keeps track of number of processes that will go through the memory
		currentProcess = 0;
		
		// sets the virtual time to zero
		virtualTime = 0;
		
		// instantiates a new queue for processes to go to
		inputQueue = new LinkedList<Integer>();
		
		// will keep track of the total time process will be in memory
		processTime = new HashMap<Integer, Integer>();
	}
	
	/**
	 * will start performing VSP on data processes provided
	 */
	public void startVSP(int policyParameter){

		
		
		firstFit(processes, memories);
	}
	
	public void firstFit(Process[] process, int[] memory) {
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
				processArrival(currentProcess, inputQueue); // prints process to terminal
				
				// CHECKS IF THERE IS A SECOND PROCESS ARRIVING AT SAME TIME
				if(virtualTime == process[currentProcess].getArrivalTime()) {
					inputQueue.add(process[currentProcess].getId());
					currentProcess++;
					
					processArrival(currentProcess, inputQueue); // prints arrival time
				}
			}
			
			// checks if queue is empty 
			// if not then moves to memory manager
			// and prints to terminal
			if(!inputQueue.isEmpty()) {
//				movingProcess(currentProcess, inputQueue, process, memories);
				movingProcess(inputQueue, process, memory); // memories
				
			} 
			
			// checks if a process has been completed
			// check dictionary, if process has been completed
			if(processTime.containsKey(virtualTime)) {
				removingProcess(memory, virtualTime);
			}
			
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
		
	}
	
	/**
	 * will remove the process from the memory manager once the time 
	 * has been fulfilled by the process
	 */
	public void removingProcess(int[] memory, int virtualTime) {
		// will store the id number of the process
		// of the id that will be removed from memory
		int idNumber = 0;
		
		// prints out the statement to terminal of the process that is completed
		System.out.println("t=" + virtualTime);
		System.out.println("\tProcess " + processTime.get(virtualTime) + " completes");
		
		idNumber = processTime.get(virtualTime);
		
		// set the value of the id number to zero so it can be empty again
		for(int i = 0; i < memory.length; i++) {
			if(memory[i] == (idNumber - 1)) {
				memory[i] = 0;
			}
		}
		
		// call the updated memory map
		// when the process has finished running
		memoryManagerPrint(memory);
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
	public void movingProcess(Queue<Integer> inputQueue, Process[] process, int[] memoriess) {
		// keeps track of the current process that is inside the queue
		int idMemory;
		
		// will find the first available free position in memory so that the process can move into
		int occurrences;
		
		// stores value of id before getting removed from queue for local reference
		idMemory = inputQueue.peek();
		
		System.out.println("MM moves Process " + idMemory + " to memory");
		idMemory = inputQueue.peek();
		
		// will remove id from queue into the memory manager
		inputQueue.remove();
		
		System.out.println("Input Queue: " + inputQueue);
		
		// looks for empty spaces in memory and adds it to next available location[
		occurrences = firstOccurrence(memories, memories.length, 0);
		
		// process address space it takes up
		for(int i = occurrences; i < process[idMemory - 1].getAddressSpace() + occurrences; i++) {
			memories[i] = idMemory;
		}
		
		// adds the time of virtual time and process to be referenced when it is time to leave memory
		// for the map
		processTime.put((process[idMemory - 1].getLifeTime() + virtualTime), (idMemory - 1));
		
		// prints out the contents of the memory manager
		memoryManagerPrint(memoriess);
		
	}
	
	
	/**
	 * keeps track of all the processes in the memory manager array
	 * will also print out the current processes currently in the memory manager
	 */
	public void memoryManagerPrint(int[] memory) {
		System.out.println("Memory Map: ");
		// keeps track of start and ending indexes for each process
		int startIndex = 0;
		int endIndex = 0;
		
		boolean loop = true;
		
		int processValue = 0;
		
//		System.out.println("array length: " + memory.length);
		
		for(int i = 0; i < memory.length; i++) {
			loop = true;
			processValue = memory[i];
			startIndex = i;
			
			if(i == memory.length - 1) {
				loop = false;
			}
			while(loop) {
				if((processValue == memory[i]) && (i < memory.length - 1)) { // - 1
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
	public void processArrival(int currentProcess, Queue<Integer> inputQueue) {
		System.out.println("Process " + (currentProcess) + " arrives");
		System.out.println("Input Queue: " + inputQueue);
	}
	
	/**
	 * 
	 * @param memory
	 * @param sizeArray
	 * @param findElement
	 * @return
	 */
	public int firstOccurrence(int[] memory, int sizeArray, int findElement) {
		int start = -1;
		
		for (int i = 0; i < sizeArray; i++) {
			if(memory[i] == findElement) {
				start = i;
				break;
			}
		}
		
		return start;
	}
	
	/**
	 * finds last occurring element in the array
	 * 
	 * @param memory
	 * @param sizeArray
	 * @param findElement
	 * @return
	 */
	public int lastOccurrence(int[] memory, int sizeArray, int findElement) {
		int end = -1;
		
		for(int i = sizeArray - 1; i >= 0; i--) {
			if(memory[i] == findElement) {
				end = i;
				break;
			}
		}
		
		return end;
	}
	
	/**
	 * prints out contents of object for testing purposes
	 * 
	 * @param processes
	 */
	public void printProcess(Process processes[]) {
		// prints out process for each id
		for(int i = 0; i < processes.length; i++) {
			System.out.println(processes[i].getId() +" "
								+ processes[i].getArrivalTime()+ " "
								+ processes[i].getLifeTime()+" "
								+ processes[i].getAddressSpace());
		}
	}
}
