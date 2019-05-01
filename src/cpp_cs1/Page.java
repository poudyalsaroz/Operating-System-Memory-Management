package cpp_cs1;

public class Page {

	private int pIndex;
	
	private int pageFrameNum;
	
	private boolean status;
	
	public Page(int pIdx, boolean stat) {
		pIndex = pIdx;
		status = stat;
	}
	
	public void setPIndex( int indx) {
		pIndex = indx;
	}
	
	public int getPIndex() {
		return pIndex;
	}
	
	public void setStatus(boolean stat) {
		status = stat;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setPageFrameNumber(int frmNum) {
		pageFrameNum = frmNum;
	}
	
	public int getPageFrameNumber() {
		return pageFrameNum;
	}
	
	
}
