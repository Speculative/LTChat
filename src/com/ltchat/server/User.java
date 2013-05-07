package com.ltchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
    private String userID;
    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;
    
    public User(String id, Socket socket) throws IOException {
        userID = id;
        connection = socket;
        out = new PrintWriter(connection.getOutputStream());
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }
    
    public Socket getConnection() {
        return connection;
    }
    
    //TODO: DELETE THIS
    public void setID(String id) {
        userID = id;
    }
    
    public String getID() {
        return userID;
    }
    
    public PrintWriter getOutputWriter() {
        return out;
    }
    
    public BufferedReader getInputReader() {
        return in;
    }
    
    public void close() throws IOException{
        connection.close();
    }
    
}
