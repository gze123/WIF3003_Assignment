package com.WIF3003_Assignment;

public class MapWorker implements Runnable {

    MapAccess mapAccess;

    public MapWorker(MapAccess mapAccess) {
        this.mapAccess = mapAccess;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            mapAccess.createEdge();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
