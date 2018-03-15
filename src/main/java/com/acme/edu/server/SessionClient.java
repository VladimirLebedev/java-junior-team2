package com.acme.edu.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SessionClient implements Runnable {
    private Socket connection;
    private BufferedWriter writer;
    private List<SessionClient> clientList;

    public SessionClient(Socket connection, List<SessionClient> clientlist) {
        this.connection = connection;
        this.clientList = clientlist;
    }

    @Override
    public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))){
                this.writer = writer;
            System.out.println("ready input");
            while (!Thread.interrupted()) {
                System.out.println(1);
                String message = reader.readLine();
                System.out.println(message);
                for (SessionClient client : clientList) {
                    client.send(message);
                }
                //business-logic
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message){
        System.out.println("message = " + message);
        try {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
