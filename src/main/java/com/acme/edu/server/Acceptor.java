package com.acme.edu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Acceptor implements Runnable {

    public void run() {
        try (ServerSocket portListener = new ServerSocket(6666)) {
            //portListener.setSoTimeout(1_000);
            while (true) {
                try (Socket connection = portListener.accept()) {
                    System.out.println("connection created");
                    SessionClient client = new SessionClient(connection);
                    client.run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
