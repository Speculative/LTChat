/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ltchat.client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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

    JTextArea chatHistory;
    JTextArea input;
    JButton send;

    public ChatTab() {
        chatHistory = new JTextArea();
        chatHistory.setPreferredSize(new Dimension(769, 380));
        input = new JTextArea();
        input.setPreferredSize(new Dimension(769, 100));
        send = new JButton("Send");
        
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
//        SSLSocketClient.sendMessage(message, GUI.getUser());
        System.out.println(message);
    }
}
