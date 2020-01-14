package org.pineapple.backend.exceptions;

/**
 * Custom exception signifying that authentication with server has not succeeded.
 */
public class AuthenticationFailedException extends RuntimeException
{
    //TODO: improve
    public AuthenticationFailedException(String reason)
    {
        super(reason);
    }
}
