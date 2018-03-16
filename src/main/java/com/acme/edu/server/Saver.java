package com.acme.edu.server;

import java.util.ArrayList;
import java.util.List;

class Saver {
    private List<String> messages = new ArrayList<>();
    void addMessage(String message){
        messages.add(message);
    }

    void sendHistory(){
        //TODO send history
    }
}
