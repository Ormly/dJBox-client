package org.pineapple.backend;

/**
 * Custom exception signifying that an unspecific error occured on the server-side.
 */
public class GeneralServerIssueException extends RuntimeException
{
    //TODO: improve
    public GeneralServerIssueException(String reason)
    {
        super(reason);
    }
}
