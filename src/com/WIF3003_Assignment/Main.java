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
        int m = scanner.nextInt();
        map = generateUniquePoint(n);
        ExecutorService executorService = Executors.newFixedThreadPool(t/4);

        Runnable[] runnable = new MapAccess[t];
        for (int i = 0; i < t; i++) {
            runnable[i] = new MapAccess(map);
        }

        Thread[] thread = new Thread[t];
        for (int i = 0; i < t; i++) {
            thread[i] = new Thread(runnable[i]);
        }

        for (int i = 0; i < t; i++) {
            thread[i].start();
        }


//        executorService.shutdown();
//        while (!executorService.isTerminated()) {
//        }

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
