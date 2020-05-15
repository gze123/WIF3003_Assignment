package com.WIF3003_Assignment;

import java.util.Random;

public class CreateEdge implements Runnable {

    private static final Random random = new Random();
    MapAccess mapAccess;

    public CreateEdge(MapAccess mapAccess) {
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
