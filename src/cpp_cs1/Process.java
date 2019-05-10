package cpp_cs1;

public class Process {
	
	private int id;
	private int arrivalTime;
	private int lifeTime;
	private int segment;
	private int [] segBlock;
        private int segmentBlock;
	
	public Process( int a, int arrTime, int lifTime, int[] sArray, int seg) {
		id = a;
		arrivalTime = arrTime;
		lifeTime = lifTime;
		segBlock = sArray;
		segment = seg;
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
	
	public int getSegment() {
		return segment;
	}
	
	public int getsegSpace() {
		return segmentBlock; //changed this
	}
	
	public int getAddressSpace() {
		int total = 0;
		
		for(int i = 0; i< segBlock.length; i++) {
			total += segBlock[i];
		}
		return total;
	}
	
}