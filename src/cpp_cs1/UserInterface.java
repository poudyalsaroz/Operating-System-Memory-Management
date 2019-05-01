package cpp_cs1;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserInterface {
	
	private MemoryManagementUnit mmu;
	Scanner scan;
	
	public UserInterface() {
		mmu = new MemoryManagementUnit();
	}
	
	public void startMenu() {
		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		String []fileContent = null;
		
		try {
			String line;
			br = new BufferedReader(new FileReader("in1.dat"));	
			while( ( line = br.readLine() )!= null ) {
				sb.append(line+" ");
			}
			fileContent = (sb.toString().split("\\s+"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//mmu.createProcesses(fileContent);	
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
		System.out.println("Enter the page frame size");
		return scan.nextInt();
	}
	public int getPageSize() {
		System.out.println("Enter the page size");
		return scan.nextInt();
	}
}
