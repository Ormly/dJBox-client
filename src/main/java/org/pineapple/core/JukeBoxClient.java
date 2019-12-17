package org.pineapple.core;

import org.pineapple.backend.*;
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

    /**
     * Initializes all relevant members, including the ServerControllerService providing server connection.
     */
    private JukeBoxClient()
    {
        queue = new SongList();
        library = new SongList();
        serverController = new ServerController(new HTTPControllerJavaNet());
        userData = new UserData();
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

        return ResponseState.SUCCESS;
    }

    /**
     * Exposes connection functionality to GUI.
     * Since no API call that tests for/establishes a connection to a JukeBox server exists, the authentication API is used to achieve the same result.
     * The auth API is called with nonsense data to force a 401 error code response, in which case we know that we reached a JukeBox. Otherwise, an
     * appropriate response state is returned instead.
     *
     * @param ip IP address of JukeBox to be connected.
     * @return ResponseState enum signifying to GUI whether API call succeeder not.
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
        return ResponseState.WRONGSTATE;
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
}


