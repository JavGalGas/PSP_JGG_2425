package com.jgg.Unit4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese cada cuántos segundos quieres que se guarde el saludo: ");
        int frequency = scanner.nextInt();
        ThreadWriter writer = new ThreadWriter(frequency);
        Thread thread = new Thread(writer);
        System.out.println("Pulsa enter para interrrumpir el hilo: ");
        thread.start();
        scanner.nextLine();
        scanner.nextLine();
        thread.interrupt();
        System.out.println("Hilo interrumpido.");
        try {
            thread.join();
            System.out.println("Adiós");
        } catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
    }
}