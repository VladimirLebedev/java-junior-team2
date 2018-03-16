package com.acme.edu.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Acceptor implements Runnable {
    private List<SessionClient> clientList = new LinkedList<>();
    private List<Socket> socketList = new LinkedList<>();
    private List<Thread> threadList = new LinkedList<>();

    public void run(){
        try (ServerSocket portListener = new ServerSocket(4040)) {
            //portListener.setSoTimeout(1_000);
            while(!Thread.interrupted()) { //Session loop
                System.out.println("listening....");
                try {
                    Socket connection = portListener.accept();
                    socketList.add(connection);

                    SessionClient client = new SessionClient(connection,clientList);
                    //clientList.add(client);
                    //addIntoList(client);
                    System.out.println("ready");

                    Thread threadClient = new Thread(client);
                    threadClient.start();
                    threadList.add(threadClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addIntoList(SessionClient sessionClient){
        clientList.add(sessionClient);
        //System.out.println(clientList);
    }

    public void stop(){
        for (Socket connect : socketList){
            try {
                connect.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Thread threadClient : threadList){
            threadClient.interrupt();
        }
    }

}
