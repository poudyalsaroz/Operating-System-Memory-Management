/**
 * 
 */
package cpp_cs1;

import java.util.LinkedList;
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
	
	int idMemory;
	int occurrences;
	
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
//			memories[i] = 0;
		}
		
		currentProcess = 0;
		
		virtualTime = 0;
		inputQueue = new LinkedList<Integer>();
	}
	
	/**
	 * will start performing VSP on data processes provided
	 */
	public void startVSP(int policyParameter){
		int first = 0;
		int last = 0;
		int sizeArray = memories.length;
		
		System.out.println();
		
		firstFit(processes, memories);
	}
	
	public void firstFit(Process[] process, int[] memory) {
		// currentProcess = increments as processes go through memory
		// inputQueue processes IDs that go into memory
		
		totalProcess = process.length - 1;
		
		boolean loop = true;
//		while(loop) {
		for(int i = 0; i < 3; i++) { // testing purposes
			// compares virtual time with arrival time of process
			// if true, then enters queue to be entered
			if(virtualTime == process[currentProcess].getArrivalTime()) {
				System.out.println("t=" + virtualTime);
				
				inputQueue.add(process[currentProcess].getId()); // adds process id to queue
				currentProcess++; // increments process to next available processor
				processArrival(currentProcess, inputQueue); // prints process to terminal
				
				// checks second process to see if there are same arrival times to add to queue
				if(virtualTime == process[currentProcess].getArrivalTime()) {
					inputQueue.add(process[currentProcess].getId());
					currentProcess++;
					
					processArrival(currentProcess, inputQueue);
				}
			}
			
			// checks if queue is empty 
			// if not then moves to memory manager
			// and prints to terminal
			if(!inputQueue.isEmpty()) {
				movingProcess(currentProcess, inputQueue, process, memories);
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
	
	public void movingProcess(int currentProcess, Queue<Integer> inputQueue, Process[] process, int[] memoriess) {
//		int idMemory;
		
		// stores value of id before getting removed from queue for local reference
		idMemory = inputQueue.peek();
		
		System.out.println("MM moves Process " + idMemory + " to memory");
		idMemory = inputQueue.peek();
		
		inputQueue.remove();
		
		System.out.println("Input Queue: " + inputQueue);
		
		occurrences = firstOccurrence(memories, memories.length, 0);
		
		// process address space it takes up
//		System.out.println("space it takes up for process" + idMemory + ": " + process[idMemory].getAddressSpace());
		// 
		for(int i = occurrences; i < process[idMemory - 1].getAddressSpace() + occurrences; i++) {
			memories[i] = idMemory;
		}
		
		memoryManagerPrint(memoriess);
		
//		System.out.println("Memory Map: ");
//		// need to move the memory map testing to other values because local values will be purged once exiting this function
//		System.out.println("\t" + firstOccurrence(memories, memories.length, idMemory) + "-" + lastOccurrence(memories, memories.length, idMemory) + ": Process " + idMemory); 
//		System.out.println("\t" + firstOccurrence(memories, memories.length, 0) + "-" + lastOccurrence(memories, memories.length, 0) + ": Hole");
		
		
		
	}
	
	
	/**
	 * will format the memory manager 
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
				if((processValue == memory[i]) && (i < memory.length - 1)) {
					++i;
				} 
				else {
					i--;
					endIndex = i;
					loop = false;
				}
			}
			System.out.println("\t" + startIndex + "-" + endIndex + ": Process " + processValue);
			
			
			
		}
		
		// need to move the memory map testing to other values because local values will be purged once exiting this function
		//System.out.println("\t" + firstOccurrence(memories, memories.length, idMemory) + "-" + lastOccurrence(memories, memories.length, idMemory) + ": Process " + idMemory); 
		//System.out.println("\t" + firstOccurrence(memories, memories.length, 0) + "-" + lastOccurrence(memories, memories.length, 0) + ": Hole");
		
		
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
