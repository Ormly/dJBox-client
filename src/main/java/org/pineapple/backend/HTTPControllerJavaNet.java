package org.pineapple.backend;

import org.pineapple.backend.interfaces.HTTPControllerService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;


public class HTTPControllerJavaNet implements HTTPControllerService
{
    HttpClient client;

    public HTTPControllerJavaNet()
    {
        client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    @Override
    public HttpHeaders sendPostRequest(String requestURI, String requestBody)
    throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestURI)).header("Content-Type",
                                                                                          "application/json").POST(
                BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int responseStatusCode = response.statusCode();

        if(responseStatusCode == 200)
            return response.headers();
        else

            throw new AuthenticationFailedException(String.valueOf(responseStatusCode));
    }

    @Override
    public String sendGetRequestWithToken(String requestURI, String token)
    throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestURI)).header("token", token).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int responseStatusCode = response.statusCode();

        if(responseStatusCode == 200)
            return response.body();
        else
            throw new AuthenticationFailedException(String.valueOf(responseStatusCode));
    }

    @Override
    public String sendHeadRequest(String requestURI)
    {
        //TODO: implement this if needed by server-side
        return "";
    }
}
