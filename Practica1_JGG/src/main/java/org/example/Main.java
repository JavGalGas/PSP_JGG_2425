package org.example;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        int posicion = 1;
        Carril[] carriles = new Carril[4];
        for (int i = 0; i < 4; i++) {
            carriles[i] = new Carril(i, 4);
        }

        for (Carril carril : carriles) {
            executor.submit(() -> {
                ExecutorService corredoresExecutor = Executors.newFixedThreadPool(4);
                for (int i = 1; i <= 4; i++) {
                    Corredor corredor = new Corredor(i, carril);
                    carril.anadirCorredor(corredor, (i-1) * 100000);
                    if (i == 1) corredor.recibirTestigo();
                    corredoresExecutor.submit(corredor);
                }
                corredoresExecutor.shutdown();
                try {
                    corredoresExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
                    System.out.println("PODIUM: " + posicion + "º para el equipo de carril");
                    System.out.println("Carril " + carril.getId() + " ha finalizado la carrera.");
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            });
        }

        executor.shutdown();
    }
}