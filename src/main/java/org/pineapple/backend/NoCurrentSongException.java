package org.pineapple.backend;

/**
 * Custom exception signifying that the server is not currently playing any song.
 */
public class NoCurrentSongException extends RuntimeException
{
    public NoCurrentSongException(String reason)
    {
        super(reason);
    }
}

