package com.ltchat.server;

import java.util.ArrayList;
import java.util.List;

/** A chatroom. */
public class Chatroom {
    
    /** Each chatroom has its own unique name. */
    private String chatID;
    /** All of the users in this room. */
    private List<User> users;
    
    /**
     * Make a new chatroom.
     * @param id Unique name of chatroom
     */
    Chatroom(final String id) {
        
        chatID = id;
        users = new ArrayList<User>();
        
    }
    
    /**
     * @return The ID of this chat
     */
    public String getChatID() {
        return chatID;
    }
    
    /**
     * Adds user to the chatroom.
     * @param u The user
     */
    public void addUser(final User u) {
        
        users.add(u);
        System.out.println(u.getID() + " joined " + chatID);
        
    }
    
    /**
     * Sends a message to all users in the room.
     * @param userID User who sent the message
     * @param message The message
     */
    public void sendMessage(final String userID, final String message) {
        System.out.println("Number of users: " + users.size());
        for (User u : users) {
            u.getOutputWriter().println(userID + ": " + message);
        }
    
    }
}
