package main.java;

import javafx.scene.paint.Color;
import main.java.controller.GameProcessVisualisationController;
import main.java.object.Point;
import main.java.object.ThreadResult;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;

public class MapWorker implements Callable<ThreadResult> {

    MapAccess mapAccess;
    HashMap<Integer, Point> map;
    String anyholding = "nothing";
    Random random = new Random();
    private int numberOfEdgeFormed = 0;
    private int attempt = 0;
    private String result;
    Color color;
    private ThreadResult threadResult;
    GameProcessVisualisationController controller;
    public MapWorker(GameProcessVisualisationController controller,HashMap<Integer, Point> map, MapAccess mapAccess, Color color) {
        this.controller = controller;
        this.map = map;
        this.mapAccess = mapAccess;
        this.color = color;
    }

    public String getResult() {
        return result;
    }

    public int getNumberOfEdgeFormed() {
        return numberOfEdgeFormed;
    }


    public ThreadResult call() throws Exception {
        ThreadResult threadResult = new ThreadResult();
        threadResult.setName(Thread.currentThread().getName());
        Point point_1 = null;
        Point point_2 = null;
        int num1 = 0;
        int num2 = 0;

        try {
            while (mapAccess.getIsRunning() &&
                    (this.map.get(num1).isSelected() || map.get(num2).isSelected() || (point_1 == null && point_2 == null))
                    && attempt <= 20) {
                Thread.sleep(200);
                //get two random point, if already lock one point hold it to avoid release a isSelected point
                if (point_1 == null) {
                    num1 = random.nextInt(map.size());
                }
                if (point_2 == null) {
                    num2 = random.nextInt(map.size());
                }

                //lock and get the two points
                if (point_1 == null && map.get(num1).lockPoint()) {
                    point_1 = map.get(num1);
                }

                if (point_2 == null && map.get(num2).lockPoint()) {
                    point_2 = map.get(num2);
                }

                if ( (point_1 != null && point_2 != null) && this.mapAccess.getIsRunning() ) {
                    //form edge with the two locked point in this thread
                    this.mapAccess.addCreatedEdge(point_1, point_2);
                    controller.drawLine(point_1,point_2, this.color);

                    numberOfEdgeFormed++;
                    //release after create edge
//                    System.out.println(Thread.currentThread().getName() + " Created edge and release: " + point_1 + point_2);
                    point_1 = null;
                    point_2 = null;
                } else {
                    attempt++;
                }

            }

        } catch (InterruptedException e) {
            System.out.println("Time out! Number of edges created by " + Thread.currentThread().getName() + " is " + numberOfEdgeFormed);
        }

        if (attempt > 20) {
            this.mapAccess.setIsRunning(false);
            if (point_1 != null) {
                anyholding = point_1.toString();
                if (point_2 != null){
                    anyholding += point_2.toString();
                }
            }
            if (point_2 != null) {
                anyholding = point_2.toString();
            }
            result = "More than 20 attempt, " + Thread.currentThread().getName() + " has stop and holding " + anyholding + ". Number of edges by this thread is " + numberOfEdgeFormed
                    + ".\nStop other thread...";
            threadResult.setNumberOfEdgeCreated(numberOfEdgeFormed);
            threadResult.setNumberOfFailure(attempt-1);
            return threadResult;
        } else {
            if (point_1 != null) {
                anyholding = point_1.toString();
                if (point_2 != null){
                    anyholding += point_2.toString();
                }
            }
            if (point_2 != null) {
                anyholding = point_2.toString();
            }
            result = "The number of edges created by " + Thread.currentThread().getName() + " is " + numberOfEdgeFormed + ", with " + attempt + " attempt(s)";
            threadResult.setNumberOfEdgeCreated(numberOfEdgeFormed);
            threadResult.setNumberOfFailure(attempt);
            return threadResult;
        }
    }

}
