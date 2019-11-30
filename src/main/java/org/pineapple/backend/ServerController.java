package org.pineapple.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pineapple.backend.interfaces.HTTPControllerService;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.core.Song;

import java.io.IOException;
import java.util.ArrayList;
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
    {

    }

    @Override
    public List<Song> getServerQueueWithToken(String securityToken)
    {
        List<Song> queue = new ArrayList<>();
        String request = requestURI + "/queue";

        ObjectMapper mapper = new ObjectMapper();

        StringBuilder authResponse = new StringBuilder();
        String json;

        try
        {
            authResponse.append(httpController.sendGetRequestWithToken(request, securityToken));
            json = authResponse.toString();

            // https://www.mkyong.com/java/jackson-convert-json-array-string-to-list/
            // convert JSON array to List of objects
            queue = Arrays.asList(mapper.readValue(json, Song[].class));


        } catch(Exception e)
        {
            System.out.println("----------------------");
            System.out.println("Exception from ServerController--->  getServerQueueWithToken: ");
            System.out.println(e.getMessage());
            System.out.println("----------------------");
        }

        // Process json string

        return queue;
    }

    @Override
    public List<Song> getServerLibraryWithToken(String securityToken)
    {
        List<Song> library = new ArrayList<>();
        String request = requestURI + "/library";

        return library;
    }

    @Override
    public String authenticateUser(String userEmail, String userPassword)
    throws AuthenticationFailedException
    {
        StringBuilder authResponse = new StringBuilder();
        String request = requestURI + "/auth";

        //TODO: check if this can be done better with jackson, and change "userName" to "userEmail" once server-side got their shit together
        String requestBody = "{ \"" + "userName" + "\"" + " : " + "\"" + userEmail + "\"" + ", " + "\"" + "password" + "\"" + " : " + "\"" + userPassword + "\" }";

        try
        {
            authResponse.append(httpController.sendPostRequest(request, requestBody).allValues("token"));
        } catch(InterruptedException ex)
        {
            //TODO: handle these properly
        } catch(IOException ex)
        {

        }

        return authResponse.substring(1,authResponse.length()-1);
    }

    @Override
    public void logoutUser(String securityToken)
    {

    }
}
