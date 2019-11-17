package org.pineapple.backend;

public class AuthenticationFailedException extends RuntimeException
{
    public AuthenticationFailedException(String reason)
    {
        super(reason);
    }
}
