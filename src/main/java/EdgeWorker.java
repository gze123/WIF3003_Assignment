package main.java;

import javafx.scene.paint.Color;
import main.java.controller.GameProcessVisualisationController;
import main.java.object.Point;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;

public class EdgeWorker implements Callable<EdgeWorker>,Comparable<EdgeWorker> {
    PairList pairList;
    HashMap<Integer, Point> pointList;

    private String result;
    String anyholding = "nothing";

    private int numberOfEdgeFormed = 0;
    private int attempt = 0;
    private String name ;
    Color color;

    GameProcessVisualisationController controller;

    Random random = new Random();

    public EdgeWorker(GameProcessVisualisationController controller, HashMap<Integer, Point> pointList, PairList pairList, Color color) {
        this.controller = controller;
        this.pointList = pointList;
        this.pairList = pairList;
        this.color = color;
    }

    public String getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttempt() {
        return attempt;
    }

    public int getNumberOfEdgeFormed() {
        return numberOfEdgeFormed;
    }

    public String toString() {
        return "ThreadResult{" +
                "name='" + name + '\'' +
                ", numberOfEdgeCreated=" + numberOfEdgeFormed +
                ", numberOfFailure=" + attempt +
                '}';
    }

    public int compareTo(EdgeWorker o) {
        return o.numberOfEdgeFormed - this.numberOfEdgeFormed;
    }

    public EdgeWorker call() throws Exception {
        setName(Thread.currentThread().getName());
        Point point_1 = null;
        Point point_2 = null;
        int num1 = 0;
        int num2 = 0;

        try {
            while (pairList.getIsRunning() &&
                    (pointList.get(num1).isSelected() || pointList.get(num2).isSelected() || (point_1 == null && point_2 == null))
                    && attempt < 20) {

                //get two random point, if already lock one point hold it to avoid release a isSelected point
                if (point_1 == null) {
                    num1 = random.nextInt(pointList.size());
                }
                if (point_2 == null) {
                    num2 = random.nextInt(pointList.size());
                }

                //lock and get the two points
                if (point_1 == null && pointList.get(num1).lockPoint()) {
                    point_1 = pointList.get(num1);
                }

                if (point_2 == null && pointList.get(num2).lockPoint()) {
                    point_2 = pointList.get(num2);
                }

                if ((point_1 != null && point_2 != null) && pairList.getIsRunning()) {
                    //form edge with the two locked point in this thread
                    pairList.createEdge(point_1, point_2);
                    controller.drawLine(point_1, point_2, color);
                    numberOfEdgeFormed++;
                    Thread.sleep(1000);//wait the line to draw

                    //release after create edge
                    System.out.println(Thread.currentThread().getName() + " Created edge and release: " + point_1 + point_2);
                    point_1 = null;
                    point_2 = null;
                } else {
                    attempt++;
                }

            }

        } catch (InterruptedException e) {
            System.out.println("Time out! Number of edges created by " + Thread.currentThread().getName() + " is " + numberOfEdgeFormed);
        }

        if (attempt >= 20) {
            pairList.setIsRunning(false);
            if (point_1 != null) {
                anyholding = point_1.toString();
                if (point_2 != null) {
                    anyholding += point_2.toString();
                }
            }
            if (point_2 != null) {
                anyholding = point_2.toString();
            }
            result = "Reached 20 attempt, " + Thread.currentThread().getName() + " has stop and holding " + anyholding + ". Number of edges by this thread is " + numberOfEdgeFormed
                    + ".\nStop other thread...";
            System.out.println(result);

            return this;
        } else {
            if (point_1 != null) {
                anyholding = point_1.toString();
                if (point_2 != null) {
                    anyholding += point_2.toString();
                }
            }
            if (point_2 != null) {
                anyholding = point_2.toString();
            }
            result = "The number of edges created by " + Thread.currentThread().getName() + " is " + numberOfEdgeFormed + ", with " + attempt + " failed attempt(s)";
            System.out.println(result);
            return this;
        }
    }

}
