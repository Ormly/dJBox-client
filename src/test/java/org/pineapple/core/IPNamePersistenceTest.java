package org.pineapple.core;

import org.junit.jupiter.api.*;
import org.pineapple.backend.exceptions.JukeBoxIPNamePairNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Persistence Test - testing internal logic and file handling.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IPNamePersistenceTest
{
    private static JukeBoxClient jukeBoxClient;
    private static Logger log = Logger.getLogger(JukeBoxClientTest.class.getName());
    private static String firstTestName, secondTestName, firstTestIP, secondTestIP, fetchedName, fetchedIP;
    private static JukeBoxIPNamePair testPair;
    private static List<JukeBoxIPNamePair> testPairList;

    @Test
    @BeforeAll
    static void setup()
    {
        jukeBoxClient = jukeBoxClient.getJukeBoxClientInstance();
        assertNotNull(jukeBoxClient, "JukeBoxClient instance could not be retrieved.");

        firstTestName = "test1";
        secondTestName = "test2";
        firstTestIP = "192.168.1.1";
        secondTestIP = "192.168.1.2";
        fetchedName = "";
        fetchedIP = "";
        testPairList = new ArrayList<>();
    }

    @Test
    @AfterAll
    static void cleanup()
    {
        jukeBoxClient.deleteAllIPNamePairs();
        jukeBoxClient.addIPNamePair("localhost","localhost");
        jukeBoxClient.storePersistenceToFile();
    }

    @Test
    @Order(0)
    @Tag("Persistence")
    @DisplayName("Adding to and fetching single entries from Persistence.")
    void addAndReadEntries()
    {
        jukeBoxClient.addIPNamePair(firstTestName, firstTestIP);
        testPair = new JukeBoxIPNamePair(firstTestName,firstTestIP);
        fetchedName = jukeBoxClient.fetchJukeBoxIPNamePair(firstTestName).getJukeBoxName();
        fetchedIP = jukeBoxClient.fetchJukeBoxIPNamePair(firstTestName).getJukeBoxIP();

        assertEquals(firstTestName, fetchedName, "First pair fetched from persistence did not have expected name value.");
        assertEquals(firstTestIP, fetchedIP, "First pair fetched from persistence did not have expected ip value.");

        jukeBoxClient.addIPNamePair(secondTestName, secondTestIP);
        testPair = new JukeBoxIPNamePair(secondTestName, secondTestIP);
        fetchedName = jukeBoxClient.fetchJukeBoxIPNamePair(secondTestName).getJukeBoxName();
        fetchedIP = jukeBoxClient.fetchJukeBoxIPNamePair(secondTestName).getJukeBoxIP();

        assertEquals(testPair.getJukeBoxName(), fetchedName, "Second pair fetched from persistence did not have expected name value.");
        assertEquals(testPair.getJukeBoxIP(), fetchedIP, "Second pair fetched from persistence did not have expected ip value.");
    }

    @Test
    @Order(1)
    @Tag("Persistence")
    @DisplayName("Reading and removing single entries from persistence, checking if appropriate value is returned at invalid fetch.")
    void readAndDeleteEntries()
    {
        jukeBoxClient.deleteIPNamePair(firstTestName);
        assertNull(jukeBoxClient.fetchJukeBoxIPNamePair(firstTestName), "Fetch does not return null, even though fetched pair should not exist in persistence");

        testPair = jukeBoxClient.fetchJukeBoxIPNamePair(secondTestName);
        fetchedName = testPair.getJukeBoxName();
        fetchedIP = testPair.getJukeBoxIP();

        assertEquals(secondTestName, fetchedName, "Second pair fetched from persistence did not have expected name value.");
        assertEquals(secondTestIP, fetchedIP, "Second pair fetched from persistence did not have expected ip value.");
    }

    @Test
    @Order(2)
    @Tag("Persistence")
    @DisplayName("Reading all entries from persistence.")
    void readAllEntries()
    {
        testPairList = jukeBoxClient.fetchAllJukeBoxIPNamePairs();
        int pairListSize = testPairList.size();
        assertEquals(2,pairListSize,"Number of pairs not as expected.");
    }

    @Test
    @Order(3)
    @Tag("Persistence")
    @DisplayName("Removing all entries from persistence.")
    void removeAllEntries()
    {
        jukeBoxClient.deleteAllIPNamePairs();
        testPairList = jukeBoxClient.fetchAllJukeBoxIPNamePairs();
        assertEquals(true, testPairList.isEmpty(), "Persistence was not cleared on deletion.");
    }
}
