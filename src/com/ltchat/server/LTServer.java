package com.ltchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class LTServer {
    
    private ServerSocket serverSocket;
    private Map<String, Chatroom> allChatrooms;
    private Map<String, User> allUsers;
    
    LTServer(int port) {
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
    
    public Chatroom getChatroom(String id) {
        
        if (allChatrooms.containsKey(id)) {
            return allChatrooms.get(id);
        } else {
            return makeChatroom(id);
        }
    
    }
    
    private Chatroom makeChatroom(String id) {
        
        Chatroom chat = new Chatroom(id);
        allChatrooms.put(id, chat);
        return chat;
    
    }
    
    public void addUser(User u) throws IOException {
        if (allUsers.containsKey(u.getID())) {
            //TODO: No concurrent login
            u.getOutputWriter().println("That user is already logged in!");
            u.close();
        } else {
            allUsers.put(u.getID(), u);
        }
        System.out.println("Online users: " + allUsers.size());
    
    }
    
    private void closeServer() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}