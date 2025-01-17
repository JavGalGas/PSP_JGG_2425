package org.example;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

            UI.printMessage(UI.CONNECTED + ". " + UI.GET_MESSAGE);

            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        if (message.equals("/file")) {
                            receiveFile(socket);
                        } else {
                            UI.printMessage(message);
                        }
                    }
                } catch (IOException exception) {
                    UI.printMessage(UI.DISCONNECTED + " Error: " + exception.getMessage());
                }
            });
            receiveThread.start();

            String input;
            while ((input = userInput.readLine()) != null) {
                if (input.startsWith("/sendfile")) {
                    sendFile(userInput, writer, dataOut);
                } else {
                    writer.println(input);
                }
            }
        } catch (IOException e) {
            UI.printMessage(UI.ERROR + " Error al conectar con el servidor: " + e.getMessage());
        }
    }

    private static void sendFile(BufferedReader userInput, PrintWriter writer, DataOutputStream dataOut) {
        try {
            System.out.println("Introduce la ruta del archivo:");
            String filePath = userInput.readLine();
            File file = new File(filePath);

            if (!file.exists() || !file.isFile()) {
                System.out.println("Archivo no válido.");
                return;
            }

            writer.println("/sendfile");
            writer.println(file.getName());
            dataOut.writeLong(file.length());

            try (FileInputStream fileIn = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileIn.read(buffer)) > 0) {
                    dataOut.write(buffer, 0, bytesRead);
                }
                dataOut.flush();
                System.out.println("Archivo enviado: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Error al enviar el archivo: " + e.getMessage());
        }
    }

    private static void receiveFile(Socket socket) {
        try (DataInputStream dataIn = new DataInputStream(socket.getInputStream())) {
            String fileName = dataIn.readUTF();
            long fileSize = dataIn.readLong();

            System.out.println("Recibiendo archivo: " + fileName + " (" + fileSize + " bytes)");

            try (FileOutputStream fileOut = new FileOutputStream("descargado_" + fileName)) {
                byte[] buffer = new byte[4096];
                long bytesRead = 0;
                while (bytesRead < fileSize) {
                    int read = dataIn.read(buffer, 0, (int) Math.min(buffer.length, fileSize - bytesRead));
                    bytesRead += read;
                    fileOut.write(buffer, 0, read);
                }
                System.out.println("Archivo recibido: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("Error al recibir el archivo: " + e.getMessage());
        }
    }
}
