package cpp_cs1;
import java.util.Queue;
import java.util.LinkedList;

public class MemoryManagementUnit {

	private Memory memory [];
	private Page page[];
	private Process process[];
	private Queue<Process> processQueue;
	static int processCount;

	public void createMemory(int size) {
		memory = new Memory[size];
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

			for(int j = 0; j< process[i].getsegSpace().length; j++) {

				System.out.println(process[i].getsegSpace()[j]);
			}
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
		processQueue = new LinkedList<Process>();
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

}