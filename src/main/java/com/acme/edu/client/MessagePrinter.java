package com.acme.edu.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MessagePrinter implements Runnable {
    private Socket serverSocket;

    MessagePrinter(Socket socket){
        this.serverSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {
            while (!Thread.interrupted()) {
                String chatMessage = reader.readLine();
                System.out.println(chatMessage);
            }
        } catch (SocketException | SocketTimeoutException e) {
            if (!Thread.interrupted()) {
                System.out.println("Соединение с сервером потеряно, перезапустите приложение!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
