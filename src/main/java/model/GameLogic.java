package main.java.model;

import javafx.scene.paint.Color;
import main.java.EdgeWorker;
import main.java.PairList;
import main.java.controller.GameProcessVisualisationController;
import main.java.object.GameSetting;
import main.java.object.Point;

import java.util.*;
import java.util.concurrent.*;

public class GameLogic {
    private static final Random random = new Random();

    private static HashMap generateUniquePoint(GameProcessVisualisationController controller, int n) {
        HashMap<Integer, Point> pointList = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Point point = generateRandomPoint();
            while (pointList.containsValue(point)) {
                point = generateRandomPoint();
            }
            System.out.println(point);
            controller.drawPoints(point);
            pointList.put(i, point);
        }
        return pointList;
    }

    private static Color generateRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return new Color(r, g, b, 0.9);
    }

    //generate 3 d.p points
    private static Point generateRandomPoint() {
        double x = Math.round(random.nextDouble() * 1000000) / 1000.000;//to 3d.p.
        double y = Math.round(random.nextDouble() * 1000000) / 1000.000;//to 3d.p.

        return new Point(x, y);
    }

    public  void initGame(GameProcessVisualisationController gameProcessVisualisationController, GameSetting gameSetting) throws InterruptedException {
        HashMap<Integer, Point> pointList = new HashMap<>();
        pointList = generateUniquePoint(gameProcessVisualisationController, gameSetting.getNumberOfPoint());
        PairList pairList = new PairList();
        boolean timeout = false;
        ExecutorService executorService = Executors.newFixedThreadPool(gameSetting.getNumberOfThread());

        //create worker thread
        List<EdgeWorker> tasklist = new ArrayList<>();
        List<Color> colorList = new ArrayList<>();
        for (int i = 0; i < gameSetting.getNumberOfThread(); i++) {
            Color color = generateRandomColor();
            while(colorList.contains(color)) {
                color = generateRandomColor();
            }
            colorList.add(color);
            EdgeWorker worker = new EdgeWorker(gameProcessVisualisationController, pointList, pairList, color);
            tasklist.add(worker);
        }


        //run worker thread
        List<Future<EdgeWorker>> resultList = null;
        try {
            resultList = executorService.invokeAll(tasklist, gameSetting.getTimeLimit(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("something wrong..." + e);
        }
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            //wait thread finish
        }

        //check timeout
        List<EdgeWorker> temp = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++){
            Future<EdgeWorker> future = resultList.get(i);
            try{
                EdgeWorker result = future.get();
                temp.add(result);
            }catch (InterruptedException| ExecutionException|CancellationException e){
                timeout = true;
                System.out.println("Time out! Cancel the running thread(s). With " + e);
            }
        }

        //get results through direct refer
        List<EdgeWorker> threadResultList = new ArrayList<>();
        for(int i =0 ; i < tasklist.size() ; i ++){
            threadResultList.add(tasklist.get(i));
        }

        Collections.sort(threadResultList);
        pairList.print();

        //hold for a while before go to result scene
        Thread.sleep(3000);

        if (gameProcessVisualisationController != null) {
            gameProcessVisualisationController.showResult(threadResultList, timeout);
        }

    }
}
