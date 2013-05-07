package com.ltchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ClientTest {
    
    private SSLSocket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    public ClientTest(String hostname, int port) {

        try {
            System.setProperty("javax.net.ssl.trustStore", "LTClient.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "ltpass");
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) factory.createSocket(hostname, port);
            System.out.println("Connected to the server");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                out.println(message);
                System.out.println(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public static void main(String[] args) {
        ClientTest ct = new ClientTest(args[0], Integer.parseInt(args[1]));
    }

}
