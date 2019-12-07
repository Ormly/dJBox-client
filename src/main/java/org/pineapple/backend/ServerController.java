package org.pineapple.backend;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.core.Song;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;


public class ServerController extends ServerControllerService
{
    //localhost for testing purposes
    final private String requestURI = "http://localhost:8080";

    public ServerController()
    {
        httpController = new HTTPControllerJavaNet();
    }

    @Override
    public void addSongToServerQueue(int songID, String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException
    {
        String request = requestURI + "/queue/add/" + songID;

        httpController.sendGetRequestWithToken(request, securityToken);
    }

    @Override
    public List<Song> getServerQueueWithToken(String securityToken)
    throws IOException, InterruptedException, AuthenticationFailedException
    {
        List<Song> queue;
        String request = requestURI + "/queue";
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder authResponse = new StringBuilder();

        authResponse.append(httpController.sendGetRequestWithToken(request, securityToken));

        queue = Arrays.asList(mapper.readValue(authResponse.toString(), Song[].class));

        return queue;
    }

    @Override
    public List<Song> getServerLibraryWithToken(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException
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
    throws IOException, InterruptedException, AuthenticationFailedException
    {
        StringBuilder authResponse = new StringBuilder();
        String request = requestURI + "/auth";
        //TODO: reconsider this bullshit
        String requestBody = "{ \"" + "userName" + "\"" + " : " + "\"" + userEmail + "\"" + ", " + "\"" + "password" + "\"" + " : " + "\"" + userPassword + "\" }";

        HttpHeaders headers = httpController.sendPostRequest(request, requestBody);

        authResponse.append(headers.allValues("token"));

        //TODO: have server-side fix this
        return authResponse.substring(1, authResponse.length() - 1);
    }

    @Override
    public void logoutUser(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException
    {
        String request = requestURI + "/auth/logout";

        httpController.sendGetRequestWithToken(request, securityToken);
    }
}
