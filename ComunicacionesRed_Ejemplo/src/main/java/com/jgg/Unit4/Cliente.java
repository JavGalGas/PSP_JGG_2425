package com.jgg.Unit4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        try (Socket s = new Socket("localhost", 5000)){
            OutputStream out = s.getOutputStream();
            PrintWriter pw = new PrintWriter(out, true);

            while(true) {
                System.out.println("Introduce la línea a enviar:");
                String message = sc.nextLine();
                pw.println(message);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
