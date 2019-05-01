package cpp_cs1;

public class Memory {
	
	private int index;
	private boolean status;
	
	public Memory(int idx, boolean stat) {
		index = idx;
		status = stat;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setStatus(boolean bool) {
		status = bool;
	}
	
	public boolean getStatus() {
		return status;
	}
	
}
