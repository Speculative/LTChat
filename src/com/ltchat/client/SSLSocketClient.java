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
 *
 * @author Mick
 */
public class SSLSocketClient {

    private SSLSocket socket;
    private PrintWriter out;
    private Scanner in;
    private String tempUser;
    private String tempPass;

    public SSLSocketClient(String hostname, int port) {
        System.setProperty("javax.net.ssl.trustStore", "LTClient.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "ltpass");
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        System.out.println("Creating a SSL Socket for " + hostname
                + "on port " + port + ".");
        try {
            socket = (SSLSocket) factory.createSocket(hostname, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("IO Exception in creating SSL socket.");
        }
    }

    private void closeConnections() {
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("FML");
        }
    }
    
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
    
    public void requestSalt(String username, String password) {
        out.println("REQSALT`" + username);
        tempUser = username;
        tempPass = password;
    }
    
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
    
    public String listen() {
        return in.nextLine();
    }

    public void login(String salt) {
        String encrypt = hash(tempPass, salt);
        out.println("LOGIN`" + tempUser + "`" + encrypt);
    }

    public void register(String username, String password) {
        String salt = genSalt();
        String encrypt = hash(password, salt);
        out.println("REGISTER`" + username + "`" + encrypt + "`" + salt);
    }

    public void addContact(String username) {
        out.println("ADDCONTACT`" + username);
    }

    public void joinChat(String room) {
        out.println("JOIN`" + room);
    }
    
    public void sendMessage(String message, String chatId) {
        out.println("MESSAGE`" + chatId + "`" + message);
    }
}
