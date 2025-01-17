package org.example;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static final Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        UI.printMessage(UI.SERVER_START);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!serverSocket.isClosed()) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            UI.printMessage(UI.ERROR + " Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;
        private PrintWriter writer;
        private DataOutputStream dataOut;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    DataInputStream dataIn = new DataInputStream(socket.getInputStream())
            ) {
                writer = new PrintWriter(socket.getOutputStream(), true);
                dataOut = new DataOutputStream(socket.getOutputStream());

                synchronized (clientHandlers) {
                    clientHandlers.add(this);
                }

                writer.println(UI.GET_NAME);
                clientName = reader.readLine();
                broadcast("** " + clientName + " se ha unido al chat **");

                String message;
                while ((message = reader.readLine()) != null) {
                    if (message.equals("/sendfile")) {
                        handleFileTransfer(reader, dataIn);
                    } else {
                        broadcast(clientName + ": " + message);
                    }
                }
            } catch (IOException e) {
                System.out.println(UI.ERROR + " Error con el cliente: " + e.getMessage());
            } finally {
                disconnect();
            }
        }

        private void handleFileTransfer(BufferedReader reader, DataInputStream dataIn) throws IOException {
            String fileName = reader.readLine();
            long fileSize = dataIn.readLong();
            broadcast(clientName + " ha enviado un archivo: " + fileName);

            byte[] buffer = new byte[4096];
            synchronized (clientHandlers) {
                for (ClientHandler client : clientHandlers) {
                    if (client != this) {
                        client.dataOut.writeUTF("/file");
                        client.dataOut.writeUTF(fileName);
                        client.dataOut.writeLong(fileSize);

                        long bytesSent = 0;
                        while (bytesSent < fileSize) {
                            int bytesRead = dataIn.read(buffer, 0, (int) Math.min(buffer.length, fileSize - bytesSent));
                            bytesSent += bytesRead;
                            client.dataOut.write(buffer, 0, bytesRead);
                        }
                        client.dataOut.flush();
                    }
                }
            }
        }

        private void broadcast(String message) {
            synchronized (clientHandlers) {
                for (ClientHandler client : clientHandlers) {
                    client.writer.println(message);
                }
            }
        }

        private void disconnect() {
            try {
                socket.close();
            } catch (IOException e) {
                UI.printMessage(UI.ERROR + " Error cerrando el socket: " + e.getMessage());
            }
            synchronized (clientHandlers) {
                clientHandlers.remove(this);
            }
            broadcast("** " + clientName + " ha salido del chat **");
        }
    }
}