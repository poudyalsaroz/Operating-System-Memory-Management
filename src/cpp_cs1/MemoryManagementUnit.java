package cpp_cs1;

public class MemoryManagementUnit {

	private Memory memory[];
	private Page page[];
	private Process process[];

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
		int adSpace = 0;
		int index = 0;
		
		int numOfProcess = Integer.valueOf(processData[0]); // gets # of processes in text file
		process = new Process[numOfProcess]; // creates an object for each process provided
		for (int i = 1; i < processData.length; i += 4 ){ 
			id = Integer.valueOf(processData[i].trim());
			String timeSegment[] = processData[i+1].split(" ");
			aTime = Integer.valueOf(timeSegment[0]);
			lTime = Integer.valueOf(timeSegment[1]);

			String memSegment[] = processData[i+2].split(" ");

			for(int j= 1; j < memSegment.length; j++ ) {
				adSpace += Integer.valueOf(memSegment[j]);
			}
			
			process[index] = new Process(id,aTime,lTime,adSpace);
			index +=1;
			adSpace = 0;
		}
		
		// prints out process for each id
		for(int i = 0; i < numOfProcess; i++) {
			System.out.println(process[i].getId() +" "
								+ process[i].getArrivalTime()+ " "
								+ process[i].getLifeTime()+" "
								+ process[i].getAddressSpace());
		}
	}
	
	
	/**
	 * for testing purposes
	 * trying to send process object to VSP so I can begin working on this part of the project
	 * 
	 * @param processData
	 * @return
	 */
	public Process[] createProcessess(String[] processData) {
		int id = 0;
		int aTime = 0;
		int lTime = 0;
		int adSpace = 0;
		int index = 0;
		
		int numOfProcess = Integer.valueOf(processData[0]); // gets # of processes in text file
		
		process = new Process[numOfProcess];
		
		for (int i = 1; i < processData.length; i += 4 ){ 
			id = Integer.valueOf(processData[i].trim());
			String timeSegment[] = processData[i+1].split(" ");
			aTime = Integer.valueOf(timeSegment[0]);
			lTime = Integer.valueOf(timeSegment[1]);

			String memSegment[] = processData[i+2].split(" ");

			for(int j= 1; j < memSegment.length; j++ ) {
				adSpace += Integer.valueOf(memSegment[j]);
			}
			
			process[index] = new Process(id,aTime,lTime,adSpace);
			index +=1;
			adSpace = 0;
		}
		
		return process;
	}

	public void printPage() {
		for(int i = 0; i < page.length; i++) {
			System.out.println(page[i].getPIndex() + " " + page[i].getPageFrameNumber()+" "+page[i].getStatus());
		}
	}
}
