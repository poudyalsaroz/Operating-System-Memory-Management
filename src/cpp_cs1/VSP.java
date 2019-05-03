/**
 * 
 */
package cpp_cs1;

/**
 * @author miguel
 *
 */
public class VSP {
	int parameter;
	
	Process processes[];
	
	//Process process[];
	
	/**
	 * default constructor
	 */
	public VSP(int parameter, Process[] process) {
		parameter = this.parameter;
		
		/**
		 * copies all the contents of the process objects
		 * into the processes 
		 */
		processes = new Process[process.length];
		for(int i = 0; i < process.length; i++) {
			processes[i] = process[i];
		}
//		processes = process;
//		
//		Process process = new Process();
		
	}
	
	/**
	 * will start performing VSP on data processes provided
	 */
	public void startVSP(int policyParameter){
		
		System.out.println("This is id #: " + processes[0].getId());
		
		System.out.println("You chose: " + policyParameter);
		
		printProcess(processes);
	}
	
	/**
	 * prints out contents of object for testing purposes
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
