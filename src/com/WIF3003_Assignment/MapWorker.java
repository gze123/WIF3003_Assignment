package com.WIF3003_Assignment;

public class MapWorker implements  Runnable {

    MapAccess mapAccess;

    public MapWorker(MapAccess mapAccess) {
        this.mapAccess = mapAccess;
    }


    @Override
    public void run() {
        while (MapAccess.getIsRunning() && mapAccess.checkRunTime()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                MapAccess.setIsInteruppted(true);
                System.out.println("Thread was interrupted, Failed to complete operation");
                e.printStackTrace();
            }
            mapAccess.createEdge();
        }
    }
}
