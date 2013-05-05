/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ltchat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author Mick
 */
public class SSLSocketClient {

    private SSLSocket socket;
    private PrintWriter out;
    private Scanner in;

    public SSLSocketClient(String hostname, int port) {
        SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
        System.out.println("Creating a SSL Socket for " + hostname
                + "on port " + port + ".");

        try {
            socket = (SSLSocket) factory.createSocket(hostname, port);
            socket.startHandshake();
            out = new PrintWriter(socket.getOutputStream());
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
    
    public boolean login(String username, String password) {
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        String encrypt = encryptor.encryptPassword(password);
        out.println("LOGIN`" + username + "`" + encrypt);
        return in.nextBoolean();
    }
    
    public boolean register(String username, String password) {
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        String encrypt = encryptor.encryptPassword(password);
        out.println("REGISTER`" + username + "`" + encrypt);
        return in.nextBoolean();
    }
    
    public boolean addContact(String username) {
        out.println("ADDCONTACT`" + username);
        return in.nextBoolean();
    }
    
    public boolean joinChat(String room) {
        out.println("JOIN`" + room);
        return in.nextBoolean();
    }
}
