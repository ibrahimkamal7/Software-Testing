package edu.drexel.se320;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.lang.StringBuilder;
import java.lang.UnsupportedOperationException;

public class Client {

    private String lastResult;
    ServerConnection conn;
    StringBuilder sb = null;

    public Client() {
        lastResult = "";
    }

    public Client(ServerConnection conn){
        this.conn = conn;
        lastResult = "";
    }

    public ServerConnection getConn(){
        return this.conn;
    }

    public String requestFile(String server, String file) {
        if (server == null)
            throw new IllegalArgumentException("Null server address");
        if (file == null)
            throw new IllegalArgumentException("Null file");

	// We'll use a StringBuilder to construct large strings more efficiently
	// than repeated linear calls to string concatenation.
        sb = new StringBuilder();

        try {
            if (conn.connectTo(server)) {
                boolean validFile = conn.requestFileContents(file);
                if (validFile) {
                    while (conn.moreBytes()) {
                        String tmp = conn.read();
                        if (tmp != null) {
                            sb.append(tmp);
                        }
                    }
                    conn.closeConnection();
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }

        String result = sb.toString();
        lastResult = result;
        return result;
    }
}

