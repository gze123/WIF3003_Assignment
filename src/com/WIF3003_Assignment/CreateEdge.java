package com.WIF3003_Assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateEdge implements Runnable {

    private static Random random = new Random();
    Map<Integer, Point> map = new HashMap<Integer, Point>();
    Map<Point, Point> pairPoint = new HashMap<>();

    public CreateEdge(Map map) {
        this.map = map;
    }

    @Override
    public void run() {
        int[] randomNo = generateDifferentNumber(map.size());
        Point point1 = this.map.get(randomNo[0]);
        Point point2 = this.map.get(randomNo[1]);
        int i = 0;
        boolean validPair = true;
        while (validPair) {
            while ((pairPoint.containsKey(point1) || pairPoint.containsKey(point2) ||
                    pairPoint.containsValue(point1) || pairPoint.containsValue(point2)) &&
                    (i < 20)) {
                randomNo = generateDifferentNumber(map.size());
                point1 = (Point) map.get(randomNo[0]);
                point2 = (Point) map.get(randomNo[1]);
                i++;
                if (i == 20) {
                    System.out.println(Thread.currentThread().getName()+"No matched point, cannot form edge");
                    validPair = false;
                    //exit
                }
            }

            if (validPair) {
                System.out.println("[" + point1.toString() + "," + point2.toString() + "]");
                pairPoint.put(point1, point2);
            }
        }
        System.out.println("Program end");

    }

    private static int[] generateDifferentNumber(int size) {
        int randomNo_1 = random.nextInt(size);
        int randomNo_2 = random.nextInt(size);
        while (randomNo_1 == randomNo_2) {
            randomNo_2 = random.nextInt(size);
        }
        int[] randomNo = {randomNo_1, randomNo_2};
        return randomNo;
    }
}
