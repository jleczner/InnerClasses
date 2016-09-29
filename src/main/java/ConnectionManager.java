import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathanleczner on 9/28/16.
 */
public class ConnectionManager {
    private List<Connection> connections;
    private int maxSize;
    public static final String SUCCESS = "New connection added";
    public static final String ERROR = "Could not add, manager is full";

    public ConnectionManager(int maxSize) {
        connections = new ArrayList<>();
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public Connection getConnection(String IP, Connection.Protocol protocol) {
        for (Connection c : connections) {
            String cIP = c.getIP();
            Connection.Protocol cProtocol = c.getProtocol();
            if (cIP.equals(IP) && cProtocol == protocol) {
                return c;
            }
        }
        return null;
    }

    public Connection getConnection(String IP, int port) {
        for (Connection c : connections) {
            String cIP = c.getIP();
            int cPort = c.getPort();
            if (cIP.equals(IP) && cPort == port) {
                return c;
            }
        }
        return null;
    }

    public String addConnection(String IP, Connection.Protocol protocol) {
        if (connections.size() == maxSize) {
            return ERROR;
        } else {
            connections.add(new ManagedConnection(IP, protocol));
            return SUCCESS;
        }
    }

    private class ManagedConnection implements Connection {
        private String IP;
        private int port;
        private Protocol protocol;

        public ManagedConnection(String IP, Protocol protocol) {
            this.IP = IP;
            this.protocol = protocol;
            port = getPort(protocol);
        }

        private int getPort(Protocol protocol) {
            switch (protocol) {
                case HTTP: return 80;
                case SMTP: return 25;
                case SSH: return 22;
                default: return 80;
            }
        }

        @Override
        public String getIP() {
            return IP;
        }

        @Override
        public int getPort() {
            return port;
        }

        @Override
        public Protocol getProtocol() {
            return protocol;
        }

        @Override
        public String connect() {
            return null;
        }
    }
}
