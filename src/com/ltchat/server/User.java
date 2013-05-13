package com.ltchat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stores information for a single user.
 * @author Jeff
 *
 */
public class User {
    /** Unique username. */
    private String userID;
    /** The socket the user connected with. */
    private Socket connection;
    /** Output writer for the user's socket. */
    private PrintWriter out;
    /** Input reader for the user's socket. */
    private Scanner in;
    /** User's buddy list. */
    private ArrayList<String> contactList;
    
    /**
     * @param id User ID
     * @param socket User's connection
     * @throws IOException Connection probably closed
     */
    public User(final String id, final Socket socket) throws IOException {
        
        userID = id;
        connection = socket;
        out = new PrintWriter(connection.getOutputStream(), true);
        in = new Scanner(connection.getInputStream());
        contactList = new ArrayList<String>();
        
    }
    
    /**
     * @return The user's socket
     */
    public Socket getConnection() {
        return connection;
    }
    
    /**
     * @param id Name to set the user's username to
     */
    public void setID(final String id) {
        userID = id;
    }
    
    /**
     * @return The user's unique username
     */
    public String getID() {
        return userID;
    }
    
    /**
     * @return The output writer for this user
     */
    public PrintWriter getOutputWriter() {
        return out;
    }
    
    /**
     * @return The input reader for this user
     */
    public Scanner getInputReader() {
        return in;
    }
    
    /**
     * @param contactID User ID of contact to add
     */
    public void addContact(final String contactID) {
        
        if (!contactList.contains(contactID)) {
            contactList.add(contactID);
        }
        
    }
    
    /**
     * @return Copy of user's contact list
     */
    public ArrayList<String> getContactList() {
        return new ArrayList<String>(contactList);
    }
    
    /**
     * Close up all the connections.
     * @throws IOException User closed connection.
     */
    public final void close() throws IOException {
        
        out.close();
        in.close();
        connection.close();
        
    }
    
}
