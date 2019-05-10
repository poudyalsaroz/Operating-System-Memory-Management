package cpp_cs1;
import java.util.Queue;
import java.util.LinkedList;

public class MemoryManagementUnit {

	private Memory memory [];
	private Page page[];
	private Process process[];
	private Queue<Process> processQueue;
        private Memory memHoles [];
	static int processCount;;
        static int arraySize;

	public void createMemory(int size) {
		memory = new Memory[size];
                arraySize = size;
		for(int i = 0; i < size; i++) {
			memory[i] = new Memory(i,true);
		}
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
            System.out.println("Global variables for HOleyBATMAN");
            System.out.println(memory.length);
            for(int i = 0; i<arraySize;i++){
                //find end to memhole IF flag is 1 and memory[i] is true, AND the next index in memory is false
                if((memory[i].getStatus() == true)&&(flag == 1)&&(memory[i+1].getStatus() == false)){//(memory[i+1].getStatus() == false)||(memory[i+1].getIndex()>memory.length))){
                    flag =0;
                        //System.out.println("HOLEY BATMAN second IF");
                    int end = memory[i].getIndex();
                    memHoles[k].setEndPoint(end);
                    size = end-memHoles[k].getStartPoint();
                    memHoles[k].setSize(size);
                    k++;
                }
                //find end to memhole IF flag is 1, AND i+1 is equal to the arraySize
                else if((flag == 1) && ((i+1)==arraySize)){//(memory[i].getIndex()==(arraySize-1))){
                    flag =0;
                        //System.out.println("HOLEY BATMAN LAST IF");
                    int end = memory[i].getIndex();
                    memHoles[k].setEndPoint(end);
                    size = end-memHoles[k].getStartPoint();
                    memHoles[k].setSize(size);
                }
                //this is to set the start point of the memory hole
                else if((memory[i].getStatus() == true)&&(flag==0)){//&& (memory[i].getIndex()!=arraySize)){
                        //System.out.println("HOLEY BATMAN FIRST IF");
                    int start = memory[i].getIndex();
                        //System.out.println("HOLEY BATMAN FIRST IF, preSetStartPoint");
                        //System.out.println(start);
                    memHoles = new Memory[memory.length];
                    memHoles[k] = new Memory(k,true);
                    memHoles[k].setStartPoint(start);
                        //System.out.println("HOLEY BATMAN FIRST IF, postSetStartPoint");
                    flag = 1;
                }
            }
            //check to see if this section even fucking works
            System.out.println("Pre end point for");
            for(int i =0; i==memHoles.length;i++){
                System.out.println("Memory hole "+i+" = "+memHoles[i].getSize());
            }
        }
        
        
        public void SegmentFirstFit(){
            long startTime = System.currentTimeMillis();
            long elapsedTime;
            boolean flag = false;
            int tally = 0;
            int finalFlag = 0;
            System.out.println("Global variables");
         
            while(finalFlag < processQueue.size()){
                System.out.println("while loop beginning");
                Process p = processQueue.peek();
                elapsedTime = System.currentTimeMillis()-startTime;
                System.out.println(elapsedTime);
                if(p.getArrivalTime()>=elapsedTime){
                    HoleyMemoryBatman();
                    System.out.println("HoleyMemoryBatman call in first if");
                    //if the process only has one segment, check if open hole, put in
                    if( p.getSegment()==1){
                        
                        for(int k = 0; k<memHoles.length;k++){
                            if(p.getsegSpace()<=memHoles[k].getSize()){
                                
                                tally++;
                            }
                        }
                        if(tally != 0){
                            //ACTUALLY MANIPULATE THE MEMORY ARRAY
                            //take over the spaces
                            //revert the tally flag
                            tally = 0;
                            finalFlag++;
                        }
                    }
                    
                    //if the proccess has more than 1 segment, FML
                    else if(p.getSegment()>=2){
                        for(int j = 0; j<p.getSegment();j++){
                            for(int k = 0; k<memHoles.length;k++){
                                if(p.getsegSpace()<=memHoles[k].getSize()){
                                    System.out.println("MemHoleSize = "+memHoles[k].getSize());
                                    int get = memHoles[k].getSize();
                                    int getSeg = p.getsegSpace();
                                    int remainder = get-getSeg;
                                    memHoles[k].setSize(remainder);
                                    System.out.println("MemHoleSize = "+memHoles[k].getSize());
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
