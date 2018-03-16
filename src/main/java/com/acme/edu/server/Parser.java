package com.acme.edu.server;

import java.util.Date;

class Parser {
    private String inputString;
    private SessionClient session;
    private String message = "";

    Parser(String inputString, SessionClient session) {
        this.inputString = inputString;
        this.session = session;
        parsString();
    }

    private void parsString() {
        if (inputString.equals("/hist")) {
            Saver.sendHistory(session);
            return;
        }
        String[] parsedString = inputString.split(" ");
        if (parsedString[0].equals("/snd")) {
            for (int i = 1; i < parsedString.length; i++) {
                message += parsedString[i];
                message += " ";
            }
            message = "[ " + new Date().toString() + " ]  " + message;
            //Saver.addMessage(message);
            session.sendToAll(message);
        } else if (parsedString[0].equals("/chid")) {
            //TODO доделать для команды /chid
        } else {
            session.send("Вы ввели не корректную команду " + parsedString[0]);
        }
    }
}
