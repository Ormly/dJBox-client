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

    @Override
    public List<Song> getAllMedia()
    {
        return songList;
    }

    @Override
    public Optional<Song> getMedia(int mediaID)
    {
        for(Song song : songList)
            if(song.getId() == mediaID)
                return Optional.of(song);
        return Optional.empty();
    }

    @Override
    public void setSongList(List<Song> list)
    {
        songList.clear();
        songList.addAll(list);
    }
}
