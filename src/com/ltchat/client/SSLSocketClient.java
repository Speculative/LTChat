/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ltchat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.commons.codec.binary.Base64;

/**
 * The SSL socket for the chat client.
 * @author Mick
 */
public class SSLSocketClient {

    /**
     * Class constructor, creates the SSLsocket and input/output streams.
     * @param hostname The address of the server.
     * @param port The port number of the server.
     */
    public SSLSocketClient(String hostname, int port) {
        //Creating the socket.
        System.setProperty("javax.net.ssl.trustStore", "LTClient.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "ltpass");
        SSLSocketFactory factory = 
                (SSLSocketFactory) SSLSocketFactory.getDefault();
        System.out.println("Creating a SSL Socket for " + hostname
                + "on port " + port + ".");
        try {
            socket = (SSLSocket) factory.createSocket(hostname, port);
            //Input/Output Streams.
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("IO Exception in creating SSL socket.");
        }
    }

//    private void closeConnections() {
//        try {
//            socket.close();
//            out.close();
//            in.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            System.out.println("FML");
//        }
//    }
    
    /**
     * Generates a random 32 bit salt using the SHA1PRNG algorithm.
     * @return The salt in String form.
     */
    private String genSalt() {
        final int SALTLENGTH = 32;
        byte[] salt = null;
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALTLENGTH);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Check genSalt() algorithm.");
        }
        return new String(Base64.encodeBase64(salt));
    }
    
    /**
     * Requests the salt from the server for a specific user's password.
     * @param username The username associated with the password.
     * @param password The password to request the salt for.
     */
    public void requestSalt(String username, String password) {
        out.println("REQSALT`" + username);
        //Stores username and password for use on login.
        tempUser = username;
        tempPass = password;
    }
    
    /**
     * Produces a salted hash of a password.
     * @param password The password to encrypt.
     * @param salt The salt to use.
     * @return The salted hashed password.
     */
    private String hash(String password, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            String salted = password + salt;
            md.update(salted.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Check hash() algorithm.");
        }
        return new String(Base64.encodeBase64(md.digest()));
    }
    
    /**
     * Listens for input from the server.
     * @return The message from the server.
     */
    public String listen() {
        return in.nextLine();
    }

    /**
     * Sends a login request to the server using given salt to hash the password
     * @param salt The salt to hash the password with.
     */
    public void login(String salt) {
        String encrypt = hash(tempPass, salt);
        out.println("LOGIN`" + tempUser + "`" + encrypt);
    }

    /**
     * Sends a register request to the server along with the salt used for hash.
     * @param username The username to request registration for.
     * @param password The password for the account, to be hashed w/ salt.
     */
    public void register(String username, String password) {
        String salt = genSalt();
        String encrypt = hash(password, salt);
        out.println("REGISTER`" + username + "`" + encrypt + "`" + salt);
    }

    /**
     * Sends an add contact request to the server.
     * @param username The username of the contact to add.
     */
    public void addContact(String username) {
        out.println("ADDCONTACT`" + username);
    }

    /**
     * Sends a join chat request to the server.
     * @param room The name of the chat room.
     */
    public void joinChat(String room) {
        out.println("JOIN`" + room);
    }
    
    /**
     * Sends a chat message to the server.
     * @param message The message to send to the chat room.
     * @param chatId  The name of the chat room the message is sent to.
     */
    public void sendMessage(String message, String chatId) {
        out.println("MESSAGE`" + chatId + "`" + message);
    }
    
    //Declaration of instance fields.
    private SSLSocket socket;
    private PrintWriter out;
    private Scanner in;
    private String tempUser;
    private String tempPass;
}
