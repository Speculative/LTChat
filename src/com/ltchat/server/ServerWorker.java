package com.ltchat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.sql.*;

import javax.net.ssl.SSLSocket;

public class ServerWorker implements Runnable {
    private User user;
    private LTServer server;

    public ServerWorker(SSLSocket client, LTServer theServer) {
        
        server = theServer;
        try {
            
            authenticate(client);
            System.out.println("Client Connected!! Yay!");
       
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void authenticate(SSLSocket client) throws IOException {
        //TODO: MAKE THIS NOT FUCKING STUPID
        try {
            user = new User("", client);
            Class.forName("org.sqlite.JDBC");
            Connection dbConnection =
                    DriverManager.getConnection("jdbc:sqlite:./LTChat.db");
            Statement query = dbConnection.createStatement();
            //Ask for login or register
            String command = user.getInputReader().nextLine();
            if (command.startsWith("login")) {
                String[] args = command.split("\\s");
                ResultSet userSelect =
                        query.executeQuery(
                                "SELECT passhash FROM users WHERE id='"
                                + args[1]
                                + "';");
                //We only expect 1 result
                String clientPass = args[2];
                String passHash = userSelect.getString("passhash");
                if (clientPass.equals(passHash)) {
                    user.setID(args[1]);
                    server.addUser(user);
                    user.getOutputWriter().println("Successfully Logged In");
                } else {
                    user.getOutputWriter().println("Bad username or password");
                }
                //TODO: ACTUAL HASH AUTH
            } else if (command.startsWith("register")) {
                //Expect format: register user passhash salt
                String[] args = command.split("\\s");
                for (String s : args) {
                    System.out.println(s);
                }
                query.execute("INSERT INTO users values("
                        + "'" + args[1] + "',"
                        + "'" + args[2] + "',"
                        + "'" + args[3] + "',"
                        + "'0'"
                        + ");");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something's wrong with the database.");
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
    
    private void closeConnections() {
        
        try {

            user.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            
        }
        
    }

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
                            .sendMessage(messageArgs[1]);
                        System.out.println("Chat: " + messageArgs[0]);
                        System.out.println("Message: " + messageArgs[1]);
                        
                    } else {
                        
                        user.getOutputWriter()
                            .println("Usage: message [chatid] [message]");
                        
                    }
                    
                } else if (command.equals("identify")) {
                    //TODO: THIS SHOULD NOT BE HERE
                    user.setID(args);
                    user.getOutputWriter()
                        .println("Your name is now: " + user.getID());
                }
                
            } else {
                user.getOutputWriter().println("Invalid Command");
            }
            
        }
        
    }
}
