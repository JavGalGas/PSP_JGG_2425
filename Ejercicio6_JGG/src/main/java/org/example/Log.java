package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Log {
    PrintWriter printWriter = null;

    public Log(){
        try {
            printWriter = new PrintWriter(new FileWriter("./Files/log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void escribir (int id, String cadena){

        // Escribimos en el fichero el id y la fecha y hora actual
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd ");
        LocalDateTime now = LocalDateTime.now();

        try {
            printWriter.println("ID: " + id + " - " + dtf.format(now));

            // Hacemos una espera aleatoria entre 1 y 50 milisegundos
            // Esta espera nos ayuda a forzar que se produzcan los problemas de concurrencia
            // y que no se produzcan solo de forma esporádica.
            Random r = new Random();
            Thread.sleep(r.nextInt(51));
            // Escribimos la línea
            printWriter.println(cadena);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void cerrar(){
        if(printWriter != null){
            printWriter.close();
        }
    }
}
