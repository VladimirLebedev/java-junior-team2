package com.acme.edu.client;

import java.io.IOException;
import java.net.Socket;

public class Connector {
    private Socket serverSocket;

    public void connect() throws IOException {
        serverSocket = new Socket("localhost", 4040);
    }

    public Socket getServerSocket() {
        return serverSocket;
    }

    public void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
