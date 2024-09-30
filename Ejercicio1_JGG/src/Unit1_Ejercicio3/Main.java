package Unit1_Ejercicio3;

import Unit1_Ejercicio2.ThreadLauncher;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el número de hilos a crear:");
        int numThreads = scanner.nextInt();
        scanner.close();
        ThreadLauncher threadLauncher = new ThreadLauncher();
        threadLauncher.launch(numThreads);
    }
}