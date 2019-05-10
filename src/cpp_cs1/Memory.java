package cpp_cs1;

public class Memory {
	
	private int index;
	private boolean status;
	private int size;
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
        
	
        public int getSize(){
            return size;
        }
        
        public void setSize(int sizeOf){
            size = sizeOf;
        }
}