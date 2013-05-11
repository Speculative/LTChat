package com.ltchat.server;

import java.io.IOException;
import java.sql.SQLException;

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
            theServer.addUser(user);
            System.out.println("Client Connected!! Yay!");
       
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
     */
    private void handleMessages() throws IOException {
        //TODO: This needs to do chatroom communication now
        //TODO: Remove all the sysouts in here
        while (user.getInputReader().hasNext()) {
            
            String message = user.getInputReader().nextLine();
            System.out.println("Message: " + message);
            
            if (message.matches("\\w+\\s+.+")) {
                
                String[] input = message.split("\\s", 2);
                String command = input[0].toLowerCase();
                System.out.println("Command: " + command);
                String args = input[1];
                
                if (command.equals("join")) {
                    
                    if (!args.contains(" ")) {
                        
                        server.getChatroom(args).addUser(user);
                        user.getOutputWriter()
                            .println("You joined chatroom: " + args);
                        
                    } else {
                        user.getOutputWriter().println("Usage: join [chat]");
                    }
                    
                } else if (command.equals("message")) {
                    
                    if (args.matches("^\\w+\\s?.+")) {
                        
                        String[] messageArgs = args.split("\\s", 2);
                        server.getChatroom(messageArgs[0])
                            .sendMessage(user.getID(), messageArgs[1]);
                        
                    } else {
                        
                        user.getOutputWriter()
                            .println("Usage: message [chatid] [message]");
                        
                    }
                    
                }
                
            } else {
                user.getOutputWriter().println("Invalid Command");
            }
            
        }
        
    }
}
