package com.WIF3003_Assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapAccess {

    private final static Random random = new Random();
    private final static Map<Point, Point> pairPoint = new HashMap<>();
    private final Map<Integer, Point> map;
    private static boolean running = true;
    private static boolean interrupted = false;
    private static boolean endProgram = false;
    private final long endTime;

    public MapAccess(Map map, long endTime) {
        this.map = map;
        this.endTime = endTime;
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

    public static synchronized void setIsRunning(boolean isRunning) {
        running = isRunning;
    }

    public static synchronized boolean getIsRunning() {
        return running;
    }

    public static synchronized boolean getIsInteruppted() {
        return interrupted;
    }

    public static synchronized void setIsInteruppted(boolean isInteruppted) {
        interrupted = isInteruppted;
    }

    //pair-point
    public void createEdge() {
        synchronized (pairPoint) {
            if(!interrupted && checkRunTime()) {
                int[] randomNo = generateDifferentNumber(map.size());
                Point point1 = this.map.get(randomNo[0]);
                Point point2 = this.map.get(randomNo[1]);
                int i = 0;
                //check if the point is selected into the map before
                while ((point1.isSelected() || point2.isSelected() || point1.equals(point2)) && (i < 20) && getIsRunning()) {
                    if (point1.isSelected()) {
                        point1 = this.map.get(random.nextInt(this.map.size()));
                    }
                    if (point2.isSelected() || point1.equals(point2)) {
                        point2 = this.map.get(random.nextInt(this.map.size()));
                    }
                    i++;
                    if (i == 20 ) {
                        System.out.println(Thread.currentThread().getName() + " - No matched point, cannot form edge");
                        System.out.println(Thread.currentThread().getName() + " - Failed to form a single edge after 20 attempts.");
                        //exit
                        setIsRunning(false);
                        return;
                    }
                }

                if (!getIsRunning()) {
                    System.out.println(Thread.currentThread().getName() + " - " + "One of the thread failed to form a single edge after 20 attempts.");
                    return;
                }
                //if valid pair, put them into map
                pairPoint.put(point1, point2);
                point1.setSelected(true);
                point2.setSelected(true);
                setIsRunning(true);
            }
        }
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

    public boolean checkRunTime() {
        if (System.currentTimeMillis() > this.endTime) {
            if(!endProgram) {
                System.out.println("Timeout! Program is forced to end.");
                endProgram = true;
            }
            return false;
        } else return true;
    }
}
