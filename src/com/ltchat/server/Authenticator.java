package com.ltchat.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.apache.commons.codec.binary.Base64;

/**
 * Facilitates user login/registration.
 * @author Jeff
 *
 */
public class Authenticator {
    /** User that we're trying to authenticate. */
    private User user;
    /** Needed to interact with the SQLite DB. */
    private Connection dbConnection;
    /** Used to execute queries on the DB. */
    private Statement query;
    /** Each query may return a ResultSet. */
    private ResultSet userSelect;
    
    /**
     * @param u User to authenticate
     */
    Authenticator(final User u) {
        try {
            user = u;
            Class.forName("org.sqlite.JDBC");
            dbConnection =
                    DriverManager.getConnection("jdbc:sqlite:./LTChat.db");
            query = dbConnection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something's wrong with the database.");
        }
    }
    
    /**
     * Handles registering/logging in new connections.
     * @throws IOException Client probably closed
     * @return Authenticated User or null if not
     * @throws SQLException 
     */
    public User authenticate() throws IOException, SQLException {
        try {
            
            //While we're still not logged in
            while (user.getID().isEmpty() && user.getInputReader().hasNext()) {
                //Ask for login or register
                String command = user.getInputReader().nextLine();
                
                if (command.matches("^\\w+`?.+$")) {
                    
                    String[] args = command.split("`");
                    
                    if (args[0].equalsIgnoreCase("login")) {
                        login(args[1], args[2]);
                    } else if (args[0].equalsIgnoreCase("register")) {
                        register(args[1], args[2], args[3]);
                    } else if (args[0].equalsIgnoreCase("reqsalt")) {
                        reqsalt(args[1]);                    
                    }
                    
                }
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something's wrong with the database.");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("Bad hashing algorithm!?");
        } finally {
            dbConnection.close();
        }
        
        return user;
        
    }
    
    /**
     * Register user in db.
     * @param userID The user to register
     * @param passHash Hashed password from client
     * @param clientSalt Salt from client
     * @throws SQLException DB error
     * @throws UnsupportedEncodingException Something wrong with Codec
     * @throws NoSuchAlgorithmException SHA-256 should be valid
     */
    private void register(
            final String userID,
            final String passHash,
            final String clientSalt)
                    throws SQLException,
                    UnsupportedEncodingException,
                    NoSuchAlgorithmException {
        userSelect =
                query.executeQuery(
                        "SELECT * FROM users WHERE lower(id)='"
                        + userID.toLowerCase()
                        + "';");
      //Expect format: register user passhash salt
        if (userSelect.next()) {
            
            //If an entry exists, duplicate registration
            user.getOutputWriter().println("REGISTER`false");
            
        } else {
            
            byte[] randomBytes = new byte[32];
            SecureRandom.getInstance("SHA1PRNG")
                .nextBytes(randomBytes);
            String serverSalt = new String(
                    Base64.encodeBase64(randomBytes));
            MessageDigest md =
                    MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update((passHash + serverSalt).getBytes("UTF-8"));
            String storedHash = new String(
                    Base64.encodeBase64(md.digest()));
            query.execute("INSERT INTO users values("
                    + "'" + userID + "',"
                    + "'" + storedHash + "',"
                    + "'" + clientSalt + "',"
                    + "'" + serverSalt + "'"
                    + ");");
            user.getOutputWriter().println("REGISTER`true");
            System.out.println(
                    "Successfully registered "
                    + userID);
            
        }
    }
    
    /**
     * Logs a user in, signified by setting the ID.
     * @param userID The ID of the user to log in
     * @param passhash Password hash from the client
     * @throws SQLException DB error
     * @throws NoSuchAlgorithmException SHA-256 should be valid
     * @throws IOException Client probably closed connection
     */
    private void login(final String userID, final String passhash)
            throws SQLException,
            NoSuchAlgorithmException,
            IOException {
        userSelect =
                query.executeQuery(
                        "SELECT * FROM users WHERE lower(id)='"
                        + userID.toLowerCase()
                        + "';");
        if (userSelect.next()) {
            
            String clientHash = passhash;
            String storedHash = userSelect.getString("passhash");
            String serverSalt = userSelect.getString("serversalt");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update((clientHash + serverSalt).getBytes("UTF-8"));
            String computedHash =
                    new String(Base64.encodeBase64(md.digest()));
            
            if (computedHash.equals(storedHash)) {
                user.setID(userID);
            } 
            
        } else {
            user.getOutputWriter().println("LOGIN`false");
        }
    }
    
    /**
     * Gets user client salt from db.
     * @param userID The ID of the user whose salt we want
     * @throws SQLException DB error
     */
    private void reqsalt(final String userID) throws SQLException {
        userSelect =
                query.executeQuery(
                        "SELECT * FROM users WHERE lower(id)='"
                        + userID.toLowerCase()
                        + "';");
        if (userSelect.next()) {
            String salt = userSelect.getString("clientsalt");
            user.getOutputWriter().println("REQSALT`" + salt);
        } else {
            user.getOutputWriter().println("REQSALT`false");
        }
    }
    
}
