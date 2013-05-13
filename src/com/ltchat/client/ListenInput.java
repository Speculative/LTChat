/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ltchat.client;

/**
 * Thread executed object for receiving messages from the server.
 * @author Mick
 */
public class ListenInput implements Runnable {

    /**
     * Class constructor, stores references to the SSLSocketClient and GUI.
     * @param c The SSLSocketClient object to use in sending messages.
     * @param gui The GUI object to perform server commands on.
     */
    public ListenInput(SSLSocketClient c, GUI gui) {
        client = c;
        this.gui = gui;
    }

    /**
     * Thread executed method.
     */
    @Override
    public void run() {
        //Listens for commands from the server while program is active.
        while (true) {
            //Extracts the command from the server's message.
            String[] message = client.listen().split("`");
            //Performs appropriate action.
            switch(message[0]) {
                case "MESSAGE":
                    gui.addMessage(message[1], message[2], message[3]);
                    break;
                case "UPCHAT":
                    gui.updateChats(message[1]);
                    break;
                case "UPCONTACT":
                    gui.updateContacts(message[1]);
                    break;
                case "LOGIN":
                    gui.login(Boolean.parseBoolean(message[1]));
                    break;
                case "REGISTER":
                    gui.register(Boolean.parseBoolean(message[1]));
                    break;
                case "REQSALT":
                    client.login(message[1]);
                    break;
            }
//            System.out.println(message);
        }
    }
    
    //Declaration of instance fields.
    private SSLSocketClient client;
    private GUI gui;
}
