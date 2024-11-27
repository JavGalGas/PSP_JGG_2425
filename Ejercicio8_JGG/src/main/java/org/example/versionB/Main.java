package org.example.versionB;

import org.example.versionA.Capsula;
import org.example.versionA.ConsumidorCapsulas;
import org.example.versionA.ProductorCapsulas;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static volatile List<Capsula> contenedor2 = new ArrayList<>();

    public static void main(String[] args) {
        ProductorCapsulas_B productorCapsulas = new ProductorCapsulas_B("Latte", 2);
        ConsumidorCapsulas_B consumidorCapsulas = new ConsumidorCapsulas_B();
        Thread thread = new Thread(productorCapsulas);
        Thread thread2 = new Thread(consumidorCapsulas);
        thread.start();
        thread2.start();
    }
}