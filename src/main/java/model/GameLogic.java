package main.java.model;

import main.java.MapAccess;
import main.java.MapWorker;
import main.java.controller.GameProcessVisualisationController;
import main.java.object.GameSetting;
import main.java.object.Point;

import java.util.*;
import java.util.concurrent.*;

public class GameLogic{
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
//        HashMap<Integer, Point> map = new HashMap<Integer, Point>();
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter number of points you want to create, n: ");
//        int n = scanner.nextInt();
//        System.out.print("Enter number of thread you want to launch (value must be lesser or equal to n), t: ");
//        int t = scanner.nextInt();
//        System.out.print("Enter the time you want the program to run, m (in second): ");
//        int m = scanner.nextInt();
//        GameSetting gameSetting = new GameSetting(n, t, m);
//        GameLogic gameLogic = new GameLogic();
//        gameLogic.initGame(null, gameSetting);
    }

    public void initGame(GameProcessVisualisationController gameProcessVisualisationController, GameSetting gameSetting) {
        HashMap<Integer, Point> map = new HashMap<Integer, Point>();
        map = generateUniquePoint(gameProcessVisualisationController,gameSetting.getNumberOfPoint());
        MapAccess mapAccess = new MapAccess();
        ExecutorService executorService = Executors.newFixedThreadPool(gameSetting.getNumberOfThread());
        //create worker thread
        List<MapWorker> tasklist = new ArrayList<>();
        for (int i = 0 ; i < gameSetting.getNumberOfThread(); i++){
            MapWorker worker = new MapWorker(gameProcessVisualisationController,map,mapAccess);
            tasklist.add(worker);
        }
        //run worker thread
        List<Future<MapWorker>> resultlist = null;
        try {
            resultlist = executorService.invokeAll(tasklist, gameSetting.getTimeLimit(), TimeUnit.SECONDS);
        }catch (InterruptedException e) {
            System.out.println("something wrong..." + e);
        }
        executorService.shutdown();

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        for (int i = 0; i < resultlist.size() ; i++){
            Future<MapWorker> future = resultlist.get(i);
            try {
                String result = future.get().getResult();
                System.out.println(result);
            }catch(InterruptedException | ExecutionException | CancellationException e){
                System.out.println("Time out! Cancel the running thread(s). With " + e);
            }
        }

        System.out.println("Final result (successful pair: "+mapAccess.getPairPoint().size()+"): " + mapAccess.getPairPoint());
        System.out.println("Fail to pair point ("+mapAccess.getUnpairPoint().size()+" points): " + mapAccess.getUnpairPoint());
    }

    private static HashMap generateUniquePoint(GameProcessVisualisationController controller,int n) {
        HashMap<Integer, Point> map = new HashMap<Integer, Point>();
        for (int i = 0; i < n; i++) {
            Point point = generateRandomPoint();
            while (map.containsValue(point)) {
                point = generateRandomPoint();
            }
            System.out.println(point);
            controller.drawPoints(point);
            map.put(i, point);
        }

        return map;
    }

    //generate 3 d.p points
    private static Point generateRandomPoint() {
        double x = Math.round(random.nextDouble() * 1000000) / 1000.000;//to 3d.p.
        double y = Math.round(random.nextDouble() * 1000000) / 1000.000;//to 3d.p.

        return new Point(x, y);
    }
}
