LTChat
======
Required Libraries for build:
Jsypt

To set up keystore:
    Create a keystore
        keytool -genkey -keystore Server.jks -alias LTChat
    Export the public key
        keytool -export -keystore Server.jks -alias LTChat -file LTChat.cer
    Import it to the client keystore
        keytool -import -keystore Client.jks -alias LTChat -file LTChat.cer