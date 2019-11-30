package org.pineapple.backend.interfaces;

import org.pineapple.backend.AuthenticationFailedException;
import org.pineapple.core.Song;

import java.util.List;

public abstract class ServerControllerService
{
    protected HTTPControllerService httpController;

    //TODO: return type in case of success/failure? Parse and compare http status code?
    public abstract void addSongToServerQueue(int songID, String securityToken);

    public abstract void logoutUser(String securityToken);

    public abstract List<Song> getServerQueueWithToken(String securityToken);

    public abstract List<Song> getServerLibraryWithToken(String securityToken);

    public abstract String authenticateUser(String userEmail, String userPassword)
    throws AuthenticationFailedException;
}
