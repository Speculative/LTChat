/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ltchat.client;

/**
 *
 * @author Mick
 */
public class ListenInput implements Runnable {

    public ListenInput(SSLSocketClient c, GUI gui) {
        client = c;
        this.gui = gui;
    }

    @Override
    public void run() {
        while (true) {
            String[] message = client.listen().split("`");
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
            System.out.println(message);
        }
    }
    private SSLSocketClient client;
    private GUI gui;
}
