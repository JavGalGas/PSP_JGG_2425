package org.example.versionA;

import java.util.List;

public class ConsumidorCapsulas implements Runnable {

    public final List<Capsula> contenedor;

    public ConsumidorCapsulas(List<Capsula> contenedor) {
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
                    System.out.println("Hilo Consumidor: Caja creada");
                    contenedor.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
