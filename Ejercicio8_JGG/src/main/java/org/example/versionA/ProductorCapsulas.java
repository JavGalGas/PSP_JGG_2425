package org.example.versionA;

import java.util.Random;

public class ProductorCapsulas implements Runnable {
    Random rand = new Random();
    int min = 500;
    int max = 1000;
    @Override
    public void run() {
        try{
            Capsula capsula = new Capsula("Latte", 2);
            Thread.sleep(rand.nextInt((max - min) + 1) + min);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
