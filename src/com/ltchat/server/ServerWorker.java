package com.ltchat.server;

import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class ServerWorker implements Runnable {
    private PrintWriter out;
    private Scanner in;
    private SSLSocket client;
    private String name;

    public ServerWorker(SSLSocket client) {
        name = new String();
        try {
            
            this.client = client;
            System.out.println("Client Connected!! Yay!");
            out = new PrintWriter(client.getOutputStream(), true);
            in = new Scanner(client.getInputStream());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
        public void run() {
            doSomething();
            closeConnections();
            System.out.println("Thread closed.");

        }
        
        private void closeConnections() {

            try {
                out.close();
                in.close();
                client.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * Wait for message from client and print it out
         */
        public void doSomething() {
            while(in.hasNextLine()) {
                String message = in.nextLine();
                if (message.startsWith("identify")) {
                    name = message.replaceAll("identify ", "");
                }
                if (name.equals("")) {
                    System.out.println(client.getInetAddress() + " says: " + message);
                } else {
                    System.out.println(name + " says: " + message);
                }
                

                out.println("I am a cool server who also says " + message);

            }
        }
}
