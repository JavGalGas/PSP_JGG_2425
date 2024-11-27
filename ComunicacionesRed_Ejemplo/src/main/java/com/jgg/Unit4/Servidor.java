package com.jgg.Unit4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(5000)) {
            System.out.println("Esperando un cliente...");
            try (Socket s = servidor.accept()){
                System.out.println("hola");

                InputStream in = s.getInputStream();
//              OutputStream out = s.getOutputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(in));
//              br.readLine();
//              PrintWriter pw = new PrintWriter(out, true);

                while(true) {
                    String message = br.readLine();
                    System.out.println("Mensaje recibido: " + message);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
