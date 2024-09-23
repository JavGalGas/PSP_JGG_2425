package Ejercicio1b;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce el número de hilos a crear:");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++){
            PrimeThread h = new PrimeThread();
            h.setName("Hilo " + (i+1));
            h.start();
        }

    }


}