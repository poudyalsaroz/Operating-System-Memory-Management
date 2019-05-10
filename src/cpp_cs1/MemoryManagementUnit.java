package cpp_cs1;
import java.util.Queue;
import java.util.LinkedList;

public class MemoryManagementUnit {

	private Memory memory [];
	private Page page[];
	private Process process[];
	private Queue<Process> processQueue;
        private int[] segArray;
        private int[] testArray;
	static int processCount;;
        static int processCountTotal;
        static int arraySize;

	public void createMemory(int size) {
		memory = new Memory[size];
                arraySize = size;
		for(int i = 0; i < size; i++) {
			memory[i] = new Memory(i,true);
		}
	}
        
        public void createArrayOfZero(int size){
            segArray = new int[size];
            
        }
        public void createTestArray(int size){
            testArray = new int[size];
        }
	public void createPages(int pageSize) {
		page = new Page[pageSize];
		for(int i = 0; i < pageSize; i++) {
			page[i] = new Page(i, true);
		}
	}

	public void createPageFrame(int pageSize, int pageFrameSize) {
		int frameNum = 0;
		page[0].setPageFrameNumber(frameNum);
		for(int i = 1; i < pageSize; i++) {
			if(i % pageFrameSize == 0) {
				frameNum += 1;
				page[i].setPageFrameNumber(frameNum);
			}
			else
				page[i].setPageFrameNumber(frameNum);
		}
	}

	public void createProcesses(String[]processData) {

		int id = 0;
		int aTime = 0;
		int lTime = 0;
		int index = 0;

		int numOfProcess = Integer.valueOf(processData[0]);
		processCount = numOfProcess;
                processCountTotal = numOfProcess;
		process = new Process[numOfProcess];
		for (int i = 1;i< processData.length; i+=4 ){

			id = Integer.valueOf(processData[i].trim());

			String timeSegment[] = processData[i+1].split(" ");
			aTime = Integer.valueOf(timeSegment[0]);
			lTime = Integer.valueOf(timeSegment[1]);

			String memSegment[] = processData[i+2].split(" ");
			int seg = Integer.valueOf(memSegment[0]);
			int[] segArray = new int[seg];

			for(int j= 1; j <= seg; j++ ) {
				segArray[j-1] =  Integer.valueOf(memSegment[j]);
			}

			process[index] = new Process(id,aTime,lTime,segArray, seg);
			index +=1;
		}

		//just printing processes with their attributes to make sure each process has all the attributes.

		System.out.println("For VSP and Paging:");

		for(int i = 0; i < numOfProcess; i++) {
			System.out.println(process[i].getId() +" "
					+ process[i].getArrivalTime()+ " "
					+ process[i].getLifeTime()+" "
					+ process[i].getAddressSpace());
		}
		System.out.println();
		System.out.println("For segmentation:");

		for(int i = 0; i < numOfProcess; i++) {

			System.out.println("process " + process[i].getId());
			System.out.println("No. of segments "+ process[i].getSegment());

			//for(int j = 0; j< process[i].getsegSpace().length; j++) {

			//	System.out.println(process[i].getsegSpace()[j]);
			//}
		}
	}

	/**
	public void printPage() {
		for(int i = 0; i < page.length; i++) {
			System.out.println(page[i].getPIndex() + " " + page[i].getPageFrameNumber()+" "+page[i].getStatus());
		}
	}
	**/
	
	public void createVirtualTime() {	
		
		int currentTime = 0;	
		processQueue = new LinkedList<Process>(); //should this scope be changed?
		Process p;

		while(processCount > 0) {			
			
			for(int i = 0; i< process.length;i++) {	
				
				p = process[i];
				if(p.getArrivalTime() == currentTime) {
					
					System.out.println("t = "+ currentTime + " Process "+ p.getId()+ " arrives.");
				
					processQueue.add(p);
					
					System.out.print("Input Queue: [");
					processQueue.stream().forEach(Process ->{
						System.out.print(Process.getId()+ " ");
					});
					System.out.println("]");
					
					processCount = processCount-1;
					
				}
			}
			currentTime += 1;
		}
	}
        
        //create method for creating array of memory holes and their sizes
        public void HoleyMemoryBatman(){
            //set hole array to equal null
            int size;
            int flag = 0;
            int k = 0;
            int memSize = 1;
            System.out.println("Global variables for HOleyBATMAN");
            System.out.println(memory.length);
            for(int i = 0; i<arraySize;i++){
                //System.out.println(i);
                //find end to memhole IF flag is 1 and memory[i] is true, AND the next index in memory is false
 
                if((flag==1)&&((segArray[i]==0)&&(i == arraySize-1))){
                    int end = i;
                    int start = testArray[k];
                    testArray[k] = end-start;
                    //System.out.println("set the end point "+ testArray[k]);
                    k++;
                    flag = 0;
                }
                else if ((flag==1)&&(segArray[i]==0)){
                    memSize++;
                }
                //this is to set the start point of the memory hole
                else if((segArray[i] == 0)&&(flag==0)){//&& (memory[i].getIndex()!=arraySize)){
                        //System.out.println("HOLEY BATMAN FIRST IF");
                    int start = i;
                        //System.out.println("HOLEY BATMAN FIRST IF, preSetStartPoint");
                        //System.out.println(start);
                    testArray[k] = start;
                    //System.out.println("set the start point "+ testArray[k]);
                    flag++;
                }
            }
           /* //check to see if this section even works
            System.out.println("Pre end point for");
            for(int i =0; i<=testArray.length;i++){
                System.out.println("Memory hole "+i+" = "+testArray[i]);
            }*/
        }
        
        
        public void SegmentFirstFit(){
            //long startTime = System.currentTimeMillis();
            long elapsedTime;
            int tally = 0;
            int finalFlag = 0;
            HoleyMemoryBatman();
            long startTime = System.currentTimeMillis();
            while(finalFlag < processCountTotal){
                //System.out.println("while loop beginning");
                Process p = processQueue.peek();
                elapsedTime = System.currentTimeMillis()-startTime;
                //System.out.println(elapsedTime);
                //System.out.println(p.getArrivalTime());
                if(p.getArrivalTime()<=elapsedTime){
                    //System.out.println("SegmentFirst first IF");
                    //if the process only has one segment, check if open hole, put in
                    if( p.getSegment()==1){
                        for(int k = 0; k<testArray.length;k++){
                            if(p.getsegSpace()<=testArray[k]){
                                tally++;
                            }
                        }
                        if(tally != 0){
                            //ACTUALLY MANIPULATE THE MEMORY ARRAY
                            //take over the spaces
                            //revert the tally flag
                            System.out.println("Tally Ho Too");
                            int sizeOfHole = 0;
                            int startPoint = 0;
                            for(int i = 0; i<arraySize;i++){              //if there's room, loop through length of segArray
                                if(segArray[i]!=0){                             //if digit at segArray[i] is not equal to 0, set variable sizeOfHole back to zero
                                    sizeOfHole = 0;
                                }
                                else if(sizeOfHole+1 == p.getsegSpace()){         //once sizeOfHole reaches the size of the segment size, start filling in the segArray to the ID num of the process
                                   System.out.println(sizeOfHole);
                                    for(int j = 0; j<sizeOfHole;j++){
                                        segArray[startPoint+j] = p.getId();
                                        System.out.println("Segarray start point" +segArray[startPoint+j]);
                                        testArray[startPoint+j] = p.getId();
                                    }
                                    i = arraySize;
                                    processQueue.remove();
                                   // System.out.println("removed");
                                }
                                
                                else if((segArray[i]==0)&&(sizeOfHole==0)){                        //if the digit at segArray[i] is equal to 0, add one to variable sizeOfHole
                                    startPoint = i;
                                    sizeOfHole++;
                                }
                                else if(segArray[i]==0){      
                                    sizeOfHole++;
                                }
                            }
                            tally = 0;
                            finalFlag++;
                            
                            //HoleyMemoryBatman();
                        }
                    }
                    
                    //if the proccess has more than 1 segment, FML
                    else if(p.getSegment()>=2){
                        for(int j = 0; j<p.getSegment();j++){
                            for(int k = 0; k<testArray.length;k++){
                                if(p.getsegSpace()<=testArray[k]){
                                    System.out.println("MemHoleSize = "+testArray[k]);
                                    int get = testArray[k];
                                    int getSeg = p.getsegSpace();
                                    int remainder = get-getSeg;
                                    testArray[k] = remainder;
                                    System.out.println("MemHoleSize = "+testArray[k]);
                                    tally++;
                                }
                                
                            //placeholder for memholes array to allow deletion of accessed mem hole
                            //if a memhole is large enough to accommodate, 
                            //delete space taken in temp array
                            //refresh temp array
                            //ex. space of 400, process of 200 finds, uses it. change 400 to 200
                            // IF no segments can hold a process, add +1 to TALLY


                            //then loop back and test next segment
                            
                            }
                        }
                        if(tally==p.getSegment()){//then we fill em in, fo realz
                            HoleyMemoryBatman();
                            
                            
                            
                            
                            tally = 0;
                           
                        }
                    }
                    finalFlag++;
                  
                     //i need an array of memoryhole objects with parameters(size, start point, and end point, AND size)
                        // set the location of the memHole to the index of the process
                        
                    //check to see if memory array has a spot open
                    //if YES
                        //change length of memory array accessed to value of process num
                        //start new timer(with larger scope) to add elapsed time and getFinalTime together
                    //if NO
                        //break and check again
                }
            }
        }

}