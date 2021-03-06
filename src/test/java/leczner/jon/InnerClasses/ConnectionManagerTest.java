package leczner.jon.InnerClasses;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static leczner.jon.InnerClasses.Connection.Protocol;

/**
 * Created by jonathanleczner on 9/28/16.
 */
public class ConnectionManagerTest {
    ConnectionManager cm = new ConnectionManager(10);
    String[] IPAddresses = {"247.25.216.18",
            "144.116.246.206",
            "194.3.175.189",
            "140.31.241.26",
            "121.192.249.201",
            "45.87.55.67",
            "9.148.202.215",
            "184.59.197.117",
            "111.31.219.76",
            "13.136.61.40",
            "87.19.78.100",
            "12.241.154.169"};

    private String getRandomIP() {
        Random r = new Random();
        return IPAddresses[r.nextInt(IPAddresses.length - 1)];
    }

    @Test
    public void newConnectionTest() {
        assertNotNull(cm.getConnection(getRandomIP(), Protocol.HTTP));
    }

    @Test
    public void tooManyConnectionsTest() {
        for (int i = 0; i < cm.getMaxSize(); i++)
            cm.getConnection(getRandomIP(), Protocol.HTTP);

        assertNull(cm.getConnection(getRandomIP(), Protocol.HTTP));
    }

    @Test
    public void getFieldsTest() {
        Connection c = cm.getConnection(IPAddresses[0], Protocol.HTTP);
        c.connect();
        assertEquals(IPAddresses[0], cm.getConnections().get(0).getIP());
    }

    @Test
    public void getIPFailTest() {
        Connection c = cm.getConnection(IPAddresses[0], Protocol.HTTP);
        c.connect();
        c.close();
        assertEquals(ConnectionManager.CLOSEDERROR, cm.getConnections().get(0).getIP());
    }

    @Test
    public void getPortFailTest() {
        Connection c = cm.getConnection(IPAddresses[0], Protocol.HTTP);
        c.connect();
        c.close();
        assertEquals(ConnectionManager.CLOSEDERROR, cm.getConnections().get(0).getPort());
    }

    @Test
    public void connectTest() {
        Connection c = cm.getConnection(IPAddresses[0], Protocol.HTTP);
        assertEquals(ConnectionManager.SUCCESS, c.connect());
    }

    @Test
    public void connectFailTest() {
        Connection c = cm.getConnection(IPAddresses[0], Protocol.HTTP);
        c.connect();
        c.close();
        assertEquals(ConnectionManager.CLOSEDERROR, c.connect());
    }
}
