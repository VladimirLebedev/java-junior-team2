package com.acme.edu.client;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        Thread readProcess = null;

        try (Socket connection = new Socket("192.168.1.49", 4040);
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            String message = "";

            readProcess = new Thread(() -> {
                while (!Thread.interrupted()) {
                    try {
                        if (reader.ready()) {
                            System.out.println(reader.readLine());
                        }

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            readProcess.start();
            while (!message.equals("/exit")) {
                message = consoleReader.readLine();
                writer.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (readProcess != null) {
                readProcess.interrupt();
            }
        }
    }
}
