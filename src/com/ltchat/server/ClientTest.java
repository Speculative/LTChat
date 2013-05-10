package com.ltchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.codec.binary.Base64;

public class ClientTest {
    
    private SSLSocket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    public ClientTest(String hostname, int port) {

        try {
            System.setProperty("javax.net.ssl.trustStore", "LTClient.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "ltpass");
            SSLSocketFactory factory =
                    (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) factory.createSocket(hostname, port);
            System.out.println("Connected to the server");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            askUser();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }

    }
    
    private void closeConnections() {

        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void askUser() {
        
        Scanner userInput = new Scanner(System.in);
        
        while(true) {
            try {
                System.out.println("Please enter a message to send to the server");
                String message = userInput.nextLine();
                if (message.startsWith("login")) {
                    String[] args = message.split("\\s");
                    String user = args[1];
                    String password = args[2];
                    out.println("reqsalt " + user);
                    String salt = in.readLine();
                    System.out.println("Salt: " + salt);
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.reset();
                    md.update((password + salt).getBytes("UTF-8"));
                    String passHash = new String(Base64.encodeBase64(md.digest()));
                    System.out.println("Passhash: " + passHash);
                    out.println("login " + user + " " + passHash);
                } else if (message.startsWith("register")) {
                    String[] args = message.split("\\s");
                    String user = args[1];
                    System.out.println("User: " + user);
                    String password = args[2];
                    System.out.println("Password: " + password);
                    byte[] randomBytes = new byte[32];
                    SecureRandom.getInstance("SHA1PRNG")
                        .nextBytes(randomBytes);
                    String salt = new String(Base64.encodeBase64(randomBytes));
                    System.out.println("Salt: " + salt);
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.reset();
                    md.update((password + salt).getBytes("UTF-8"));
                    String passHash = new String(Base64.encodeBase64(md.digest()));
                    System.out.println("Pass Hash: " + passHash);
                    out.println("register " + user + " " + passHash + " " + salt);
                } else {
                    out.println(message);
                }
                System.out.println(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public static void main(String[] args) {
        ClientTest ct = new ClientTest(args[0], Integer.parseInt(args[1]));
    }

}
