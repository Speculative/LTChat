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
        
        int i = 1;
        while (true) {
            try {
                
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                Runnable worker = new ServerWorker(clientSocket, this);
                Thread t = new Thread(worker);
                t.start();
                System.out.println("Started Thread " + i++);
                
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
        return chat;
    
    }
    
    /**
     * Adds a user to the server's list.
     * @param u The user
     * @throws IOException Something happened to client socket.
     */
    public boolean addUser(final User u) throws IOException {
        
        if (allUsers.containsKey(u.getID())) {
            //TODO: No concurrent login
            //TODO: Write true/false for login success here
            return false;
        } else {
            allUsers.put(u.getID(), u);
            System.out.println("Online users: " + allUsers.size());
            return true;
        }
    
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