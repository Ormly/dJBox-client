package org.pineapple.core;

/**
 * Wraps a song object and adds an elapsed member, representing the elapsed time in seconds.
 */
public class CurrentSong
{
    private Song currentSong;
    private double elapsed;

    public CurrentSong()
    {
        elapsed = 0.0;
    }

    public int getId()
    {
        return currentSong.getId();
    }

    public String getTitle()
    {
        return currentSong.getTitle();
    }

    public String getArtist()
    {
        return currentSong.getArtist();
    }

    public String getAlbum()
    {
        return currentSong.getAlbum();
    }

    public String getGenre()
    {
        return currentSong.getGenre();
    }

    public int getYear()
    {
        return currentSong.getYear();
    }

    public int getDuration()
    {
        return currentSong.getDuration();
    }

    public Song song()
    {
        return currentSong;
    }

    public double getElapsed()
    {
        return elapsed;
    }

    public void setElapsed(double value)
    {
        elapsed = value;
    }

    public void setSong(Song song)
    {
        currentSong = song;
    }
}
