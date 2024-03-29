/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ltchat.client;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Mick
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUI(String a, int p, SSLSocketClient c) {
        initComponents();
        loginPanel.setVisible(true);
        registerPanel.setVisible(false);
        mainPanel.setVisible(false);

        mainChatroomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Initializing variables.
        client = c;
        tabs = new ArrayList<>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextArea1 = new javax.swing.JTextArea();
        loginPanel = new javax.swing.JPanel();
        loginUsernameTextField = new javax.swing.JTextField();
        loginPasswordTextfield = new javax.swing.JTextField();
        loginTitleLabel = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        loginRegisterLinkLabel = new javax.swing.JLabel();
        registerPanel = new javax.swing.JPanel();
        registerUsernameTextField = new javax.swing.JTextField();
        registerPasswordTextField = new javax.swing.JTextField();
        registerRePasswordTextField = new javax.swing.JTextField();
        registerTitleLabel = new javax.swing.JLabel();
        registerButton = new javax.swing.JButton();
        registerLoginLinkLabel = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        mainChatTabbedPane = new javax.swing.JTabbedPane();
        mainChatroomScrollPane = new javax.swing.JScrollPane();
        mainChatroomList = new javax.swing.JList();
        mainJoinChatButton = new javax.swing.JButton();
        mainContactScrollPane = new javax.swing.JScrollPane();
        mainContactTextArea = new javax.swing.JTextArea();
        mainAddContactButton = new javax.swing.JButton();
        mainJoinChatroomTextField = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginUsernameTextField.setText("Username");
        loginUsernameTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginUsernameTextFieldMouseClicked(evt);
            }
        });

        loginPasswordTextfield.setText("Password");
        loginPasswordTextfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginPasswordTextfieldMouseClicked(evt);
            }
        });

        loginTitleLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        loginTitleLabel.setText("Welcome to LTChat!");

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        loginRegisterLinkLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        loginRegisterLinkLabel.setForeground(new java.awt.Color(0, 0, 255));
        loginRegisterLinkLabel.setText("New to LTChat? Register here...");
        loginRegisterLinkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginRegisterLinkLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(453, 453, 453)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(loginTitleLabel))
                    .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(loginButton)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loginPasswordTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(loginUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(loginRegisterLinkLabel)))
                .addContainerGap(530, Short.MAX_VALUE))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(loginTitleLabel)
                .addGap(32, 32, 32)
                .addComponent(loginUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(loginPasswordTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(loginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loginRegisterLinkLabel)
                .addContainerGap(254, Short.MAX_VALUE))
        );

        registerUsernameTextField.setText("Username");
        registerUsernameTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerUsernameTextFieldMouseClicked(evt);
            }
        });

        registerPasswordTextField.setText("Password");
        registerPasswordTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerPasswordTextFieldMouseClicked(evt);
            }
        });

        registerRePasswordTextField.setText("Re-enter Password");
        registerRePasswordTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerRePasswordTextFieldMouseClicked(evt);
            }
        });

        registerTitleLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        registerTitleLabel.setText("Please Enter Your Desired Username and Password");

        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        registerLoginLinkLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        registerLoginLinkLabel.setForeground(new java.awt.Color(0, 0, 255));
        registerLoginLinkLabel.setText("Return to login");
        registerLoginLinkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerLoginLinkLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout registerPanelLayout = new javax.swing.GroupLayout(registerPanel);
        registerPanel.setLayout(registerPanelLayout);
        registerPanelLayout.setHorizontalGroup(
            registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerPanelLayout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addGroup(registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerTitleLabel)
                    .addGroup(registerPanelLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(registerButton)
                            .addGroup(registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(registerRePasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(registerPasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(registerUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(registerLoginLinkLabel))))
                .addContainerGap(437, Short.MAX_VALUE))
        );
        registerPanelLayout.setVerticalGroup(
            registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerPanelLayout.createSequentialGroup()
                .addContainerGap(237, Short.MAX_VALUE)
                .addComponent(registerTitleLabel)
                .addGap(18, 18, 18)
                .addComponent(registerUsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(registerPasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(registerRePasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(registerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(registerLoginLinkLabel)
                .addGap(221, 221, 221))
        );

        mainChatroomScrollPane.setViewportView(mainChatroomList);

        mainJoinChatButton.setText("Join Chatroom");
        mainJoinChatButton.setToolTipText("If the textfield has content, a chatroom with that name is either created or joined.");
        mainJoinChatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainJoinChatButtonActionPerformed(evt);
            }
        });

        mainContactTextArea.setColumns(20);
        mainContactTextArea.setRows(5);
        mainContactScrollPane.setViewportView(mainContactTextArea);

        mainAddContactButton.setText("Add Contact");
        mainAddContactButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainAddContactButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(mainJoinChatroomTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainJoinChatButton))
                    .addComponent(mainAddContactButton)
                    .addComponent(mainChatroomScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                    .addComponent(mainContactScrollPane))
                .addGap(18, 18, 18)
                .addComponent(mainChatTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(mainChatTabbedPane)
                        .addContainerGap())
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(mainChatroomScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mainJoinChatButton)
                            .addComponent(mainJoinChatroomTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mainContactScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mainAddContactButton)
                        .addGap(6, 6, 6))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(registerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(loginPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 41, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 42, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(registerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(loginPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginUsernameTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginUsernameTextFieldMouseClicked
        loginUsernameTextField.setText("");
        loginUsernameTextField.requestFocusInWindow();
    }//GEN-LAST:event_loginUsernameTextFieldMouseClicked

    private void loginPasswordTextfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginPasswordTextfieldMouseClicked
        loginPasswordTextfield.setText("");
        loginPasswordTextfield.requestFocusInWindow();
    }//GEN-LAST:event_loginPasswordTextfieldMouseClicked

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String username = loginUsernameTextField.getText();
        String password = loginPasswordTextfield.getText();
        client.requestSalt(username, password);
        user = username;
    }//GEN-LAST:event_loginButtonActionPerformed

    private void loginRegisterLinkLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginRegisterLinkLabelMouseClicked
        loginPanel.setVisible(false);
        loginUsernameTextField.setText("Username");
        loginPasswordTextfield.setText("Password");
        registerPanel.setVisible(true);
    }//GEN-LAST:event_loginRegisterLinkLabelMouseClicked

    private void registerUsernameTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerUsernameTextFieldMouseClicked
        registerUsernameTextField.setText("");
        registerUsernameTextField.requestFocusInWindow();
    }//GEN-LAST:event_registerUsernameTextFieldMouseClicked

    private void registerPasswordTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerPasswordTextFieldMouseClicked
        registerPasswordTextField.setText("");
        registerPasswordTextField.requestFocusInWindow();
    }//GEN-LAST:event_registerPasswordTextFieldMouseClicked

    private void registerRePasswordTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerRePasswordTextFieldMouseClicked
        registerRePasswordTextField.setText("");
        registerRePasswordTextField.requestFocusInWindow();
    }//GEN-LAST:event_registerRePasswordTextFieldMouseClicked

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        //Passwords do not match.
        if (!registerPasswordTextField.getText().equals(
                registerRePasswordTextField.getText())) {
            JOptionPane.showMessageDialog(registerPanel,
                    "Your passwords did not match. Please try again.",
                    "Error: Password Verification Failure",
                    JOptionPane.ERROR_MESSAGE);
            registerPasswordTextField.setText("Password");
            registerRePasswordTextField.setText("Re-enter Password");
            return;
        }
        //Checking for empty usernames or passwords.
        String username = registerUsernameTextField.getText();
        String password = registerPasswordTextField.getText();
        if (username == null || username.isEmpty() || password == null
                || password.isEmpty()) {
            JOptionPane.showMessageDialog(registerPanel,
                    "Your username or password cannot be blank.",
                    "Error: Bad Username or Password",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } //If user did not click on the textbox.
        else if (username.equalsIgnoreCase("username")) {
            JOptionPane.showMessageDialog(registerPanel,
                    "Your username may not be \"username\".",
                    "Error: Bad Username",
                    JOptionPane.ERROR_MESSAGE);
            registerUsernameTextField.setText("Username");
            registerPasswordTextField.setText("Password");
            registerRePasswordTextField.setText("Re-enter Password");
            return;
        } else if (password.equalsIgnoreCase("password")) {
            JOptionPane.showMessageDialog(registerPanel,
                    "Your password may not be \"password\".",
                    "Error: Bad Password",
                    JOptionPane.ERROR_MESSAGE);
            registerPasswordTextField.setText("Password");
            registerRePasswordTextField.setText("Re-enter Password");
            return;
        }
        //Checking username format.
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{3,15}$");
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(registerPanel,
                    "Your username cannot have special characters and must"
                    + " be from 3 to 15 characters long.");
            registerUsernameTextField.setText("Username");
            registerPasswordTextField.setText("Password");
            registerRePasswordTextField.setText("Re-enter Password");
            return;
        }
        //Checking password format.
        pattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{6,20}");
        matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(registerPanel,
                    "Your password must (and can only) contain lower case,"
                    + " upper case, numbers, and a special character"
                    + " (!@#$%^&*).");
            registerPasswordTextField.setText("Password");
            registerRePasswordTextField.setText("Re-enter Password");
            return;
        }
        //Registering
        client.register(username, password);
    }//GEN-LAST:event_registerButtonActionPerformed

    private void registerLoginLinkLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerLoginLinkLabelMouseClicked
        registerPanel.setVisible(false);
        registerUsernameTextField.setText("Username");
        registerPasswordTextField.setText("Password");
        registerRePasswordTextField.setText("Re-enter Password");
        loginPanel.setVisible(true);
    }//GEN-LAST:event_registerLoginLinkLabelMouseClicked

    private void mainAddContactButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainAddContactButtonActionPerformed
        String id = JOptionPane.showInputDialog(mainPanel,
                "Please input contact's exact id.",
                "Add Friend",
                JOptionPane.QUESTION_MESSAGE);
        client.addContact(id);
    }//GEN-LAST:event_mainAddContactButtonActionPerformed

    private void mainJoinChatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainJoinChatButtonActionPerformed
        //Obtaining the name of a room to create (or join).
        String room = mainJoinChatroomTextField.getText();
        //If textField is empty, get the selected room from the list.
        if (room.isEmpty() || room == null) {
            room = (String) mainChatroomList.getSelectedValue();
        }
        //If a room is specified, attempt to join (and create if necessary).
        //Create a tab for the chat.
        if (!(room.isEmpty() || room == null)) {
            client.joinChat(room);
            ChatTab chatroomPanel = new ChatTab(client, room);
            tabs.add(chatroomPanel);
            mainChatTabbedPane.addTab(room, chatroomPanel);
        }
    }//GEN-LAST:event_mainJoinChatButtonActionPerformed

    /**
     * Adds a user message sent from server to appropriate chat tab.
     * @param chatId The name of the chat to add the message to.
     * @param user The name of the user sending it.
     * @param message The message sent by the user.
     */
    public void addMessage(String chatId, String user, String message) {
        //Searches for the Chat tab with the appropriate name and adds message.
        for (ChatTab tab : tabs) {
            if (tab.getChat().equals(chatId)) {
                tab.upMessage(user, message);
                return;
            }
        }
    }

    /**
     * Updates the list of chat rooms open.
     * @param chats The name of the chat rooms in String format.
     */
    public void updateChats(String chats) {
        mainChatroomList.setListData(chats.split("\\s"));
    }

    /**
     * Updates the text table of contact locations.
     * @param contacts The text form of the contact locations.
     */
    public void updateContacts(String contacts) {
        mainContactTextArea.setText(contacts.replaceAll("\\s", "\n"));
    }

    /**
     * Processing server return for login request.
     * @param allow Whether the server allows the user to login or not.
     */
    public void login(boolean allow) {
        //Login correct.
        if (allow) {
            //Display main screen.
            loginPanel.setVisible(false);
            loginUsernameTextField.setText("Username");
            loginPasswordTextfield.setText("Password");
            mainPanel.setVisible(true);
        } else { //Login incorrect.
            JOptionPane.showMessageDialog(loginPanel,
                    "Your username or password was incorrect.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            loginUsernameTextField.setText("Username");
            loginPasswordTextfield.setText("Password");
        }
    }

    /**
     * Processing server return for registration request.
     * @param allow Whether the server allows the registration or not.
     */
    public void register(boolean allow) {
        //Registration passed.
        if (allow) {
            JOptionPane.showMessageDialog(loginPanel,
                    "You have successfully registered as " + user + ".",
                    "Registered",
                    JOptionPane.INFORMATION_MESSAGE);
            //Return to login screen.
            registerPanel.setVisible(false);
            registerUsernameTextField.setText("Username");
            registerPasswordTextField.setText("Password");
            registerRePasswordTextField.setText("Re-enter Password");
            loginPanel.setVisible(true);

        } else { //Registration failed.
            JOptionPane.showMessageDialog(loginPanel,
                    "Your registration has failed."
                    + "Please try another username.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE);
            registerUsernameTextField.setText("Username");
            registerPasswordTextField.setText("Password");
            registerRePasswordTextField.setText("Re-enter Password");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JTextField loginPasswordTextfield;
    private javax.swing.JLabel loginRegisterLinkLabel;
    private javax.swing.JLabel loginTitleLabel;
    private javax.swing.JTextField loginUsernameTextField;
    private javax.swing.JButton mainAddContactButton;
    private javax.swing.JTabbedPane mainChatTabbedPane;
    private javax.swing.JList mainChatroomList;
    private javax.swing.JScrollPane mainChatroomScrollPane;
    private javax.swing.JScrollPane mainContactScrollPane;
    private javax.swing.JTextArea mainContactTextArea;
    private javax.swing.JButton mainJoinChatButton;
    private javax.swing.JTextField mainJoinChatroomTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel registerLoginLinkLabel;
    private javax.swing.JPanel registerPanel;
    private javax.swing.JTextField registerPasswordTextField;
    private javax.swing.JTextField registerRePasswordTextField;
    private javax.swing.JLabel registerTitleLabel;
    private javax.swing.JTextField registerUsernameTextField;
    // End of variables declaration//GEN-END:variables
    private static ArrayList<ChatTab> tabs;
    private SSLSocketClient client;
    private String user;
}
