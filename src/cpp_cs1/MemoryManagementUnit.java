package cpp_cs1;

public class MemoryManagementUnit {

	private Memory memory [];
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
	
	/**
	public void createProcesses(String[]processData) {
		
		for(int i = 0 ;i< processData.length;i++ ) {
			System.out.println(processData[i]);
		}
		
		
		int numOfProcess = Integer.valueOf(processData[0]);
		int id = 0;
		int aTime = 0;
		int lTime = 0;
		int segment = 0;
		int adSpace = 0;
		
		for(int i = 1; i< (processData.length-4);) {
			
			id = Integer.valueOf(processData[i]);
			aTime = Integer.valueOf(processData[i+1]);
			lTime = Integer.valueOf(processData[i+2]);
			segment = Integer.valueOf(processData[i+3]);
			
			if(segment > 1) {
				int j;
				for(j = 1; j <= segment; j++) {
					System.out.println("j:" + j);
					adSpace += Integer.valueOf(processData[i+j]);
					i += i+3+j;
				}
			}		
			else {
				i+=1;
			}
			System.out.println("Adspace "+adSpace);	
		}
		for(int i = 0; i < numOfProcess; i++) {
			process[i] = new Process(id,aTime,lTime,adSpace);
		}
		
		for(int i = 0; i < numOfProcess; i++) {
			System.out.println(process[i].getId() +" "+ process[i].getArrivalTime()+ " "+ process[i].getLifeTime()+ process[i].getAddressSpace());
		}
		
	}
	**/

	public void printPage() {
		for(int i = 0; i < page.length; i++) {
			System.out.println(page[i].getPIndex() + " " + page[i].getPageFrameNumber()+" "+page[i].getStatus());
		}
	}
}
