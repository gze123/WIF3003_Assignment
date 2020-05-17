package com.WIF3003_Assignment;

import java.util.Map;
import java.util.concurrent.Callable;

public class MapWorker implements Callable<Map> {

    MapAccess mapAccess;

    public MapWorker(MapAccess mapAccess) {
        this.mapAccess = mapAccess;
    }

    @Override
    public Map call() throws Exception {
        while (MapAccess.getIsRunning() && !MapAccess.getIsInteruppted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted, Timeout");
                MapAccess.setIsInteruppted(true);
                return this.mapAccess.getPairPoint();
//                e.printStackTrace();
            }
            this.mapAccess.createEdge();
        }
        return this.mapAccess.getPairPoint();
    }
}
