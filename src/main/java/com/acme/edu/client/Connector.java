package com.acme.edu.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Connector {
    private String address;
    private int port;
    private String login;

    public Connector(String address, int port, String login) {
        this.address = address;
        this.port = port;
        this.login = login;
    }

    public void send(String message) {
        try (Socket connection = getConnection();
             BufferedWriter os = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
             BufferedReader is = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            System.out.println("ready to send");
            while (true){
                os.write(message);
                os.newLine();
                os.flush();
                System.out.println("message from "+login+":" +is.readLine());
                Thread.sleep(1000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Socket getConnection() throws IOException {
        return new Socket(InetAddress.getByName(address), port);
    }


}
