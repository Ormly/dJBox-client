package org.pineapple.core;

public class Song
{
    private int id;
    private String title;
    private String artist;
    private String album;
    private int year;
    private String genre;
    private String pathToFile;

    public Song(int id, int year, String title, String artist, String album, String genre, String pathToFile)
    {
        this.id = id;
        this.year = year;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.pathToFile = pathToFile;
    }

    public int getId()
    {
        return id;
    }

    public int getYear()
    {
        return year;
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

    public String getPathToFile()
    {
        return pathToFile;
    }
}
