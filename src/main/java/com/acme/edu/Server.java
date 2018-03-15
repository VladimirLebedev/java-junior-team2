package com.acme.edu;

import com.acme.edu.server.Acceptor;

/**
 * Hello world!
 *
 */
public class Server {
    public static void main( String[] args ) {
        new Thread(new Acceptor()).start();
    }
}
