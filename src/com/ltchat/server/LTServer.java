package com.ltchat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 * @author Jeff
 * Manages users, chatrooms, and connections.
 */
public final class LTServer {
    
    /** We only want one server running. */
    private static LTServer serverInstance;
    
    /** Handles all incoming connections. */
    private ServerSocket serverSocket;
    /** Server needs to keep track of all active chats. */
    private Map<String, Chatroom> allChatrooms;
    /** Server needs to keep track of all current users. */
    private Map<String, User> allUsers;
    
    /**
     * Starts accepting connections.
     * @param port Port to listen on
     */
    private LTServer(final int port) {
        try {
            
            System.setProperty("javax.net.ssl.keyStore", "LTChat.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "ltpass");
            ServerSocketFactory serverFactory =
                    SSLServerSocketFactory.getDefault();
            serverSocket = serverFactory.createServerSocket(port);
            
            allChatrooms = new HashMap<String, Chatroom>();
            allUsers = new HashMap<String, User>();
            
            handleNewConnections();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeServer();
        }
	}
    
    /**
     * @param port Port to listen on (passed to constructor)
     * @return The server instance
     */
    public static LTServer getInstance(final int port) {
        
        if (serverInstance == null) {
            serverInstance = new LTServer(port);
        }
        
        return serverInstance; 
        
    }
    
    /** Starts a new ServerWorker thread to handle each connection. */
    private void handleNewConnections() {
        
        while (true) {
            try {
                
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                Runnable worker = new ServerWorker(clientSocket, this);
                Thread t = new Thread(worker);
                t.start();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     *  Gets the reference to a chatroom, or makes one if it doesn't exist.
     *  @param id Chatroom name
     *  @return The chatroom matching id
     */
    public Chatroom getChatroom(final String id) {
        
        if (allChatrooms.containsKey(id)) {
            return allChatrooms.get(id);
        } else {
            return makeChatroom(id);
        }
    
    }
    
    /**
     * Creates a new chatroom and adds it to the server's list.
     * @param id Name of the chatroom
     * @return The chatroom
     */
    private Chatroom makeChatroom(final String id) {
        
        Chatroom chat = new Chatroom(id);
        allChatrooms.put(id, chat);
        
        //Update all users
        for (User u : allUsers.values()) {
            printChatList(u);
        }
        
        return chat;
    
    }
    
    /**
     * Updates the user with a list of all current chats.
     * @param u User to be notified
     */
    private void printChatList(final User u) {
        
        if (!allChatrooms.isEmpty()) {
            
            String message = "UPCHAT`";
            for (String chatID : allChatrooms.keySet()) {
                message += chatID + " ";
            }
            message.trim();
            u.getOutputWriter().println(message);
            
        }
        
    }
    
    /**
     * Prints all online contacts and the chatrooms they're in.
     * @param u User whose contact list we're updating
     */
    public void printContactList(final User u) {
        
        if (!u.getContactList().isEmpty()) {
            
            String message = "UPCONTACT`";
            
            for (String userID : u.getContactList()) {
                //See if contacts are online
                if (allUsers.containsKey(userID)) {
                    message += userID + "-";
                    for (Chatroom c : allChatrooms.values()) {
                        if (c.hasUser(userID)) {
                            message += c.getChatID() + ",";
                        }
                    }
                    message += " ";
                }
            }
            message.trim();
            if (!message.equals("UPCONTACT`")) {
                u.getOutputWriter().println(message);
            }
            
        }
        
    }
    
    /**
     * Notify all contacts that have user as a contact.
     * @param u User that other users are updated about
     */
    public void notifyAllContacts(final User u) {
        
        for (User someUser : allUsers.values()) {
            if (someUser.getContactList().contains(u.getID())) {
                printContactList(someUser);
            }
        }
        
    }
    
    /**
     * Adds a user to the server's list.
     * @param u The user to add
     * @throws IOException Something happened to client socket
     * @return True if successful, False if duplicate login
     */
    public boolean addUser(final User u) throws IOException {
        
        if (allUsers.containsKey(u.getID())) {
            return false;
        } else {
            
            allUsers.put(u.getID(), u);
            System.out.println("Online users: " + allUsers.size());
            printChatList(u);
            //Update all online contacts
            for (User someUser : allUsers.values()) {
                if (someUser.getContactList().contains(u.getID())) {
                    printContactList(someUser);
                }
            }
            return true;
        }
    
    }
    
    /**
     * Remove a user from allUsers list (logging out).
     * @param userID ID of user to remove
     */
    public void removeUser(final String userID) {
        allUsers.remove(userID);
    }
    
    /**
     * Shut down the server.
     */
    private void closeServer() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}