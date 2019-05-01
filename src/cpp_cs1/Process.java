package cpp_cs1;

public class Process {
	
	private int id;
	private int arrivalTime;
	private int lifeTime;
	private int addressSpace;
	
	public Process( int a, int arrTime, int lifTime, int aSpace) {
		id = a;
		arrivalTime = arrTime;
		lifeTime = lifTime;
		addressSpace = aSpace;
	}
	
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
