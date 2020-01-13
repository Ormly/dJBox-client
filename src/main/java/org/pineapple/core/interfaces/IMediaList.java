package org.pineapple.core.interfaces;

import org.pineapple.core.Song;
import java.util.List;
import java.util.Optional;


/**
 * Interface for any object offering to manage media of any kind.
 */
public interface IMediaList<T>
{
    public List<T> getAllMedia();
    public Optional<T> getMedia(int mediaID);
    public void setSongList(List<T> list);
}
