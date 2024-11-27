package org.example.versionB;

import java.util.Random;


public class ConsumidorCapsulas_B implements Runnable {

    Random rand = new Random();
    private int min = 1000;
    private int max = 3000;

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            synchronized (Main.contenedor2) {
                try {
                    while (Main.contenedor2.size() < 6) {
                        Main.contenedor2.wait();
                    }
                    System.out.println("Hilo Consumidor: Creando caja con 6 cápsulas");
                    Thread.sleep(rand.nextInt((max - min) + 1) + min);
                    Main.contenedor2.subList(0, 6).clear();
                    System.out.println("Hilo Consumidor: Caja creada");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
