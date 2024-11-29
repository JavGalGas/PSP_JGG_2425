package org.example.versionA;

import java.util.List;
import java.util.Random;

public class ProductorCapsulas implements Runnable {
    public final List<Capsula> contenedor;
    Random rand = new Random();
    private final String variedadCapsula;
    private final int intensidadCapsula;

    public ProductorCapsulas(List<Capsula> contenedor, String variedad, int intensidad) {
        this.contenedor = contenedor;
        variedadCapsula = variedad;
        intensidadCapsula = intensidad;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try{
                synchronized (contenedor) {
                    int capacidad = 6;
                    while(contenedor.size() >= capacidad) {
                        contenedor.wait();
                    }
                    contenedor.add(new Capsula(variedadCapsula, intensidadCapsula));
                    System.out.println("Hilo Productor: Se ha fabricado una cápsula. Total en contenedor: " + contenedor.size());
                    if (contenedor.size() == capacidad) {
                        contenedor.notify();
                    }
                    int min = 500;
                    int max = 1000;
                    Thread.sleep(rand.nextInt((max - min) + 1) + min);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
