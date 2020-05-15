package com.WIF3003_Assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapAccess implements Runnable {

    private final static Random random = new Random();
    private final static Map<Point, Point> pairPoint = new HashMap<>();
    private final Map<Integer, Point> map;
    private static boolean running = true;

    public MapAccess(Map map) {
        this.map = map;
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

    public static synchronized boolean getIsRunnning() {
        return running;
    }

    //pair-point
    public boolean createEdge() {
        synchronized (pairPoint) {
            int[] randomNo = generateDifferentNumber(map.size());
            Point point1 = this.map.get(randomNo[0]);
            Point point2 = this.map.get(randomNo[1]);
            int i = 0;
            while (((pairPoint.containsKey(point1) || pairPoint.containsKey(point2) ||
                    pairPoint.containsValue(point1) || pairPoint.containsValue(point2)) &&
                    (i < 20))) {
                randomNo = generateDifferentNumber(map.size());
                point1 = map.get(randomNo[0]);
                point2 = map.get(randomNo[1]);
                i++;
                if (i == 20) {
                    System.out.println(Thread.currentThread().getName() + " - No matched point, cannot form edge");
                    System.out.println(Thread.currentThread().getName() + " - " + " Failed to form a single edge after 20 attempts.");
                    //exit
                    setIsRunning(false);
                    return false;
                }
                if (!getIsRunnning()) {
                    System.out.println(Thread.currentThread().getName() + " - " + "One of the thread failed to form a single edge after 20 attempts.");
                    return false;
                }
            }

            pairPoint.put(point1, point2);

            System.out.println(Thread.currentThread().getName() + " - " + pairPoint.toString());
            System.out.println(pairPoint.size());
            setIsRunning(true);
            return true;
        }
    }

    @Override
    public void run() {
        while (getIsRunnning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, Failed to complete operation");
                e.printStackTrace();
            }
            createEdge();
        }
    }
}
