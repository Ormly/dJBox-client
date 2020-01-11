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
    private String pathToFile;
    private String coverArtURL;

    public Song() {}

    public Song(int id,
                String title,
                String artist,
                String album,
                String genre,
                int year,
                int duration,
                String pathToFile,
                String coverArtURL)
    {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.year = year;
//        this.duration = duration;
        this.pathToFile = pathToFile;
        this.coverArtURL = coverArtURL;
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

    public String getPathToFile()
    {
        return pathToFile;
    }

    public String getCoverArtURL() { return coverArtURL; }
}
