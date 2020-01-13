package org.pineapple.core;

import org.pineapple.backend.*;
import org.pineapple.backend.exceptions.*;
import org.pineapple.backend.interfaces.PersistenceControllerService;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.core.interfaces.IMediaList;

import java.io.IOException;
import java.util.List;

/**
 * Central class controlling client-side logic, wraps functionality provided by server and exposes it to the GUI.
 */
public class JukeBoxClient
{
    private final ServerControllerService serverController;
    //Singleton
    private static JukeBoxClient jukeBoxClientInstance;

    private IMediaList library;
    private IMediaList queue;
    private UserData userData;
    private CurrentSong currentSong;
    private PersistenceControllerService jbIPNamePersistence;

    /**
     * Initializes all relevant members, including the ServerControllerService providing server connection.
     */
    private JukeBoxClient()
    {
        queue = new SongList();
        library = new SongList();
        serverController = new ServerController(new HTTPControllerJavaNet());
        userData = new UserData();
        currentSong = new CurrentSong();

        try
        {
            jbIPNamePersistence = new PersistenceControllerProperties(ClientConstants.RESOURCES_FOLDER_PATH);
        } catch(IOException e)
        {
            //...
        }
    }

    /**
     * JukeboxClient is Singleton.
     *
     * @return JukeboxClient instance.
     */
    public static JukeBoxClient getJukeBoxClientInstance()
    {
        if(jukeBoxClientInstance == null)
            jukeBoxClientInstance = new JukeBoxClient();

        return jukeBoxClientInstance;
    }

    /**
     * Exposes authentication functionality to GUI, relegates to ServerControllerService member.
     * Saves relevant data to member UserData if API call succeeded.
     *
     * @param userEmail
     * @param userPassword
     * @return ResponseState enum signifying to GUI whether API call succeeded or not.
     */
    public ResponseState doAuthentication(String userEmail, String userPassword)
    {
        try
        {
            userData.setSecurityToken(serverController.authenticateUser(userEmail, userPassword));
            userData.setEmailAddress(userEmail);
        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(GeneralServerIssueException generalEx)
        {
            return ResponseState.GENERALFAIL;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.AUTHFAIL;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        }

        userData.setLoggedIn(true);
        return ResponseState.SUCCESS;
    }

    /**
     * Exposes queue fetch functionality to GUI, relegates to ServerControllerService member.
     * No actual queue is returned, queue list of songs gets saved to queue member if API call succeeds.
     *
     * @return ResponseState enum signifying to GUI whether API call succeeded or not.
     */
    public ResponseState getQueueResponseState()
    {
        try
        {
            queue.setSongList(serverController.getServerQueueWithToken(userData.getSecurityToken()));

        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(GeneralServerIssueException generalEx)
        {
            return ResponseState.GENERALFAIL;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.AUTHFAIL;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        }

        return ResponseState.SUCCESS;
    }

    /**
     * Provides JukeboxClient queue as list of songs, to be called only after a valid ResponseState has been determined through getQueueResponseState.
     *
     * @return list of songs representing queue state.
     */
    public List<Song> doGetQueue()
    {
        //TODO: proofing
        return queue.getAllMedia();
    }

    /**
     * Exposes adding a song to server queue functionality to GUI, relegates to ServerControllerService member.
     *
     * @param songID ID of song to be added to queue.
     * @return ResponseState enum signifying to GUI whether API call succeeded or not.
     */
    public ResponseState addSongToQueue(int songID)
    {
        try
        {
            serverController.addSongToServerQueue(songID, userData.getSecurityToken());
        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(GeneralServerIssueException generalEx)
        {
            return ResponseState.GENERALFAIL;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.AUTHFAIL;
        } catch(SongNotFoundException songNotFoundEx)
        {
            return ResponseState.SONGNOTFOUND;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        }

        return ResponseState.SUCCESS;
    }

    /**
     * Exposes library fetch functionality to GUI, relegates to ServerControllerService member.
     * No actual library is returned, library list of songs gets saved to library member if API call succeeds.
     *
     * @return ResponseState enum signifying to GUI whether API call succeeded or not.
     */
    public ResponseState getLibraryResponseState()
    {
        try
        {
            library.setSongList(serverController.getServerLibraryWithToken(userData.getSecurityToken()));
        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(GeneralServerIssueException generalEx)
        {
            return ResponseState.GENERALFAIL;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.AUTHFAIL;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        }

        return ResponseState.SUCCESS;
    }

    /**
     * Provides JukeboxClient library as list of songs, to be called only after a valid ResponseState has been determined through getLibraryResponseState.
     *
     * @return list of songs representing library state.
     */
    public List<Song> doGetLibrary()
    {
        //TODO: proofing
        return library.getAllMedia();
    }

    /**
     * Exposes logout functionality to GUI, relegates to ServerControllerService member.
     *
     * @return ResponseState enum signifying to GUI whether API call succeeded or not.
     */
    public ResponseState doLogout()
    {
        try
        {
            serverController.logoutUser(userData.getSecurityToken());
            userData.clear();
        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(GeneralServerIssueException generalEx)
        {
            return ResponseState.GENERALFAIL;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.AUTHFAIL;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        }

        userData.setLoggedIn(false);
        return ResponseState.SUCCESS;
    }

    /**
     * Exposes connection functionality to GUI.
     * Since no API call that tests for/establishes a connection to a JukeBox server exists, the authentication API is used to achieve the same result.
     * The auth API is called with nonsense data to force a 401 error code response, in which case we know that we reached a JukeBox. Otherwise, an
     * appropriate response state is returned instead.
     *
     * @param ip IP address of JukeBox to be connected.
     * @return ResponseState enum signifying to GUI whether API call succeeded not.
     */
    public ResponseState doConnectViaIP(String ip)
    {
        try
        {
            setJukeBoxIP(ip);
            serverController.authenticateUser(ClientConstants.NONSENSE_USER_DATA, ClientConstants.NONSENSE_USER_DATA);
        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.SUCCESS;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        }
        clearJukeBoxIP();
        return ResponseState.INVALIDIP;
    }


    /**
     * Setter for JukeBox IP. Sets ServerControllerService URI member.
     *
     * @param ipAddress
     */
    private void setJukeBoxIP(String ipAddress)
    {
        serverController.setRequestURI(ipAddress);
    }

    /**
     * Clears IP of JukeBox set in Servercontroller.
     */
    private void clearJukeBoxIP()
    {
        serverController.clearRequestURI();
    }

    /**
     * Exposes registration functionality to GUI.relegates to ServerControllerService member.
     *
     * @param userEmail
     * @param userPassword
     * @return
     */
    public ResponseState doRegistration(String userEmail, String userPassword)
    {
        try
        {
            serverController.registerUser(userEmail, userPassword);
        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(GeneralServerIssueException generalEx)
        {
            return ResponseState.GENERALFAIL;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.AUTHFAIL;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        }

        return ResponseState.SUCCESS;
    }

    /**
     * Provides a song object representing the currently playing song from the server.
     * To be called only after updateCurrentSong has been called at least once.
     *
     * @return
     */
    public Song getCurrentSong()
    {
        return currentSong.song();
    }

    /**
     * Provides the elapsed time in seconds related to the currently playing song.
     *
     * @return
     */
    public double getCurrentSongElapsed()
    {
        return currentSong.getElapsed();
    }

    /**
     * Exposes fetching the currently playing song from the server.
     * Stores fetched information in a CurrentSong member, which wraps the relevant Song object together with the elapsed time in seconds at time of request.
     *
     * @return enum signifying to GUI whether API call succeeded or not.
     */
    public ResponseState updateCurrentSong()
    {
        try
        {
            currentSong.setSong(serverController.getCurrentSong(userData.getSecurityToken()));
        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(GeneralServerIssueException generalEx)
        {
            return ResponseState.GENERALFAIL;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.AUTHFAIL;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        } catch(NoCurrentSongException noCurrentEx)
        {
            return ResponseState.NOCURRENTSONG;
        }

        return ResponseState.SUCCESS;
    }

    /**
     * Exposes fetching the elapsed time in seconds in regards to the currently playing song from the server.
     * Stores fetched information in a CurrentSong member, which wraps the relevant Song object together with the elapsed time in seconds at time of request.
     *
     * @return enum signifying to GUI whether API call succeeded or not.
     */
    public ResponseState updateCurrentSongElapsed()
    {
        try
        {
            currentSong.setElapsed(serverController.getCurrentSongElapsed(userData.getSecurityToken()));
        } catch(IOException ioEx)
        {
            return ResponseState.CANTREACH;
        } catch(GeneralServerIssueException generalEx)
        {
            return ResponseState.GENERALFAIL;
        } catch(AuthenticationFailedException authFailEx)
        {
            return ResponseState.AUTHFAIL;
        } catch(InterruptedException interruptedEx)
        {
            Thread.currentThread().interrupt();
        } catch(NoCurrentSongException noCurrentEx)
        {
            return ResponseState.NOCURRENTSONG;
        }

        return ResponseState.SUCCESS;
    }

    public void addIPNamePair(String name, String ip)
    {
        jbIPNamePersistence.writeEntryToPersistence(name,ip);
    }

    public void deleteIPNamePair(String name)
    {
        jbIPNamePersistence.deleteEntryFromPersistence(name);
    }

    public void deleteAllIPNamePairs()
    {
        jbIPNamePersistence.deleteAllEntriesFromPersistence();
    }

    public void editNameOfPair(String oldName, String newName)
    {
        jbIPNamePersistence.editEntryName(oldName, newName);
    }

    public void editIPOfPair(String name, String newIP)
    {
        jbIPNamePersistence.editEntryIP(name, newIP);
    }

    public List<JukeBoxIPNamePair> fetchAllJukeBoxIPNamePairs()
    {
        List<JukeBoxIPNamePair> pairList = jbIPNamePersistence.readAllEntriesFromPersistence();

        return pairList;
    }

    public JukeBoxIPNamePair fetchJukeBoxIPNamePair(String key)
    {
        JukeBoxIPNamePair pair;
        try
        {
            pair = jbIPNamePersistence.readEntryFromPersistence(key);
        }
        catch(JukeBoxIPNamePairNotFoundException ex)
        {
            return null;
        }

        return pair;
    }

    public void storePersistenceToFile()
    {
        try
        {
            jbIPNamePersistence.storeToFile();
        }
        catch(PersistenceStoreException ex)
        {
            //...
        }
    }

    public boolean userLoggedIn()
    {
        return userData.isLoggedIn();
    }
}


