package org.example.versionB;

import org.example.versionA.Capsula;

import java.util.List;
import java.util.Random;


public class ConsumidorCapsulas_B implements Runnable {

    Random rand = new Random();
    public final List<Capsula> contenedor;

    public ConsumidorCapsulas_B(List<Capsula> contenedor) {
        this.contenedor = contenedor;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try {
                synchronized (contenedor) {
                    while (contenedor.size() < 6) {
                        contenedor.wait();
                    }
                    System.out.println("Hilo Consumidor: Creando caja con 6 cápsulas");
                    contenedor.subList(0, 6).clear();
                    contenedor.notify();
                }
                int min = 1000;
                int max = 3000;
                Thread.sleep(rand.nextInt((max - min) + 1) + min);
                System.out.println("Hilo Consumidor: Caja creada");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
