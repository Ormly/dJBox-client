package org.pineapple.backend.interfaces;

import java.io.IOException;
import java.net.http.HttpHeaders;

/**
 * Interface for any service offering http-based connectivity to the Jukebox.
 */
public interface HTTPControllerService
{
    /**
     * Sends a post request containing the given body to the given URI.
     *
     * @param requestURI URI address for the post request.
     * @param requestBody Body to be sent to the URI.
     * @return The header associated with the Post response.
     * @throws InterruptedException
     * @throws IOException
     */
    HttpHeaders sendPostRequest(String requestURI, String requestBody)
    throws InterruptedException, IOException;

    String sendHeadRequest(String requestURI);

    /**
     * Sends a get request containing the given token in the header to the given URI.
     *
     * @param requestURI URI address for the get request.
     * @param token needs to be passed to identify user.
     * @return String representation of response body.
     * @throws IOException
     * @throws InterruptedException
     */
    String sendGetRequestWithToken(String requestURI, String token)
    throws IOException, InterruptedException;
}
