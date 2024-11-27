package org.example.versionB;

import org.example.versionA.Capsula;

import java.util.Random;

public class ProductorCapsulas_B implements Runnable {
    Random rand = new Random();
    private int min = 500;
    private int max = 1000;
    private String variedadCapsula;
    private int intensidadCapsula;

    public ProductorCapsulas_B(String variedad, int intensidad) {
        variedadCapsula = variedad;
        intensidadCapsula = intensidad;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try{
                synchronized(Main.contenedor2) {
                    Main.contenedor2.add(new Capsula(variedadCapsula, intensidadCapsula));
                    int contenedorSize = Main.contenedor2.size();
                    System.out.println("Hilo Productor: Se ha fabricado una cápsula. Total en contenedor: " + contenedorSize);
                    if (contenedorSize >= 6) {
                        Main.contenedor2.notify();
                    }
                }
                Thread.sleep(rand.nextInt((max - min) + 1) + min);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
