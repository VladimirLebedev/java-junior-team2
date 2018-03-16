package com.acme.edu.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

class SessionClient implements Runnable {
    private Socket connection;
    private BufferedWriter writer;
    private Set<SessionClient> clientSet;
    private static final Object monitor = new Object();
    private BlockingQueue<String> queue;

    SessionClient(Socket connection, Set<SessionClient> clientSet, BlockingQueue<String> queue) {
        this.connection = connection;
        this.clientSet = clientSet;
        this.queue = queue;
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
                    new Parser(message,this);
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
            //clientSet.remove(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(String message) {
        queue.offer(message);
    }

    private void addIntoList() {
        synchronized (monitor) {
            clientSet.add(this);
        }
        System.out.println(clientSet);
    }

    private void removeIntoList() {
        synchronized (monitor) {
            clientSet.remove(this);
            System.out.println(clientSet);
        }
    }

    public void send(String message) {
        //System.out.println("message = " + message);
        try {
            System.out.println("list size: "+ clientSet.size());
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
