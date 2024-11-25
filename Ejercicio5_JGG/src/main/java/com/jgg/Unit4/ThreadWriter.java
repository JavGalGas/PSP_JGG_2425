package com.jgg.Unit4;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class ThreadWriter implements Runnable {
    int frequency;

    public ThreadWriter(int frequency){
        this.frequency = frequency;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("ThreadWriter.txt", false) )) {
            while (!Thread.currentThread().isInterrupted()) {
                writer.println("!Hola mundo!");
                Thread.sleep(TimeUnit.SECONDS.toMillis(frequency));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
