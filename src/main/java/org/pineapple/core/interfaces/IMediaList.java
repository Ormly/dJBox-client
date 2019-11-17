package org.pineapple.core.interfaces;

import org.pineapple.core.Song;
import java.util.List;
import java.util.Optional;

public interface IMediaList<T>
{
    public List<T> getAllMedia();
    public Optional<T> getMedia(int mediaID);
}
