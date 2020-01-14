package org.pineapple.backend.exceptions;

/**
 * Custom exception signifying that the Jukebox-IP persistence fail does not contain a requested pair.
 */
public class JukeBoxIPNamePairNotFoundException extends RuntimeException
{
    public JukeBoxIPNamePairNotFoundException(String reason)
    {
        super(reason);
    }
}
