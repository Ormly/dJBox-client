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
    private String coverArtURL;

    /**
     * Generates a new empty song object.
     */
    public Song() {}

    /**
     * Generates a new song object.
     *
     * @param id
     * @param title
     * @param artist
     * @param album
     * @param genre
     * @param year
     * @param duration
     * @param coverArtURL
     */
    public Song(int id,
                String title,
                String artist,
                String album,
                String genre,
                int year,
                int duration,
                String coverArtURL)
    {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.year = year;
        this.duration = duration;
        this.coverArtURL = coverArtURL;
    }

    /**
     * Returns the id of the song.
     * @return int id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Returns the title of the song.
     * @return String title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Returns the artist of the song.
     * @return String artist
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * Returns the album of the song.
     * @return String album
     */
    public String getAlbum()
    {
        return album;
    }

    /**
     * Returns the genre of the song.
     * @return String genre
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * Returns the year of when the song was made.
     * @return int year
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Returns the duration of the song.
     * @return int duration
     */
    public int getDuration()
    {
        return duration;
    }

    /**
     * Returns the cover art URL of the song.
     * @return String coverArtURL
     */
    public String getCoverArtURL() { return coverArtURL; }
}
