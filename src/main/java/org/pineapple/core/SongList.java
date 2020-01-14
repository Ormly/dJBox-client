package org.pineapple.core;

import org.pineapple.core.interfaces.IMediaList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Both the Jukebox's queue and its library are represented by and handled as simple lists of songs. This List wrapper adds some functionality that is useful to the GUI layer.
 */
public class SongList implements IMediaList<Song>
{
    List<Song> songList;

    public SongList()
    {
        songList = new ArrayList<>();
    }

    /**
     * Returns all the media in the song list
     * @return List of songs
     */
    @Override
    public List<Song> getAllMedia()
    {
        return songList;
    }

    /**
     * Returns the song that corresponds to the id passed.
     * Returns null if data could not be extracted.
     * @param mediaID
     * @return Song
     */
    @Override
    public Optional<Song> getMedia(int mediaID)
    {
        for(Song song : songList)
            if(song.getId() == mediaID)
                return Optional.of(song);
        return Optional.empty();
    }

    /**
     * Generates a new song list from the list passed.
     * @param list
     */
    @Override
    public void setSongList(List<Song> list)
    {
        songList.clear();
        songList.addAll(list);
    }
}
