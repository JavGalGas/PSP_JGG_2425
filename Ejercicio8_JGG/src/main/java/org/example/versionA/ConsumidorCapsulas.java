package org.example.versionA;

public class ConsumidorCapsulas implements Runnable {

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            synchronized (Main.contenedor) {
                try {
                    while (Main.contenedor.size() < 6) {
                        Main.contenedor.wait();
                    }
                    System.out.println("Hilo Consumidor: Creando caja con 6 cápsulas");
                    Main.contenedor.subList(0, 6).clear();
                    System.out.println("Hilo Consumidor: Caja creada");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
