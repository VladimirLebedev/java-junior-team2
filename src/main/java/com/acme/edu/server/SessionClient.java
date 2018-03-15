package com.acme.edu.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
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
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
            this.writer = writer;
            String message;
            System.out.println("ready input");
            while (true) {
                try {
                    message = reader.readLine();
                } catch (SocketException e){
                    System.out.println("connection terminated");
                    break;
                }
                System.out.println(message);
                for (SessionClient client : clientList) {
                    client.send(message);
                    System.out.println(2);
                }
                //business-logic
            }
            connection.close();
            clientList.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        //System.out.println("message = " + message);
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
