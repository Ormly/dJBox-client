package org.pineapple.backend;

import org.pineapple.backend.interfaces.HTTPControllerService;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

/**
 * Offers HTTP connectivity to ServerController.
 */
public class HTTPControllerJavaNet implements HTTPControllerService
{
    HttpClient client;

    /**
     * Initiates a JavaNet specific HttpClient with a timeout set at 20 seconds.
     */
    public HTTPControllerJavaNet()
    {
        client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    /**
     * Builds JavaNet Post HttpRequest request with given header/body and sends it via member HttpClient to given URI.
     * Returns valid response contents (JSON formatted), or otherwise throws AuthenticationFailedException.
     *
     * @param requestURI URI address for the post request.
     * @param requestBody Body to be sent to the URI.
     * @return The header associated with the Post response.
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public HttpHeaders sendPostRequest(String requestURI, String requestBody)
    throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestURI)).header("Content-Type",
                                                                                          "application/json").POST(
                BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int responseStatusCode = response.statusCode();
        if(responseStatusCode == ClientConstants.GENERAL_ERROR_CODE)
            throw new GeneralServerIssueException(String.valueOf(responseStatusCode));
        else if(responseStatusCode == ClientConstants.AUTH_FAILURE_ERROR_CODE)
            throw new AuthenticationFailedException(String.valueOf(responseStatusCode));

        return response.headers();
    }

    /**
     * Builds JavaNet Get HttpRequest request with given header and sends it via member HttpClient to given URI.
     * Returns valid response contents (JSON formatted), or otherwise throws AuthenticationFailedException.
     *
     * @param requestURI URI address for the get request.
     * @param token needs to be passed to identify user.
     * @return String representation of response body.
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public String sendGetRequestWithToken(String requestURI, String token)
    throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestURI)).header("token", token).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int responseStatusCode = response.statusCode();
        if(responseStatusCode == ClientConstants.GENERAL_ERROR_CODE)
            throw new GeneralServerIssueException(String.valueOf(responseStatusCode));
        else if(responseStatusCode == ClientConstants.SONG_NOT_FOUND_ERROR_CODE)
            throw new SongNotFoundException(String.valueOf(responseStatusCode));
        else if(responseStatusCode == ClientConstants.AUTH_FAILURE_ERROR_CODE)
            throw new AuthenticationFailedException(String.valueOf(responseStatusCode));

        return response.body();
    }

    @Override
    public String sendHeadRequest(String requestURI)
    {
        //TODO: implement this if needed by server-side
        return "";
    }
}
