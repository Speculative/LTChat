package com.ltchat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class User {
    private String userID;
    private Socket connection;
    private PrintWriter out;
    private Scanner in;
    
    public User(String id, Socket socket) throws IOException {
        userID = id;
        connection = socket;
        out = new PrintWriter(connection.getOutputStream(), true);
        in = new Scanner(connection.getInputStream());
    }
    
    public Socket getConnection() {
        return connection;
    }
    
    public void setID(String id) {
        userID = id;
    }
    
    public String getID() {
        return userID;
    }
    
    public PrintWriter getOutputWriter() {
        return out;
    }
    
    public Scanner getInputReader() {
        return in;
    }
    
    public void close() throws IOException{
        out.close();
        in.close();
        connection.close();
    }
    
}
