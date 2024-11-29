package org.example.versionB;

import org.example.versionA.Capsula;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static volatile List<Capsula> contenedor2 = new ArrayList<>();

    public static void main(String[] args) {
        ProductorCapsulas_B productorCapsulas = new ProductorCapsulas_B(contenedor2, "Latte", 2);
        ConsumidorCapsulas_B consumidorCapsulas = new ConsumidorCapsulas_B(contenedor2);
        Thread thread = new Thread(productorCapsulas);
        Thread thread2 = new Thread(consumidorCapsulas);
        thread.start();
        thread2.start();
    }
}