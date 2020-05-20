package com.WIF3003_Assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Main {

    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        Map<Integer, Point> map = new HashMap<Integer, Point>();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of points you want to create, n: ");
        int n = scanner.nextInt();
        System.out.print("Enter number of thread you want to launch (value must be lesser or equal to n), t: ");
        int t = scanner.nextInt();
        System.out.print("Enter the time you want the program to run, m (in second): ");
        long m = scanner.nextLong();
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + (m*1000);
        map = generateUniquePoint(n);
        MapAccess mapAccess = new MapAccess(map, endTime);
        MapWorker mapWorker = new MapWorker(mapAccess);
        ExecutorService executorService = Executors.newFixedThreadPool(t);
        for (int i = 0; i < t; i++) {
            executorService.execute(mapWorker);
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        System.out.println("Final result: " + mapAccess.getPairPoint());
        System.out.println(mapAccess.getPairPoint().size());
        System.out.println("Fail to pair point: " + mapAccess.getUnpairPoint());
        System.out.println(mapAccess.getUnpairPoint().size());

    }

    private static Map generateUniquePoint(int n) {
        Map<Integer, Point> map = new HashMap<Integer, Point>();
        for (int i = 0; i < n; i++) {
            Point point = generateRandomPoint();
            while (map.containsValue(point)) {
                point = generateRandomPoint();
            }
            map.put(i, point);
        }
        return map;
    }

    private static Point generateRandomPoint() {
        double x = random.nextDouble() * 1000;
        double y = random.nextDouble() * 1000;
        return new Point(x, y);
    }
}
