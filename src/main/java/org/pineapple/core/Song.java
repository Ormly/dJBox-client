package org.pineapple.core;

/**
 * Represents a song and its associated data.
 */
public class Song
{
    private int id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private int year;
    private int duration;

    public Song() {}

    public Song(int id,
                String title,
                String artist,
                String album,
                String genre,
                int year,
                int duration)
    {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.year = year;
        this.duration = duration;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getArtist()
    {
        return artist;
    }

    public String getAlbum()
    {
        return album;
    }

    public String getGenre()
    {
        return genre;
    }

    public int getYear()
    {
        return year;
    }

    public int getDuration()
    {
        return duration;
    }
}
