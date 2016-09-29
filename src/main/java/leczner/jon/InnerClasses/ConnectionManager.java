package leczner.jon.InnerClasses;

import java.util.ArrayList;
import java.util.List;

import static leczner.jon.InnerClasses.Connection.Protocol;

/**
 * Created by jonathanleczner on 9/28/16.
 */
public class ConnectionManager {
    private List<Connection> connections;
    private int maxSize;
    public static final String SUCCESS = "New connection added";
    public static final String ERROR = "Connection manager is full";
    public static final String CLOSEDERROR = "Could not access";

    public ConnectionManager(int maxSize) {
        connections = new ArrayList<>();
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Connection getConnection(String IP, Protocol protocol) {
        Connection connection = new ManagedConnection(IP, protocol);
        if (connection.connect().equals(SUCCESS)) {
            return connection;
        } else {
            return null;
        }
    }

    public Connection getConnection(String IP, String port) {
        Protocol protocol = Protocol.HTTP;
        switch (port) {
            case "22":
                protocol = Protocol.SSH;
                break;
            case "25":
                protocol = Protocol.SMTP;
                break;
            default:
                break;
        }
        Connection connection = new ManagedConnection(IP, protocol);
        if (connection.connect().equals(SUCCESS)) {
            return connection;
        } else {
            return null;
        }
    }

    public Connection getConnection(String IP, String port, Protocol protocol) {
        Connection connection = new ManagedConnection(IP, port, protocol);
        if (connection.connect().equals(SUCCESS)) {
            return connection;
        } else {
            return null;
        }
    }

    private class ManagedConnection implements Connection {
        private String IP;
        private String port;
        private Protocol protocol;
        private boolean closed = false;

        public ManagedConnection(String IP, Protocol protocol) {
            this.IP = IP;
            this.protocol = protocol;
            port = getPort(protocol);
        }

        public ManagedConnection(String IP, String port, Protocol protocol) {
            this.IP = IP;
            this.port = port;
            this.protocol = protocol;
        }

        private String getPort(Protocol protocol) {
            switch (protocol) {
                case HTTP: return "80";
                case SMTP: return "25";
                case SSH: return "22";
                default: return "80";
            }
        }

        @Override
        public String getIP() {
            if (!closed) {
                return IP;
            } else {
                return CLOSEDERROR;
            }
        }

        @Override
        public String getPort() {
            if (!closed) {
                return port;
            } else {
                return CLOSEDERROR;
            }
        }

        @Override
        public Protocol getProtocol() {
            return protocol;
        }

        @Override
        public String connect() {
            if (!closed) {
                if (connections.size() != maxSize) {
                    connections.add(this);
                    return SUCCESS;
                } else {
                    return ERROR;
                }
            } else {
                return CLOSEDERROR;
            }
        }

        @Override
        public void close() {
            connections.remove(this);
            closed = true;
        }
    }
}
