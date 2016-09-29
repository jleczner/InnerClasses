import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
    public void addConnectionTest() {
        assertEquals(ConnectionManager.SUCCESS, cm.addConnection(getRandomIP(), Connection.Protocol.HTTP));
    }

    @Test
    public void addErrorTest() {
        for (int i = 0; i < cm.getMaxSize(); i++)
            cm.addConnection(getRandomIP(), Connection.Protocol.HTTP);

        assertEquals(ConnectionManager.ERROR, cm.addConnection(getRandomIP(), Connection.Protocol.HTTP));
    }

    @Test
    public void getConnectionTest() {
        cm.addConnection(IPAddresses[0], Connection.Protocol.SMTP);
        assertNotNull(cm.getConnection(IPAddresses[0], Connection.Protocol.SMTP));
    }

    @Test
    public void getConnectionFailTest() {
        cm.addConnection(IPAddresses[0], Connection.Protocol.SMTP);
        assertNull(cm.getConnection(IPAddresses[0], Connection.Protocol.HTTP));
    }

    @Test
    public void getConnectionPort() {
        cm.addConnection(IPAddresses[0], Connection.Protocol.SSH);
        assertNotNull(cm.getConnection(IPAddresses[0], 22));
    }

    @Test
    public void getConnectionPortFail() {
        cm.addConnection(IPAddresses[0], Connection.Protocol.SSH);
        assertNull(cm.getConnection(IPAddresses[0], 21));
    }
}
