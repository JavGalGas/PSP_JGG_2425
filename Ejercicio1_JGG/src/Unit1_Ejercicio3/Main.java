package Unit1_Ejercicio3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el número de hilos a crear:");
        int numThreads = scanner.nextInt();
        scanner.close();
        Exercise3ThreadLauncher threadLauncher = new Exercise3ThreadLauncher();
        threadLauncher.launch(numThreads);
    }
}