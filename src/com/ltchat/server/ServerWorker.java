package com.ltchat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class ServerWorker implements Runnable {
    private PrintWriter out;
    private Scanner in;
    private User user;
    private LTServer server;

    public ServerWorker(SSLSocket client, LTServer theServer) {
        server = theServer;
        try {
            
            authenticate(client);
            System.out.println("Client Connected!! Yay!");
            out = new PrintWriter(client.getOutputStream(), true);
            in = new Scanner(client.getInputStream());
       
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void authenticate(SSLSocket client) throws IOException {
        //TODO: MAKE THIS NOT FUCKING STUPID
        user = new User("herp", client);
        server.addUser(user);
    }

    @Override
    public void run() {
        
        handleMessages();
        closeConnections();
        System.out.println("Thread closed.");

    }
    
    private void closeConnections() {
        try {
            
            out.close();
            in.close();
            user.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait for message from client and print it out
     */
    private void handleMessages() {
        //TODO: This needs to do chatroom communication now
        while (in.hasNextLine()) {
            System.out.println("We're heeeere");
            String message = in.nextLine();
            System.out.println("Message: " + message);
            
            if (message.matches("\\w+\\s+.+")) {
                
                String[] input = message.split("\\s", 2);
                String command = input[0].toLowerCase();
                System.out.println("Command: " + command);
                String args = input[1];
                
                if (command.equals("join")) {
                    if (!args.contains(" ")) {
                        server.getChatroom(args).addUser(user);
                        out.println("You joined chatroom: " + args);
                    } else {
                        out.println("Usage: join [chat]");
                    }
                } else if (command.equals("message")) {
                    if (args.matches("^\\w+\\s?.+")) {
                        String[] messageArgs = args.split("\\s", 2);
                        server.getChatroom(messageArgs[0])
                            .sendMessage(messageArgs[1]);
                        System.out.println("Chat: " + messageArgs[0]);
                        System.out.println("Message: " + messageArgs[1]);
                    } else {
                        out.println("Usage: message [chatid] [message]");
                    }
                } else if (command.equals("identify")) {
                    user.setID(args);
                    out.println("Your name is now: " + user.getID());
                }
                
            } else {
                out.println("Invalid Command");
            }
            System.out.println("Done looping");
        }
        System.out.println("Bad times.");
    }
}
