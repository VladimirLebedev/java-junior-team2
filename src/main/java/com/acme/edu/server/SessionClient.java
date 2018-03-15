package com.acme.edu.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SessionClient{
    private Socket connection;

    SessionClient(Socket connection) {
        this.connection = connection;
    }

    public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))){
            while (true) {
                String message = reader.readLine();
                writer.write(message);
                writer.newLine();
                writer.flush();
                System.out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
