package com.acme.edu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Acceptor implements Runnable {
    private Set<SessionClient> clientSet = new HashSet<>();
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
//    private List<Socket> socketList = new LinkedList<>();
//    private List<Thread> threadList = new LinkedList<>();

    public void run(){
        new Thread(new Sender(queue, clientSet)).start();

        try (ServerSocket portListener = new ServerSocket(4040)) {
            //portListener.setSoTimeout(1_000);
            while(!Thread.interrupted()) { //Session loop
                System.out.println("listening....");
                try {
                    Socket connection = portListener.accept();
//                    socketList.add(connection);

                    SessionClient client = new SessionClient(connection, clientSet, queue);
                    //clientSet.add(client);
                    //addIntoList(client);
                    System.out.println("ready");

                    Thread threadClient = new Thread(client);
                    threadClient.start();
//                    threadList.add(threadClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addIntoList(SessionClient sessionClient){
        clientSet.add(sessionClient);
        //System.out.println(clientSet);
    }

//    public void stop(){
//        for (Socket connect : socketList){
//            try {
//                connect.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        for (Thread threadClient : threadList){
//            threadClient.interrupt();
//        }
//    }

}
