package com.ltchat.server;

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
        
        System.out.println("Number of users: " + users.size());
        for (User u : users) {
            u.getOutputWriter().println(message);
        }
    
    }
}
