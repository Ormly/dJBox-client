package org.pineapple.backend;

import org.pineapple.backend.interfaces.HTTPControllerService;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;


public class HTTPControllerJavaNet implements HTTPControllerService
{
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;

    public HTTPControllerJavaNet()
    {
        client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    @Override
    public String sendPostRequest(String requestURI, String requestBody) throws InterruptedException, IOException, AuthenticationFailedException
    {
        //TODO: fix exception handling

        request = HttpRequest.newBuilder().uri(URI.create(requestURI)).header("Content-Type","application/json").POST(BodyPublishers.ofString(requestBody)).build();
        response = client.send(request, BodyHandlers.ofString());
        int responseStatusCode = response.statusCode();

        //TODO: make this not suck
        if(responseStatusCode == 200)
            return response.body();
        else
            throw new AuthenticationFailedException(String.valueOf(responseStatusCode));
    }

    @Override
    public String sendGetRequest(String requestURI)
    {
        return "";
    }

    @Override
    public String sendHeadRequest(String requestURI)
    {
        //TODO: implement this if needed by server-side
        return "";
    }
}
