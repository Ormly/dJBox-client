package org.pineapple.backend.interfaces;

import org.pineapple.backend.AuthenticationFailedException;
import org.pineapple.core.Song;

import java.util.List;

public abstract class ServerControllerService
{
    protected HTTPControllerService httpController;

    //TODO: return type in case of success/failure? Parse and compare http status code?
    public abstract boolean addSongToServerQueue(int songID);
    public abstract List<Song>getServerQueue();
    public abstract List<Song> getServerLibrary();
    public abstract String authenticate(String userEmail, String userPassword) throws AuthenticationFailedException;
}
