package com.ltchat.server;

public class LTServerRunner {

    private LTServerRunner() {
        throw new Error("Don't instantiate LTServerRunner.");
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java LTServerRunner port");
        } else {
            LTServer server = new LTServer(Integer.parseInt(args[0]));
        }
    }

}
