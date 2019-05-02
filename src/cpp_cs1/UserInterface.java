package cpp_cs1;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class UserInterface {

	private MemoryManagementUnit mmu;
	Scanner scan;

	public UserInterface() {
		mmu = new MemoryManagementUnit();
	}

	public void startMenu() {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String []fileContent = null;

		try {
			String line;
			//change the filename each time different file is used for input.
			//specify path if input file is not in the same directory.
			br = new BufferedReader(new FileReader("in1.dat"));	 // OG sIn.dat
			while( ( line = br.readLine() )!= null ) {
				sb.append(line+"\n");
			}
			fileContent = (sb.toString().split("\\r?\\n"));
			makeProcess(fileContent);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		try {
			br.close();
		}
		catch(Exception e) {
		}
	}

	public void makeProcess(String[]fileContent) {
		mmu.createProcesses(fileContent);	
		scan = new Scanner(System.in); 
		System.out.println("Enter the size of memory");
		int size = scan.nextInt();
		mmu.createMemory(size);
		int input = getMemoryManagementPolicy();
		int parameter;
		switch(input) {
		case 1:
			parameter = getPolicyParameter();
			break;
		case 2:
			int pageSize = getPageSize();
			mmu.createPages(pageSize);
			int frameSize = getPageFrameSize();
			mmu.createPageFrame(pageSize, frameSize);
			mmu.printPage();
			break;
		case 3:
			parameter = getPolicyParameter();
			break;
		}
	}

	public int getMemoryManagementPolicy(){
		System.out.println("Enter the Memory Management Policy");
		System.out.println("VSP(1)");
		System.out.println("PAG(2)");
		System.out.println("SEG(3)");
		return  scan.nextInt();
	}

	public int getPolicyParameter() {
		System.out.println("Enter the Policy Parameter");
		System.out.println("First-Fit(1)");
		System.out.println("Best-Fit(2)");
		System.out.println("Worst-Fit(3)");
		return scan.nextInt();
	}
	public int getPageFrameSize() {
		System.out.print("Enter the page frame size: ");
		return scan.nextInt();
	}
	public int getPageSize() {
		System.out.print("Enter the page size: ");
		return scan.nextInt();
	}
}
