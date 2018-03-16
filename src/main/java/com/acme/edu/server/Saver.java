package com.acme.edu.server;

import java.util.LinkedList;
import java.util.List;

class Saver {
    private static List<String> messages = new LinkedList<>();
    private static String listMessageInString = "";

    static void addMessage(String message) {
        messages.add(message);
    }

    static void sendHistory(SessionClient sessionClient) {
        for (String message : messages) {
            listMessageInString += message;
            listMessageInString += System.lineSeparator();
            System.out.println("listMessageInString = " + listMessageInString);
        }
        sessionClient.send(listMessageInString);
        listMessageInString="";
    }
}
