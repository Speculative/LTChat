package com.ltchat.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.net.ssl.SSLSocket;


/**
 * Handles an individual user's connection.
 * @author Jeff
 *
 */
public class ServerWorker implements Runnable {
    /** Each ServerWorker serves one user. */
    private User user;
    /** Need to access the server to add users and get chatrooms. */
    private LTServer server;

    /**
     * Authenticates client then handles user input/output.
     * @param client Incoming client connection
     * @param theServer The server that started this thread
     */
    public ServerWorker(final SSLSocket client, final LTServer theServer) {
        
        server = theServer;
        try {
            
            Authenticator a =
                    new Authenticator(new User("", client));
            user = a.authenticate();
            if (server.addUser(user)) {
                user.getOutputWriter().println("LOGIN`true");
                makeContactList();
            } else {
                //Duplicate login - user already online
                user.getOutputWriter().println("LOGIN`false");
            }
       
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DB Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes the user's contact list.
     * @throws ClassNotFoundException Probably missing JDBC drivers
     * @throws SQLException DB Error
     */
    private void makeContactList() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection dbConnection =
                DriverManager.getConnection("jdbc:sqlite:./LTChat.db");
        Statement query = dbConnection.createStatement();
        ResultSet buddies = query.executeQuery(
                "SELECT buddies FROM users WHERE id='"
                + user.getID()
                + "' AND buddies IS NOT NULL;");
        if (buddies.next()) {
            
            String serializedList = buddies.getString("buddies");
            if (!serializedList.isEmpty()) {
                String[] contactList =
                        buddies.getString("buddies").split("\\s");
                for (String contact : contactList) {
                    user.addContact(contact);
                }
                server.printContactList(user);
            }
            
        }
        dbConnection.close();
    }
    
    /**
     * Writes the user's contact list to the db.
     * @throws ClassNotFoundException Probably missing JDBC drivers
     * @throws SQLException DB Error
     */
    private void writeContactList()
            throws ClassNotFoundException,
            SQLException {
        
        Class.forName("org.sqlite.JDBC");
        Connection dbConnection =
                DriverManager.getConnection("jdbc:sqlite:./LTChat.db");
        Statement query = dbConnection.createStatement();
        ArrayList<String> contactList = user.getContactList();
        String serializedList = "";
        for (String contactID : contactList) {
            serializedList += contactID + " ";
        }
        serializedList.trim();
        query.execute("UPDATE users "
                + "SET buddies='"
                + serializedList
                + "' WHERE id='"
                + user.getID()
                + "';");
        dbConnection.close();
        
    }
    
    @Override
    public void run() {
        
        try {
            handleMessages();
            closeConnections();
            System.out.println("Thread closed.");
        } catch (IOException e) {
            System.out.println("Uh oh.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("DB Error");
        } finally {
            //Log off
            server.removeUser(user.getID());
        }

    }
    
    /** Closes up the user's connection. */
    private void closeConnections() {
        
        try {

            user.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Handles user input/output.
     * @throws IOException Client probably closed.
     * @throws SQLException DB Error
     * @throws ClassNotFoundException Probably missing JDBC drivers
     */
    private void handleMessages()
            throws IOException,
            ClassNotFoundException,
            SQLException {
        //TODO: Remove all the sysouts in here
        while (user.getInputReader().hasNext()) {
            
            String message = user.getInputReader().nextLine();
            System.out.println("Message: " + message);
            
            if (message.matches("\\w+`+.+")) {
                
                String[] input = message.split("`", 2);
                String command = input[0];
                String args = input[1];
                
                if (command.equalsIgnoreCase("join")) {
                    
                    if (!args.contains("`")) {
                        
                        server.getChatroom(args).addUser(user);
                        server.notifyAllContacts(user);
                        
                    } else {
                        user.getOutputWriter().println("Usage: join [chat]");
                    }
                    
                } else if (command.equalsIgnoreCase("message")) {
                    
                    if (args.matches("^\\w+`?.+")) {
                        
                        String[] messageArgs = args.split("`", 2);
                        server.getChatroom(messageArgs[0])
                            .sendMessage(user.getID(), messageArgs[1]);
                        
                    }
                    
                } else if (command.equalsIgnoreCase("addcontact")) {
                    
                    if (args.matches("^\\w+$")) {
                        user.addContact(args);
                        writeContactList();
                        server.printContactList(user);
                    }
                    
                }
                
            } else {
                user.getOutputWriter().println("Invalid Command");
            }
            
        }
        
    }
}
