package org.example.versionA;

import java.util.Random;

public class ProductorCapsulas implements Runnable {
    Random rand = new Random();
    private int min = 500;
    private int max = 1000;
    private String variedadCapsula;
    private int intensidadCapsula;

    public ProductorCapsulas(String variedad, int intensidad) {
        variedadCapsula = variedad;
        intensidadCapsula = intensidad;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try{
                synchronized(Main.contenedor) {
                    Main.contenedor.add(new Capsula(variedadCapsula, intensidadCapsula));
                    int contenedorSize = Main.contenedor.size();
                    System.out.println("Hilo Productor: Se ha fabricado una cápsula. Total en contenedor: " + contenedorSize);
                    if (contenedorSize >= 6) {
                        Main.contenedor.notify();
                    }
                }
                Thread.sleep(rand.nextInt((max - min) + 1) + min);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
