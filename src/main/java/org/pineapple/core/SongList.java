package org.pineapple.core;

import org.pineapple.core.interfaces.IMediaList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SongList implements IMediaList<Song>
{
    //TODO: implement Library constructor

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
