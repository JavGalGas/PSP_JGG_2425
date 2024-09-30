package Unit1_Ejercicio1;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Introduce n1");
        int n1 = sc.nextInt();
        System.out.println("Introduce n2");
        int n2 = sc.nextInt();
        sc.close();
        Thread hilo1 = new Thread() {
            @Override
            public void run() {
                for(int i = n1; i < n2; i++) {
                    System.out.println(i);
                    try{
                        Thread.sleep(random.nextLong(1,1000));
                    }
                    catch (InterruptedException exception){
                        System.out.println("Thread has been interrupted.");
                    }
                }
            }
        };
        hilo1.start();

    }
}