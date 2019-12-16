package org.pineapple.backend;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.backend.interfaces.HTTPControllerService;
import org.pineapple.core.Song;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

/**
 * Exposes methods fetching server data to JukeBoxClient.
 */
public class ServerController extends ServerControllerService
{
    private String requestURI;

    public ServerController(HTTPControllerService httpController)
    {
        this.httpController = httpController;
    }

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
     * @throws IOException
     * @throws InterruptedException
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
     * @throws IOException
     * @throws InterruptedException
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

    @Override
    public void logoutUser(String securityToken)
    throws AuthenticationFailedException, GeneralServerIssueException, IOException, InterruptedException
    {
        String request = requestURI + "/auth/logout";

        httpController.sendGetRequestWithToken(request, securityToken);
    }

    //TODO: change this it's horrible
    @Override
    public void setRequestURI(String requestURI)
    {
        this.requestURI = requestURI;
    }
}
