package org.pineapple.backend.interfaces;

import java.io.IOException;
import java.net.http.HttpHeaders;

public interface HTTPControllerService
{
    //TODO: fix return type
    public abstract HttpHeaders sendPostRequest(String requestURI, String requestBody)
    throws InterruptedException, IOException;

    public abstract String sendGetRequest(String requestURI);

    public abstract String sendHeadRequest(String requestURI);

    public abstract String sendGetRequestWithToken(String requestURI, String token)
    throws IOException, InterruptedException;
}
