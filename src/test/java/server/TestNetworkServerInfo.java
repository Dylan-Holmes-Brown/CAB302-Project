package server;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import server.NetworkServer;

public class TestNetworkServerInfo {

    /**
     * Initialise the Network Server class getting all network information
     */
    @BeforeEach @Test
    public void setupServer() {
        new NetworkServer();
    }

    /**
     * Test that the port being used is the expected port
     */
    @Test
    public void testGetPort() {
        assertEquals(10000, NetworkServer.getPort());
    }
}
