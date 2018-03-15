package com.acme.edu.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Test {
    public static void main(String[] args) {
        try (Socket connection = new Socket("localhost", 6666);
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            String message = "";
            while (!message.equals("/exit")) {
                message = consoleReader.readLine();
                writer.write(message);
                writer.newLine();
                writer.flush();
                System.out.println(reader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
