package org.example.versionA;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static volatile List<Capsula> contenedor = new ArrayList<>();

    public static void main(String[] args) {
        ProductorCapsulas productorCapsulas = new ProductorCapsulas("Arábica", 8);
        ConsumidorCapsulas consumidorCapsulas = new ConsumidorCapsulas();
        Thread thread = new Thread(productorCapsulas);
        Thread thread2 = new Thread(consumidorCapsulas);
        thread.start();
        thread2.start();
    }
}