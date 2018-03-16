package com.acme.edu.server;

class Parser {
    private String inputString;
    private Saver saver;
    private SessionClient session;

    Parser(String inputString, Saver saver, SessionClient session) {
        this.inputString = inputString;
        this.saver = new Saver();
        this.session = session;
        parsString();
    }

    private void parsString(){
        String[] parsedString = inputString.split(" ");
        if (parsedString[0].equals("/snd")){
           // saver.addMessage(parsedString[1]);
            session.sendToAll(parsedString[1]);
            //send(parsedString[1]);
        }else if (parsedString[0].equals("/hist")){
            saver.sendHistory();
            //TODO доделать showHist
        } else if (parsedString[0].equals("/chid")) {
            //TODO доделать
        }
    }
}
