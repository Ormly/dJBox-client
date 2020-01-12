package org.pineapple.backend.exceptions;

/**
 * Signifies that the Jukebox-IP persistence fail does not contain a requested pair.
 */
public class JukeBoxIPNamePairNotFoundException extends RuntimeException
{
    public JukeBoxIPNamePairNotFoundException(String reason)
    {
        super(reason);
    }
}
