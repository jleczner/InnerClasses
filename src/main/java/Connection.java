/**
 * Created by jonathanleczner on 9/28/16.
 */
public interface Connection {
    double getIP();
    short getPort();
    String getProtocol();
    String connect();
}
