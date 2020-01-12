package org.pineapple.backend.exceptions;

/**
 * Custom exception signifying that the server could not retrieve the requested Song.
 */
public class SongNotFoundException extends RuntimeException
{
    public SongNotFoundException(String reason)
    {
        super(reason);
    }
}
