package com.WIF3003_Assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static Random random = new Random();

    public static void main(String[] args) {
        Map<Integer, Point> map = new HashMap<Integer, Point>();
        Map<Point, Point> pairPoint = new HashMap<>();
        Map<Point, Point> createEdge = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of points you want to create, n: ");
        int n = scanner.nextInt();
        System.out.print("Enter number of thread you want to launch (value must be lesser or equal to n), t: ");
        int t = scanner.nextInt();
        System.out.print("Enter the time you want the program to run, m (in second): ");
        int m = scanner.nextInt();
        generateUniquePoint(n, map);
        System.out.println(map.toString());
//        createEdge(map, pairPoint);
//        System.out.println(pairPoint.toString());
//        System.out.println(pairPoint.size());
        ExecutorService executorService = Executors.newFixedThreadPool(t);
        CreateEdge c1 = new CreateEdge(map);
        Thread thread = new Thread(c1);
        executorService.execute(thread);
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

    }

//    private static Map createEdge(Map map, Map pairPoint) {
//        int MAP_SIZE = map.size();
//        int i = 0;
//        while (MAP_SIZE > i) {
//            pairPoint = pairPoint(map, pairPoint);
//            i++;
//        }
//        return pairPoint;
//    }
//
//    private static Map pairPoint(Map map, Map pairPoint) {
//        int[] randomNo = generateDifferentNumber(map.size());
//        Point point1 = (Point) map.get(randomNo[0]);
//        Point point2 = (Point) map.get(randomNo[1]);
//        int i = 0;
//        boolean validPair = true;
//        while ((pairPoint.containsKey(point1) || pairPoint.containsKey(point2) ||
//                pairPoint.containsValue(point1) || pairPoint.containsValue(point2)) &&
//                (i<20) ) {
//            randomNo = generateDifferentNumber(map.size());
//            point1 = (Point) map.get(randomNo[0]);
//            point2 = (Point) map.get(randomNo[1]);
//            i++;
//            if(i == 20){
//                System.out.println("No matched point, cannot form edge");
//                validPair = false;
//                //exit
//            }
//        }
//
//        if(validPair) {
//            System.out.println("["+point1.toString() +","+point2.toString()+"]");
//            pairPoint.put(point1, point2);
//        }
//        return pairPoint;
//    }
//
//    private static int[] generateDifferentNumber(int size) {
//        int randomNo_1 = random.nextInt(size);
//        int randomNo_2 = random.nextInt(size);
//        while (randomNo_1 == randomNo_2) {
//            randomNo_2 = random.nextInt(size);
//        }
//        int[] randomNo = {randomNo_1, randomNo_2};
//        return randomNo;
//    }

    private static void generateUniquePoint(int n, Map map) {
        for (int i = 0; i < n; i++) {
            Point point = generateRandomPoint();
            while (map.containsKey(point)) {
                point = generateRandomPoint();
            }
            map.put(i, point);
        }
    }

    private static Point generateRandomPoint() {
        double x = random.nextDouble() * 1000;
        double y = random.nextDouble() * 1000;
        return new Point(x, y);
    }
}
