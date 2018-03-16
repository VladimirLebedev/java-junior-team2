package com.acme.edu.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

class SessionClient implements Runnable {
    private Socket connection;
    private BufferedWriter writer;
    private List<SessionClient> clientList;
    private static Object monitor = new Object();

    SessionClient(Socket connection, List<SessionClient> clientlist) {
        this.connection = connection;
        this.clientList = clientlist;
        addIntoList();
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
                    new Parser(message,null,this);
                } catch (SocketException e) {
                    System.out.println("connection terminated");
                    removeIntoList();
                    break;
                }
                System.out.println(message);
                //sendToAll(message);
                //business-logic
            }
            connection.close();
            //clientList.remove(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(String message) {
        synchronized (monitor) {
            for (SessionClient client : clientList) {
                client.send(message);
            }
        }
    }

    private void addIntoList() {
        synchronized (monitor) {
            clientList.add(this);
        }
        //System.out.println(clientList);
    }

    private void removeIntoList() {
        synchronized (monitor) {
            clientList.remove(this);
            System.out.println(clientList);
        }
    }

    private void send(String message) {
        //System.out.println("message = " + message);
        try {
            System.out.println("list size: "+clientList.size());
            writer.write(message +" "+new Date().toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
