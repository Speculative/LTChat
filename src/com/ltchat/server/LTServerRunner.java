package com.ltchat.server;

/** Utility class to just run the server. */
public final class LTServerRunner {

    /** This shouldn't happen ever. */
    private LTServerRunner() {
        throw new Error("Don't instantiate LTServerRunner.");
    }
    
    /**
     * @param args Expect IP and Port
     */
    public static void main(final String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java LTServerRunner port");
        } else {
            @SuppressWarnings("unused")
            LTServer server = LTServer.getInstance(Integer.parseInt(args[0]));
        }
    }

}
