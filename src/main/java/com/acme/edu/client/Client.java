package com.acme.edu.client;

public class Client {
    public static void main(String[] args) {
        String login = "22";
        Connector connector = new Connector("127.0.0.1", 4040, "client " + login);
        connector.send(login);


    }
}
