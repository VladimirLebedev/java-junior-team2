package com.acme.edu.server;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable {
    private BlockingQueue<String> queue;
    private Set<SessionClient> clientSet;

    Sender(BlockingQueue<String> queue, Set<SessionClient> clientSet) {
        this.queue = queue;
        this.clientSet = clientSet;
    }

    @Override
    public void run() {
        while (true) {

            String message = null;
            try {
                message = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(message);
            Saver.addMessage(message);
            for (SessionClient client : clientSet) {
                client.send(message);
            }
        }

    }
}
