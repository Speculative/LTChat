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
 *
 * @author Mick
 */
public class ChatTab extends Panel implements ActionListener {

    public ChatTab(SSLSocketClient c, String ch) {
        client = c;
        chat = ch;
        history = "";
        chatHistory = new JTextArea();
        input = new JTextArea();
        send = new JButton("Send");
        
        chatHistory.setPreferredSize(new Dimension(769, 380));
        input.setPreferredSize(new Dimension(769, 100));
        
        chatHistory.setEnabled(false);
        send.addActionListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(chatHistory);
        add(input);
        add(send);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = input.getText();
        client.sendMessage(message, chat);
        System.out.println(message);
    }
    
    public void upMessage(String user, String message) {
        history += user + ":  " + message + "\n";
        chatHistory.setText(history);
    }
    
    public String getChat() {
        return chat;
    }
    
    private JTextArea chatHistory;
    private JTextArea input;
    private JButton send;
    private SSLSocketClient client;
    private String chat;
    private String history;
}
