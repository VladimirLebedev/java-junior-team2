package com.acme.edu.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Acceptor implements Runnable {
    List<SessionClient> clientList = new LinkedList<>();

    public void run(){
        try (ServerSocket portListener = new ServerSocket(6666)) {
            //portListener.setSoTimeout(1_000);
            while(!Thread.interrupted()) { //Session loop
                System.out.println("listening....");
                try {
                    Socket connection = portListener.accept();
                    SessionClient client = new SessionClient(connection,clientList);
                    clientList.add(client);
                    System.out.println("ready");
                    new Thread(client).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
