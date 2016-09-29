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
            if (c.getIP().equals(IP) && c.getProtocol() == protocol) {
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
            return null;
        }

        @Override
        public int getPort() {
            return 0;
        }

        @Override
        public Protocol getProtocol() {
            return null;
        }

        @Override
        public String connect() {
            return null;
        }
    }
}
