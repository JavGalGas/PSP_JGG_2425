package com.jgg.Unit4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(5000)) {
            while (true) {
                System.out.println("Esperando un cliente...");
                try (Socket s = servidor.accept()){
                    System.out.println("Hola");
                    Thread clientListener = new Thread(() -> {
                        try {
                            InputStream in = s.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(in));
                            while(!Thread.currentThread().isInterrupted()) {
                                String message = br.readLine();
                                System.out.println("Mensaje recibido del usuario " + message);
                            }
                        } catch (IOException exception) {
                            System.out.println(exception.getMessage());
                        }
                    });
                    clientListener.start();
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
