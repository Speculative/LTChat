package com.ltchat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Chatroom {
    
    String chatID;
    List<User> users;
    
    Chatroom(String id) {
        chatID = id;
        users = new ArrayList<User>();
    }
    
    public void addUser(User u) {
        users.add(u);
        System.out.println(u.getID() + " joined " + chatID);
    }
    
    public void sendMessage(String message) {
        try {
            for (User u : users) {
                PrintWriter out =
                        new PrintWriter(u.getConnection().getOutputStream());
                out.println(message);
            }
            System.out.println("Number of users: " + users.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
