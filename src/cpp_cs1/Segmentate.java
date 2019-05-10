/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpp_cs1;

/**
 *
 * @author cayce
 */
public class Segmentate {
    private int startVal;
    private int endVal;
    private int segSize;
    private int arrivalTime;
    private int lifeTime;
    private int id;
    private int numOfSegs;
    public Segmentate(int number, int size, int start, int end, int arrTime, int lifTime,int segs){
        id = number;
        segSize = size;
        startVal = start;
        endVal = end;
        arrivalTime = arrTime;
	lifeTime = arrivalTime + lifTime;
	numOfSegs = segs;
    }
    public Segmentate() {
        
    }
    public  void setSize(int size){
        this.segSize = size;
    }
    private int getSize(){
        return segSize;
    }
    public void setBeginValue(int beginNum){
        this.startVal = beginNum;
    }
    public void setEndValue(int endNum){
        this.endVal = endNum;
    }
    public int getBeginValue(){
        return startVal;
    }
    public int getEndValue(){
        return endVal;
    }
    public int getNumOfSegs(){
        return numOfSegs;
    }
    public int getId(){
        return id;
    }
    public int getLifeTime(){
        return lifeTime;
    }
    
}
