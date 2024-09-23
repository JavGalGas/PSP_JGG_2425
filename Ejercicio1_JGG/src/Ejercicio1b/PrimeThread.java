package Ejercicio1b;

import java.util.HashSet;
import java.util.Random;

public class PrimeThread implements Runnable{

    private volatile String name;
    Random random = new Random();
    @Override
    public void run() {
        int num = random.nextInt(1, 100);
        HashSet<Integer> primeList = getPrimeNumbersUntil(num);
        System.out.println(name + ": Mostrando primos hasta el " + num);
        for (int i : primeList) {
            System.out.println(name + ": " + i);
            try {
                Thread.sleep(random.nextLong(500, 1000));
            } catch (InterruptedException exception) {
                System.out.println("Thread has been interrupted.");
            }
        }
    }

    public void start(){
        run();
    }
    public HashSet<Integer> getPrimeNumbersUntil(int num){
        HashSet<Integer> primeNumbers = new HashSet<>();
        for (int i = 1; i <= num; i++){
            if (isPrimeNumber(i)){
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    public Boolean isPrimeNumber(int num){
        int divisors = 0;
        for (int i = 1; i <= num; i++){
            if( num%i == 0)
                divisors++;
        }
        return divisors == 2;
    }

    public void setName(String name) throws IllegalArgumentException{
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty");
        this.name = name;
    }

}
