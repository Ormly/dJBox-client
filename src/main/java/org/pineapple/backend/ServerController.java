package org.pineapple.backend;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.pineapple.backend.exceptions.AuthenticationFailedException;
import org.pineapple.backend.exceptions.GeneralServerIssueException;
import org.pineapple.backend.exceptions.SongNotFoundException;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.backend.interfaces.HTTPControllerService;
import org.pineapple.core.Song;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

/**
 * Exposes functionality for fetching server data via server API to JukeBoxClient.
 */
public class ServerController extends ServerControllerService
{
    private String requestURI;

    public ServerController(HTTPControllerService httpController)
    {
        this.httpController = httpController;
    }

    /**
     * Requests a song with a given ID to be added to the server queue.
     * Constructs a requestURI and calls on HTTPControllerService to submit the request.
     *
     * @param songID        ID uniquely identifying song.
     * @param securityToken needs to be passed to identify the user.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws GeneralServerIssueException if the server has unspecified problems.
     * @throws SongNotFoundException if the requested song ID cannot be  found server-side.
     * @throws IOException if there are IO issues.
     * @throws InterruptedException if there are thread issues.
     */
    public void addSongToServerQueue(int songID, String securityToken)
    throws AuthenticationFailedException, GeneralServerIssueException, SongNotFoundException, IOException, InterruptedException
    {
        String request = requestURI + "/queue/add/" + songID;

        httpController.sendGetRequestWithToken(request, securityToken);
    }

    /**
     * Fetches the current queue of songs from the server.
     * JSON-formatted response from HTTPController gets converted into List of Song objects via Jackson.
     *
     * @param securityToken needs to be passed to identify the user.
     * @return A list of songs representing the current queue state.
     * @throws IOException if there are IO issues.
     * @throws InterruptedException if there are thread issues.
     * @throws AuthenticationFailedException if the token is invalid.
     */
    @Override
    public List<Song> getServerQueueWithToken(String securityToken)
    throws AuthenticationFailedException, GeneralServerIssueException, IOException, InterruptedException
    {
        List<Song> queue;
        String request = requestURI + "/queue";
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder authResponse = new StringBuilder();

        authResponse.append(httpController.sendGetRequestWithToken(request, securityToken));

        queue = Arrays.asList(mapper.readValue(authResponse.toString(), Song[].class));

        return queue;
    }

    /**
     * Fetches the current library of songs from the server.
     * JSON-formatted response from HTTPController gets converted into List of Song objects via Jackson.
     *
     * @param securityToken needs to be passed to identify the user.
     * @return A list of songs representing the current library state.
     * @throws IOException if there are IO issues.
     * @throws InterruptedException if there are thread issues.
     * @throws AuthenticationFailedException if the token is invalid.
     */
    @Override
    public List<Song> getServerLibraryWithToken(String securityToken)
    throws AuthenticationFailedException, GeneralServerIssueException, IOException, InterruptedException
    {
        List<Song> library;
        String request = requestURI + "/library";

        ObjectMapper mapper = new ObjectMapper();
        StringBuilder authResponse = new StringBuilder();

        authResponse.append(httpController.sendGetRequestWithToken(request, securityToken));

        library = Arrays.asList(mapper.readValue(authResponse.toString(), Song[].class));

        return library;
    }

    /**
     * Authenticates a user with a given email and password.
     * Constructs appropriate URI according to server API and sends request via member HTTPController.
     *
     * @param userEmail
     * @param userPassword
     * @return The security token of the current user.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException if there are IO issues.
     * @throws InterruptedException if there are thread issues.
     * @throws GeneralServerIssueException if the server has unspecified problems.
     */
    @Override
    public String authenticateUser(String userEmail, String userPassword)
    throws AuthenticationFailedException, GeneralServerIssueException, IOException, InterruptedException
    {
        StringBuilder authResponse = new StringBuilder();
        String request = requestURI + "/auth";
        //TODO: reconsider this bullshit
        String requestBody = "{ \"" + "userEmail" + "\"" + " : " + "\"" + userEmail + "\"" + ", " + "\"" + "password" + "\"" + " : " + "\"" + userPassword + "\" }";

        HttpHeaders headers = httpController.sendPostRequest(request, requestBody);

        authResponse.append(headers.allValues("token"));

        //token is surrounded by braces (JSON-formatted), which need to be removed
        return authResponse.substring(1, authResponse.length() - 1);
    }

    /**
     * Requests a user to be registered with the server using passed email and password.
     * Constructs a requestURI and calls on HTTPControllerService to submit the request.
     *
     * @param userEmail
     * @param userPassword
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException if there are IO issues.
     * @throws InterruptedException if there are thread issues.
     */
    @Override
    public void registerUser(String userEmail, String userPassword)
    throws AuthenticationFailedException, IOException, InterruptedException
    {

        String request = requestURI + "/auth/register";

        String requestBody = "{ \"" + "userEmail" + "\"" + " : " + "\"" + userEmail + "\"" + ", " + "\"" + "password" + "\"" + " : " + "\"" + userPassword + "\" }";

        httpController.sendPostRequest(request, requestBody);
    }


    /**
     * Requests user to be logged out server-side.
     * Constructs a requestURI and calls on HTTPControllerService to submit the request.
     *
     * @param securityToken needs to be passed to identify the user.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException if there are IO issues.
     * @throws InterruptedException if there are thread issues.
     * @throws GeneralServerIssueException if the server has unspecified problems.
     */
    @Override
    public void logoutUser(String securityToken)
    throws AuthenticationFailedException, GeneralServerIssueException, IOException, InterruptedException
    {
        String request = requestURI + "/auth/logout";

        httpController.sendGetRequestWithToken(request, securityToken);
    }

    /**
     * Requests the currently playing song from the server.
     * Constructs a requestURI and calls on HTTPControllerService to submit the request.
     * JSON-formatted response from HTTPController gets converted into Song object via Jackson.
     *
     * @param securityToken
     * @return Song object representing the currently playing song on the server.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException if there are IO issues.
     * @throws InterruptedException if there are thread issues.
     */
    @Override
    public Song getCurrentSong(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException
    {
        Song currentSong;
        String request = requestURI + "/player/current";
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder authResponse = new StringBuilder();

        authResponse.append(httpController.sendGetRequestWithToken(request, securityToken));
        currentSong = mapper.readValue(authResponse.toString(), Song.class);

        return currentSong;
    }

    /**
     * Requests the elapsed time in seconds of the currently playing song on the server.
     * Constructs a requestURI and calls on HTTPControllerService to submit the request.
     * Response is parsed into a double value and returned.
     *
     * @param securityToken
     * @return elapsed time of currently playing song on the server in seconds.
     * @throws AuthenticationFailedException if the token is invalid.
     * @throws IOException if there are IO issues.
     * @throws InterruptedException if there are thread issues.
     */
    @Override
    public double getCurrentSongElapsed(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException
    {
        double elapsed;
        String result;
        String request = requestURI + "/player/elapsed";
        StringBuilder authResponse = new StringBuilder();

        authResponse.append(httpController.sendGetRequestWithToken(request, securityToken));
        result = authResponse.toString();
        elapsed = Double.parseDouble(result);

        return elapsed;
    }

    /**
     * requestURI setter.
     *
     * @param requestURI
     */
    @Override
    public void setRequestURI(String requestURI)
    {
        this.requestURI = requestURI;
    }

    /**
     * clears requestURI.
     */
    @Override
    public void clearRequestURI()
    {
        this.requestURI = "";
    }
}
