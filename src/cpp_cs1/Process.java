package cpp_cs1;

public class Process {
	
	private int id;
	private int arrivalTime;
	private int lifeTime;
	private int addressSpace;
	
	/**
	 * Constructor
	 * 
	 * @param a
	 * @param arrTime
	 * @param lifTime
	 * @param aSpace
	 */
	public Process( int a, int arrTime, int lifTime, int aSpace) {
		id = a;
		arrivalTime = arrTime;
		lifeTime = lifTime;
		addressSpace = aSpace;
	}
	
	public Process() {
		
	}
	
	/**
	 * getters for values provided above
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public int getLifeTime() {
		return lifeTime;
	}
	
	public int getAddressSpace() {
		return addressSpace;
	}
	

}
