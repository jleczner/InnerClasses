package leczner.jon.InnerClasses;

/**
 * Created by jonathanleczner on 9/28/16.
 */
public interface Connection {
    enum Protocol {HTTP, SMTP, SSH}

    String getIP();
    String getPort();
    Protocol getProtocol();
    String connect();
    void close();
}
