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
    HttpRequest request;
    HttpResponse<String> response;

    public HTTPControllerJavaNet()
    {
        client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    @Override
    public HttpHeaders sendPostRequest(String requestURI, String requestBody)
    throws InterruptedException, IOException, AuthenticationFailedException
    {
        //TODO: fix exception handling

        request = HttpRequest.newBuilder().uri(URI.create(requestURI)).header("Content-Type", "application/json").POST(
                BodyPublishers.ofString(requestBody)).build();
        response = client.send(request, BodyHandlers.ofString());
        int responseStatusCode = response.statusCode();

        //TODO: make this not suck
        if(responseStatusCode == 200)
//            return response.body();
            return response.headers();

        else
            throw new AuthenticationFailedException(String.valueOf(responseStatusCode));
    }

    @Override
    public String sendGetRequest(String requestURI)
    {
        return "";

    }

    @Override
    public String sendGetRequestWithToken(String requestURI, String token)
    {
        request = HttpRequest.newBuilder().uri(URI.create(requestURI)).header("token", token).GET().build();

        try
        {

            response = client.send(request, BodyHandlers.ofString());
            int responseStatusCode = response.statusCode();

            if(responseStatusCode == 200)
                return response.body();
            else
                throw new AuthenticationFailedException(String.valueOf(responseStatusCode));

        } catch(Exception e) // To handle the exception from send()
        {
            System.out.println("----------------------");
            System.out.println("Exception in HTTPControllerJavaNet --> sendGetRequestWithToken:");
            System.out.println(e.getMessage());
            System.out.println("----------------------");

        }

        return "Fail on sendGetRequestWithToken";

    }

    @Override
    public String sendHeadRequest(String requestURI)
    {
        //TODO: implement this if needed by server-side
        return "";
    }
}
