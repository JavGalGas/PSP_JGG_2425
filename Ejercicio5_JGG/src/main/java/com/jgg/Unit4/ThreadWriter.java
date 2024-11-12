package com.jgg.Unit4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ThreadWriter implements Runnable {
    int frequency;

    public ThreadWriter(int frequency){
        this.frequency = frequency;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("ThreadWriter.txt", true) )) {
            while (!Thread.currentThread().isInterrupted()) {
                writer.println("!Hola mundo!");
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
