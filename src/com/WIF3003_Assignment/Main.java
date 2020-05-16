package com.WIF3003_Assignment;

import java.util.*;
import java.util.concurrent.*;

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
        ExecutorService executorService = Executors.newFixedThreadPool(t);

//        FutureTask[] runTask = new FutureTask[t];
        Set<Callable<Map>> callables = new HashSet<>();
        for (int i=0; i<t; i++) {
            Callable callable = new MapWorker(new MapAccess(map));
//            runTask[i] = new FutureTask(callable);
            callables.add(callable);
//            Thread runThread = new Thread(runTask[i]);
//            runThread.start();
        }
        List<Future<Map>> futures = executorService.invokeAll(callables, m, TimeUnit.SECONDS);
        for (Future<Map> future: futures) {
            System.out.println(future.get());
        }

//
//        executorService.invokeAll(runTask,m,TimeUnit.SECONDS);
//        for (int i=0; i<t; i++) {
//            System.out.println(runTask[1].get());
//        }

//        Thread[] thread = new Thread[t];
//        for (int i = 0; i < t; i++) {
//            thread[i] = new Thread(new MapAccess(map));
//        }
//
//        for (int i = 0; i < t; i++) {
//            thread[i].start();
//        }



        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

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
