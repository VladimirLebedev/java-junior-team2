package com.acme.edu.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class MessagePrinter implements Runnable {
    private BufferedReader reader;

    MessagePrinter(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                String chatMessage = reader.readLine();
                System.out.println(chatMessage);
            }
            reader.close();
        } catch (SocketException e) {
            System.out.println("Соединение с сервером потеряно, перезапустите приложение!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
