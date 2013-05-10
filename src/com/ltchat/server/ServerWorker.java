package com.ltchat.server;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

import javax.net.ssl.SSLSocket;

import org.apache.commons.codec.binary.Base64;

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
            
            //While we're still not logged in
            while (user.getID().isEmpty() && user.getInputReader().hasNext()) {
                //Ask for login or register
                String command = user.getInputReader().nextLine();
                if(command.matches("^\\w+\\s?.+$")) {
                    String[] args = command.split("\\s");
                    ResultSet userSelect =
                            query.executeQuery(
                                    "SELECT * FROM users WHERE id='"
                                    + args[1]
                                    + "';");
                    if (command.startsWith("login")) {
                        String clientHash = args[2];
                        System.out.println("Client Hash: " + clientHash);
                        String storedHash = userSelect.getString("passhash");
                        System.out.println("Stored Hash: " + storedHash);
                        String serverSalt = userSelect.getString("serversalt");
                        System.out.println("Server Salt: " + serverSalt);
                        //TODO: Salt stuff
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        md.reset();
                        md.update((clientHash + serverSalt).getBytes("UTF-8"));
                        String computedHash = new String(Base64.encodeBase64(md.digest()));
                        System.out.println("Computed Hash: " + computedHash);
                        if (computedHash.equals(storedHash)) {
                            user.setID(args[1]);
                            server.addUser(user);
                            user.getOutputWriter()
                                .println("Successfully Logged In");
                        } else {
                            user.getOutputWriter()
                                .println("Bad username or password");
                        }
                        //TODO: ACTUAL HASH AUTH
                    } else if (command.startsWith("register")) {
                        //Expect format: register user passhash salt
                        //TODO: GENERATE A SALT PLEASE
                        if (userSelect.next()) {
                            //If an entry exists, duplicate registration
                            user.getOutputWriter().println("false");
                        } else {
                            System.out.println("Client Pass Hash: " + args[2]);
                            System.out.println("Client Salt: " + args[3]);
                            byte[] randomBytes = new byte[32];
                            SecureRandom.getInstance("SHA1PRNG")
                                .nextBytes(randomBytes);
                            String serverSalt = new String(Base64.encodeBase64(randomBytes));
                            System.out.println("Server Salt: " + serverSalt);
                            MessageDigest md = MessageDigest.getInstance("SHA-256");
                            md.reset();
                            md.update((args[2] + serverSalt).getBytes("UTF-8"));
                            String storedHash = new String(Base64.encodeBase64(md.digest()));
                            System.out.println("Stored Pass Hash: " + storedHash);
                            query.execute("INSERT INTO users values("
                                    + "'" + args[1] + "',"
                                    + "'" + storedHash + "',"
                                    + "'" + args[3] + "',"
                                    + "'" + serverSalt + "'"
                                    + ");");
                            user.getOutputWriter().println("true");
                            System.out.println("Successfully registered " + args[1]);
                        }
                    } else if (command.startsWith("reqsalt")) {
                        String salt = userSelect.getString("clientsalt");
                        System.out.println("Salt Requested: " + salt);
                        user.getOutputWriter().println(salt);
                    }
                }
            }
            System.out.println("Should be logged in.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something's wrong with the database.");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("Bad hashing algorithm!");
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
