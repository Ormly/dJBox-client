package org.pineapple.backend;

public class AuthenticationFailedException extends RuntimeException
{
    //TODO: improve
    public AuthenticationFailedException(String reason)
    {
        super(reason);
    }
}
