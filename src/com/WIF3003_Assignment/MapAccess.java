package com.WIF3003_Assignment;

import com.WIF3003_Assignment.object.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapAccess {

    private final static Random random = new Random();
    private static final boolean running = true;
    private static final boolean endProgram = false;
    Map<Point, Point> pairPoint = new HashMap<>();
    private static boolean interrupted = false;
    Map<Integer, Point> map = new HashMap<>();

    public MapAccess() {
    }

    //generate random number
    private static int[] generateDifferentNumber(int size) {
        int randomNo_1 = random.nextInt(size);
        int randomNo_2 = random.nextInt(size);
        while (randomNo_1 == randomNo_2) {
            randomNo_2 = random.nextInt(size);
        }
        int[] randomNo = {randomNo_1, randomNo_2};
        return randomNo;
    }

    public boolean getIsRunning() {
        synchronized (this) {
            return running;
        }
    }

    public void setIsRunning(boolean isRunning) {
        synchronized (this) {
            isRunning = running;
        }
    }

    public static synchronized boolean getIsInteruppted() {
        return interrupted;
    }

    public static synchronized void setIsInteruppted(boolean isInteruppted) {
        interrupted = isInteruppted;
    }

    public void releaseLock(Point point1) {
        if (point1 != null) {
            point1.setSelected(false);
        }
    }

    public synchronized void addCreatedEdge(Point point1, Point point2) {
            pairPoint.put(point1, point2);
    }

    public synchronized Map getPairPoint() {
        return pairPoint;
    }

    public synchronized ArrayList getUnpairPoint() {
        ArrayList<Point> pointArrayList = new ArrayList<>();
        for (int i =0; i < map.size(); i++) {
            if(!map.get(i).isSelected()) {
                pointArrayList.add(map.get(i));
            }
        }
        return pointArrayList;
    }
}
