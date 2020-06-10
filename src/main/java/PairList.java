package main.java;

import main.java.object.Point;
import java.util.HashMap;
import java.util.Map;

public class PairList {

    Map<Point, Point> pairList = new HashMap<>();
    private boolean isRunning = true;

    public boolean getIsRunning() {
        synchronized (this){
            return isRunning;
        }
    }

    public void setIsRunning(boolean running) {
        synchronized (this){
            isRunning = running;
        }
    }

    //two selected point add to PairList
    public synchronized void createEdge(Point p1,Point p2){
        this.pairList.put(p1,p2);
    }

    public void print(){
        for (Point key : pairList.keySet()) {
            System.out.println("x=" + key + " , y=" + pairList.get(key));
        }
    }

}
