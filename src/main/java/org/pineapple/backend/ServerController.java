package org.pineapple.backend;

import org.pineapple.backend.interfaces.HTTPControllerService;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.core.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerController extends ServerControllerService
{
    //localhost for testing purposes
    private String requestURI = "http://localhost:8080";

    public ServerController()
    {
        httpController = new HTTPControllerJavaNet();
    }

    @Override
    public boolean addSongToServerQueue(int songID)
    {
        return false;
    }

    @Override
    public List<Song> getServerQueue()
    {
        List<Song> queue = new ArrayList<>();
        requestURI += "/queue";

        return queue;
    }

    @Override
    public List<Song> getServerLibrary()
    {
        List<Song> library = new ArrayList<>();
        requestURI += "/library";

        return library;
    }

    @Override
    public String authenticate(String userEmail, String userPassword) throws AuthenticationFailedException
    {
        StringBuilder authResponse = new StringBuilder();
        requestURI += "/auth";

        //TODO: check if this can be done better with jackson, and change "userName" to "userEmail" once server-side got their shit together
        String requestBody = "{ \""+"userName"+"\""+" : "+"\""+userEmail+"\""+", "+"\""+"password"+"\""+" : "+"\""+userPassword+"\" }";

        try
        {
            authResponse.append(httpController.sendPostRequest(requestURI, requestBody));
        } catch(InterruptedException ex)
        {
            //TODO: handle these properly
        } catch(IOException ex)
        {

        }

        return authResponse.toString();
    }
}
