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
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Mick
 */
public class SSLSocketClient {

    private static SSLSocket socket;
    private static PrintWriter out;
    private static Scanner in;

//    static {
//        String hostname = GUI.getAddress();
//        int port = GUI.getPort();
//        SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
//        System.out.println("Creating a SSL Socket for " + hostname
//                + "on port " + port + ".");
//
//        try {
//            socket = (SSLSocket) factory.createSocket(hostname, port);
//            socket.startHandshake();
//            out = new PrintWriter(socket.getOutputStream());
//            in = new Scanner(socket.getInputStream());
//        } catch (IOException ex) {
//            System.out.println("IO Exception in creating SSL socket.");
//        }
//    }

    private static void closeConnections() {
        try {
            socket.close();
            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("FML");
        }
    }
    
    private static String genSalt() {
        final int SALTLENGTH = 32;
        byte[] salt = null;
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALTLENGTH);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Check genSalt() algorithm.");
        }
        return new String(salt);
    }
    
    private static String requestSalt() {
        out.println("REQSALT`" + GUI.getUser());
        return in.nextLine();
    }
    
    private static String hash(String password, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            String salted = password + salt;
            md.update(salted.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Check hash() algorithm.");
        }
        return new String(md.digest());
    }

    public static boolean login(String username, String password) {
        String salt = requestSalt();
        String encrypt = hash(password, salt);
        out.println("LOGIN`" + username + "`" + encrypt + "`" + salt);
        return in.nextBoolean();
    }

    public static boolean register(String username, String password) {
        String salt = requestSalt();
        String encrypt = hash(password, salt);
        out.println("REGISTER`" + username + "`" + encrypt + "`" + salt);
        return in.nextBoolean();
    }

    public static boolean addContact(String username) {
        out.println("ADDCONTACT`" + username);
        return in.nextBoolean();
    }

    public static boolean joinChat(String room) {
        out.println("JOIN`" + room);
        return in.nextBoolean();
    }
    
    public static boolean sendMessage(String message, String userId) {
        out.println("MESSAGE`" + userId + "`" + message);
        return in.nextBoolean();
    }
    
    public static String requestContactUpdate() {
        out.println("UPCONT`" + GUI.getUser());
        return in.nextLine();
    }
    
    public static String requestChatroomUpdate() {
        out.println("UPROOM`");
        return in.nextLine();
    }
}
