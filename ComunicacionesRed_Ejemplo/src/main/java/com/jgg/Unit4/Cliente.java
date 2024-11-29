package com.jgg.Unit4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digame un nombre con el que conectarse:");
        String name = sc.nextLine();
        try (Socket s = new Socket("localhost", 5000)){
            OutputStream out = s.getOutputStream();
            PrintWriter pw = new PrintWriter(out, true);

            while(true) {
                System.out.println("Usuario " + name + ", introduce la línea a enviar:");
                String message = name + ": " + sc.nextLine();
                pw.println(message);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
