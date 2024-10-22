package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int limit = random.nextInt(10,20);
        SecondCounter secondCounter = new SecondCounter(limit);
        Scanner scanner = new Scanner(System.in);
        Thread thread = new Thread(secondCounter);
        System.out.println("Pulsa enter cuando creas que el contador ha llegado a " + limit);
        thread.start();
        scanner.nextLine();
        secondCounter.Stop();
        try{
            thread.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        System.out.println(secondCounter.getResult());
    }
}