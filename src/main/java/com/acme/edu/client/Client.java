package com.acme.edu.client;

import java.io.*;
import java.util.Scanner;

public class Client {
    private static final int MAX_MESSAGE_LENGTH = 150;

    public static void main(String[] args) {
        Connector connector = null;
        Thread connectionLoop = null;
        try {
            connector = new Connector();
            connector.connect();
            System.out.println("Вы присоединились к чату!");

            connectionLoop = new Thread(new MessagePrinter(connector.getServerSocket()));
            connectionLoop.start();

            try (Scanner scanner = new Scanner(System.in);
                    PrintWriter printWriter = new PrintWriter(
                            new OutputStreamWriter(connector.getServerSocket().getOutputStream()), true)) {
                while (scanner.hasNextLine()) {
                    String inputMessage = scanner.nextLine();

                    if (inputMessage.equals("/exit")) {
                        break;
                    }

                    if (inputMessage.length() > MAX_MESSAGE_LENGTH) {
                        inputMessage = inputMessage.substring(0, MAX_MESSAGE_LENGTH);
                    }

                    printWriter.println(inputMessage);
                }
                System.out.println("Вы вышли из чата!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Подключение к серверу не удалось.");
        }
        finally {
            if (connectionLoop != null) {
                connectionLoop.interrupt();
            }
            if (connector != null) {
                connector.close();
            }
        }
    }
}
