package org.pineapple.backend.interfaces;

import java.io.IOException;

public interface HTTPControllerService
{
    //TODO: fix return type
    public abstract String sendPostRequest(String requestURI, String requestBody)
    throws InterruptedException, IOException;

    public abstract String sendGetRequest(String requestURI);

    public abstract String sendHeadRequest(String requestURI);

    public abstract String sendGetRequestWithToken(String requestURI, String token);
}
