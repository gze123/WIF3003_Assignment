package com.WIF3003_Assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapAccess {

    private final static Random random = new Random();
    private final static Map<Point, Point> pairPoint = new HashMap<>();
    private final Map<Integer, Point> map;

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

    //pair-point
    public void createEdge() {
        synchronized (pairPoint) {
            int[] randomNo = generateDifferentNumber(map.size());
            Point point1 = this.map.get(randomNo[0]);
            Point point2 = this.map.get(randomNo[1]);
            int i = 0;
            boolean validPair = true;
            boolean endProgram = false;
            while ((pairPoint.containsKey(point1) || pairPoint.containsKey(point2) ||
                    pairPoint.containsValue(point1) || pairPoint.containsValue(point2)) &&
                    (i < 20)) {
                    randomNo = generateDifferentNumber(map.size());
                    point1 = map.get(randomNo[0]);
                    point2 = map.get(randomNo[1]);
                    i++;
                    if (i == 20) {
                        System.out.println(Thread.currentThread().getName()+" - No matched point, cannot form edge");
                        validPair = false;
                        //exit
                        endProgram = true;
                    }
                }

            if (validPair) {
                pairPoint.put(point1, point2);
            }

            if (endProgram) {
                System.out.println(Thread.currentThread().getName()+" - "+"Failed to form a single edge after 20 attempts.");
            }
            System.out.println(Thread.currentThread().getName()+" - "+pairPoint.toString());
        }
    }
}
