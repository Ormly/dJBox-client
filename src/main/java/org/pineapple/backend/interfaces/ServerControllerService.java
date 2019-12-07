package org.pineapple.backend.interfaces;

import org.pineapple.backend.AuthenticationFailedException;
import org.pineapple.core.Song;

import java.io.IOException;
import java.util.List;

/**
 * Interface defining functionality needed by the Jukebox to retrieve data from the server.
 */
public abstract class ServerControllerService
{
    protected HTTPControllerService httpController;

    /**
     * Adds a song with a given ID to the Jukebox (server) queue.
     * Constructs appropriate URI and sends request via member HTTPController.
     *
     * @param songID ID uniquely identifying song.
     * @param securityToken needs to be passed to identify the user.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException
     * @throws InterruptedException
     */
    public abstract void addSongToServerQueue(int songID, String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException;

    /**
     * Logs out the current user.
     * Constructs appropriate URI according to server API and sends request via member HTTPController.
     *
     * @param securityToken needs to be passed to identify the user.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException
     * @throws InterruptedException
     */
    public abstract void logoutUser(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException;

    /**
     * Fetches the current queue of songs from the server.
     * Constructs appropriate URI according to server API and sends request via member HTTPController.
     *
     * @param securityToken needs to be passed to identify the user.
     * @return A list of songs representing the current queue state.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException
     * @throws InterruptedException
     */
    public abstract List<Song> getServerQueueWithToken(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException;

    /**
     * Fetches the current library of songs from the server.
     * Constructs appropriate URI according to server API and sends request via member HTTPController.
     *
     * @param securityToken needs to be passed to identify the user.
     * @return A list of songs representing the current library state.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException
     * @throws InterruptedException
     */
    public abstract List<Song> getServerLibraryWithToken(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException;

    /**
     * Authenticates a user with a given email and password.
     * Constructs appropriate URI according to server API and sends request via member HTTPController.
     *
     * @param userEmail
     * @param userPassword
     * @return The security token of the current user.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException
     * @throws InterruptedException
     */
    public abstract String authenticateUser(String userEmail, String userPassword)
    throws AuthenticationFailedException, IOException, InterruptedException;

    /**
     * requestURI setter.
     *
     * @param requestURI
     */
    public abstract void setRequestURI(String requestURI);
}
