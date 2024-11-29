package org.example.versionB;

import org.example.versionA.Capsula;
import org.example.versionA.ProductorCapsulas;

import java.util.List;
import java.util.Random;

public class ProductorCapsulas_B implements Runnable {
    Random rand = new Random();
    public final List<Capsula> contenedor;
    private final String variedadCapsula;
    private final int intensidadCapsula;

    public ProductorCapsulas_B(List<Capsula> contenedor, String variedad, int intensidad) {
        this.contenedor = contenedor;
        variedadCapsula = variedad;
        intensidadCapsula = intensidad;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try{
                int min = 500;
                int max = 1000;
                Thread.sleep(rand.nextInt((max - min) + 1) + min);
                contenedor.add(new Capsula(variedadCapsula, intensidadCapsula));
                System.out.println("Hilo Productor: Se ha fabricado una cápsula. Total en contenedor: " + contenedor.size());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
