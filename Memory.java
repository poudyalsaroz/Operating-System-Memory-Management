package cpp_cs1;

public class Memory {
	
	private int index;
	private boolean status;
        private int end;
        private int start;
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
        
        public void memHoles(int idx, int startPoint, int endPoint, int sizeOf){
            index = idx;
            end = endPoint;
            start = startPoint;
            size = sizeOf;
        }
        public void setEndPoint(int endPoint){
            end = endPoint;
        }
        public void setStartPoint(int startPoint){
            start = startPoint;
        }
        
        public void setHoleIndex(int idx){
            index = idx;
        }
        
        public int getStartPoint(){
            return start;
        }
	
        public int getSize(){
            return size;
        }
        
        public void setSize(int sizeOf){
            size = sizeOf;
        }
}
