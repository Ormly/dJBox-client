package org.pineapple.core;

import org.junit.jupiter.api.*;
import org.pineapple.backend.exceptions.JukeBoxIPNamePairNotFoundException;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Persistence Test - testing internal logic and file handling.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IPNamePersistenceTest
{
    private static JukeBoxClient jukeBoxClient;
    private static Logger log = Logger.getLogger(JukeBoxClientTest.class.getName());
    private static String firstTestName, secondTestName, firstTestIP, secondTestIP;
    private static JukeBoxIPNamePair testPair;

    @Test
    @BeforeAll
    static void setup()
    {
        jukeBoxClient = jukeBoxClient.getJukeBoxClientInstance();
        assertNotNull(jukeBoxClient, "JukeBoxClient instance could not be retrieved.");

        jukeBoxClient.deleteAllIPNamePairs();

        firstTestName = "test1";
        secondTestName = "test2";
        firstTestIP = "192.168.1.1";
        secondTestIP = "192.168.1.2";
    }

    @Test
    @Order(0)
    @Tag("Persistence")
    @DisplayName("Adding entries to Persistence.")
    void addEntries()
    {
        jukeBoxClient.addIPNamePair(firstTestName, firstTestIP);
        testPair = new JukeBoxIPNamePair(firstTestName,firstTestIP);

        assertEquals(firstTestName,jukeBoxClient.fetchJukeBoxIPNamePair(firstTestName).getJukeBoxName(), "First pair fetched from persistence did not have expected name value.");
        assertEquals(firstTestIP,jukeBoxClient.fetchJukeBoxIPNamePair(firstTestName).getJukeBoxIP(), "First pair fetched from persistence did not have expected ip value.");

        jukeBoxClient.addIPNamePair(secondTestName, secondTestIP);
        testPair = new JukeBoxIPNamePair(secondTestName, secondTestIP);

        assertEquals(testPair.getJukeBoxName(),jukeBoxClient.fetchJukeBoxIPNamePair(secondTestName).getJukeBoxName(), "Second pair fetched from persistence did not have expected name value.");
        assertEquals(testPair.getJukeBoxIP(),jukeBoxClient.fetchJukeBoxIPNamePair(secondTestName).getJukeBoxIP(), "Second pair fetched from persistence did not have expected ip value.");
    }

    @Test
    @Order(1)
    @Tag("Persistence")
    @DisplayName("Removing entries from persistence.")
    void readEntries()
    {
        jukeBoxClient.deleteIPNamePair(firstTestName);
        assertEquals(1,jukeBoxClient.fetchAllJukeBoxIPNamePairs().size());
        testPair = jukeBoxClient.fetchJukeBoxIPNamePair(secondTestName);
        assertEquals(testPair.getJukeBoxName(),jukeBoxClient.fetchJukeBoxIPNamePair(secondTestName).getJukeBoxName(), "Second pair fetched from persistence did not have expected name value.");
        assertEquals(testPair.getJukeBoxIP(),jukeBoxClient.fetchJukeBoxIPNamePair(secondTestName).getJukeBoxIP(), "Second pair fetched from persistence did not have expected ip value.");
        assertThrows(JukeBoxIPNamePairNotFoundException.class, () -> jukeBoxClient.fetchJukeBoxIPNamePair(firstTestName), "Exception was not thrown, even though fetched pair should not exist in persistence");
    }
}
