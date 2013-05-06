package com.ltchat.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class LTServer {
    
    SSLServerSocket serverSocket;
    
    LTServer(int port) {
        try {
            System.setProperty("javax.net.ssl.keyStore", "LTChat.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "ltpass");
            SSLServerSocketFactory serverFactory =
                    (SSLServerSocketFactory)
                    SSLServerSocketFactory.getDefault();
            serverSocket =
                    (SSLServerSocket)
                    serverFactory.createServerSocket(port);
            handleNewConnections();
            /*SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            String input = null;
            while ((input = in.readLine()) != null) {
                System.out.println(input);
                out.println(input);
                out.flush();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeServer();
        }
	}
    
    private void handleNewConnections() {
        int i = 0;
        while (true) {
            try {
                SSLSocket writerSocket = (SSLSocket) serverSocket.accept();
                Runnable worker = new ServerWorker(writerSocket);
                Thread t = new Thread(worker);
                t.start();
                System.out.println("Started Thread " + i++);
                /*System.out.println(writerSocket.getInetAddress());
                Scanner in = new Scanner(writerSocket.getInputStream());
                in.nextLine();
                while (in.hasNextLine()) {
                    System.out.println(in.nextLine());
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
    private void spawnClientThread(SSLSocket client) {
        
    }
    
    private void closeServer() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}