/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ltchat.client;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 * Represents a Panel component for use in JTabbedPane, interface for chat room.
 * @author Mick
 */
public class ChatTab extends Panel implements ActionListener {

    /**
     * Class constructor, creates and layouts the panel with listener.
     * @param c The SSLSocketClient object to send chat messages through.
     * @param ch The name of the chat room.
     */
    public ChatTab(SSLSocketClient c, String ch) {
        //Initializing variables, instantiating objects.
        client = c;
        chat = ch;
        history = "";
        chatHistory = new JTextArea();
        input = new JTextArea();
        send = new JButton("Send");
        //Setting component dimesnions.
        chatHistory.setPreferredSize(new Dimension(769, 380));
        input.setPreferredSize(new Dimension(769, 100));
        //Disenabling, layout and adding components.
        chatHistory.setEnabled(false);
        send.addActionListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(chatHistory);
        add(input);
        add(send);
    }

    /**
     * Sends a message to the chat room when the send button is pressed.
     * @param e The ActionEvent that triggered.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String message = input.getText();
        client.sendMessage(message, chat);
        System.out.println(message);
    }
    
    /**
     * Updates the chat history with a new message.
     * @param user
     * @param message 
     */
    public void upMessage(String user, String message) {
        history += user + ":  " + message + "\n";
        chatHistory.setText(history);
    }
    
    /**
     * Returns the name of the chat room.
     * @return 
     */
    public String getChat() {
        return chat;
    }
    
    //Declaration of instance fields.
    private JTextArea chatHistory;
    private JTextArea input;
    private JButton send;
    private SSLSocketClient client;
    private String chat;
    private String history;
}
