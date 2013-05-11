LTChat
======
Required Libraries for build:

Xerial's SQLite JDBC Driver:
https://bitbucket.org/xerial/sqlite-jdbc/overview

Apache Commons Codec

To set up keystore:

    Create a keystore
    
        keytool -genkey -keystore Server.jks -alias LTChat
        
    Export the public key
    
        keytool -export -keystore Server.jks -alias LTChat -file LTChat.cer
        
    Import it to the client keystore
    
        keytool -import -keystore Client.jks -alias LTChat -file LTChat.cer