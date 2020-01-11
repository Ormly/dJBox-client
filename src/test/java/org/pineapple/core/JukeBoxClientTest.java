package org.pineapple.core;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JukeBoxClient Test - testing API calls and internal client logic")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JukeBoxClientTest
{
    private static JukeBoxClient jukeBoxClient;
    private static int songIDTest;
    private static Logger log = Logger.getLogger(JukeBoxClientTest.class.getName());

    @Test
    @BeforeAll
    static void setup()
    {
        jukeBoxClient = jukeBoxClient.getJukeBoxClientInstance();
        assertNotNull(jukeBoxClient, "JukeBoxClient instance could not be retrieved.");
        songIDTest = 1;
    }

    @Test
    @AfterAll
    static void cleanup()
    {
        //TODO: needed?
    }

    @Test
    @Order(0)
    @Tag("API")
    @Tag("Logic")
    @DisplayName("Connecting to Jukebox.")
    void connectToJukebox()
    {
        assertEquals(ResponseState.INVALIDIP, jukeBoxClient.doConnectViaIP("http://localhost:8080/nonsense"), "Successful connection response on invalid localhost IP.");
        assertEquals(ResponseState.CANTREACH,jukeBoxClient.doConnectViaIP("http://blabla"),"Successful connection response on invalid IP.");
        assertEquals(ResponseState.SUCCESS,jukeBoxClient.doConnectViaIP("http://localhost:8080"),"Unsuccessful connection response on valid IP.");
    }

    @Test
    @Order(1)
    @Tag("API")
    @Tag("Logic")
    @DisplayName("Calling API before authenticating.")
    void apiCallsWithoutValidTokenTest()
    {
        assertEquals(ResponseState.AUTHFAIL, (jukeBoxClient.getQueueResponseState()), "Queue could be fetched without providing valid token.");
        assertEquals(ResponseState.AUTHFAIL, (jukeBoxClient.getLibraryResponseState()), "Library could be fetched without providing valid token.");
        assertEquals(ResponseState.AUTHFAIL, (jukeBoxClient.addSongToQueue(songIDTest+1)), "Song could be added to server queue without providing valid token.");
        assertEquals(ResponseState.AUTHFAIL, (jukeBoxClient.doLogout()), "User could logout without prior authentication.");
    }

    @Test
    @Order(2)
    @Tag("API")
    @Tag("Logic")
    @DisplayName("Authenticating")
    void authenticationTest()
    {
        assertEquals(ResponseState.AUTHFAIL, (jukeBoxClient.doAuthentication("bla", "blub")), "Authentication using invalid login data was accepted.");
        assertEquals(ResponseState.SUCCESS, (jukeBoxClient.doAuthentication("testperson@gmail.com", "password")), "Authentication using valid login data was not accepted.");
        //TODO: add user getter and test whether members set correctly
    }

    @Test
    @Order(5)
    @Tag("API")
    @Tag("Logic")
    @DisplayName("Fetching library Response State and setting library song list")
    public void libraryFetchResponseTest()
    {
        assertEquals(ResponseState.SUCCESS, (jukeBoxClient.getLibraryResponseState()),"library could not be fetched with valid token.");
    }

    @Test
    @Order(6)
    @Tag("Logic")
    @DisplayName("Checking library Song list")
    public void libraryFetchSongListTest()
    {
        List<Song> songList = jukeBoxClient.doGetLibrary();
        assertFalse(songList.isEmpty(),"library song list is empty, when it should be set with songs from server.");
        //TODO: check whether library contents are as expected
    }

    @Test
    @Order(7)
    @Tag("API")
    @DisplayName("Adding Song to the Queue")
    void addSongToQueueTest()
    {
        assertEquals(ResponseState.SONGNOTFOUND, (jukeBoxClient.addSongToQueue(1212121212)), "Adding song with invalid ID was accepted.");
        assertEquals(ResponseState.SUCCESS, (jukeBoxClient.addSongToQueue(songIDTest)), "Adding song with valid ID was not accepted.");
        jukeBoxClient.addSongToQueue(songIDTest);
    }

    @Test
    @Order(9)
    @Tag("Logic")
    @DisplayName("Checking server queue Song list after adding song")
    public void queueFetchSongListTestAfter()
    {
        List<Song> songList = jukeBoxClient.doGetQueue();
        assertFalse(songList.isEmpty(),"queue song list is empty, when it should be populated.");
        assertEquals(songIDTest,songList.get(0).getId(),"song in queue does not have expected ID value.");
    }

    @Test
    @Order(10)
    @Tag("API")
    @Tag("Logic")
    @DisplayName("Checking current song")
    public void fetchAndCheckCurrentSong()
    {
        //dough needs some time to rise
        try
        {
            Thread.sleep(1000);
        } catch(InterruptedException e)
        {

        }

        Song song;
        assertEquals(ResponseState.SUCCESS,jukeBoxClient.updateCurrentSong(),"Current song could not be fetched.");
        song = jukeBoxClient.getCurrentSong();
        assertEquals(songIDTest,song.getId(),"Fetched current song does not hold expected ID value.");
    }

    @Test
    @Order(3)
    @Tag("API")
    @Tag("Logic")
    @DisplayName("Fetching server queue Response State and setting queue song list before adding song to queue")
    public void queueFetchResponseTestBefore()
    {
        assertEquals(ResponseState.SUCCESS, (jukeBoxClient.getQueueResponseState()),"queue could not be fetched with valid token.");
    }

    @Test
    @Order(8)
    @Tag("API")
    @Tag("Logic")
    @DisplayName("Fetching server queue Response State and setting queue song list after adding song to queue")
    public void queueFetchResponseTestAfter()
    {
        assertEquals(ResponseState.SUCCESS, (jukeBoxClient.getQueueResponseState()),"queue could not be fetched with valid token.");
    }

    @Test
    @Order(4)
    @Tag("Logic")
    @DisplayName("Checking server queue Song list before adding song")
    public void queueFetchSongListTestBefore()
    {
        List<Song> songList = jukeBoxClient.doGetQueue();
        assertTrue(songList.isEmpty(),"queue song list is populated, when it should be empty.");
    }

    //TODO: test exception handling -> after rework?
    /*
    @Test
    @Order(1)
    @DisplayName("Testing custom exceptions")
    void exceptionTesting()
    {
        assertThrows(AuthenticationFailedException.class, () -> jukeBoxClient.doAuthentication("blubblub", "blabla"), "AuthenticationFailedException not thrown when needed.");
    }
    */

    @Test
    @Order(99)
    @Tag("API")
    @DisplayName("Logging out")
    void doLogoutTest()
    {
        assertEquals(ResponseState.SUCCESS,(jukeBoxClient.doLogout()), "Logout failed.");
    }
}
