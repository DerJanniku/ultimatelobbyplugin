
package org.derjannik.ultimatelobbyplugin;

public class MySQL {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public MySQL(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }
}
