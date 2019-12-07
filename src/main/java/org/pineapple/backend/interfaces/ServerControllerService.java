package org.pineapple.backend.interfaces;

import org.pineapple.backend.AuthenticationFailedException;
import org.pineapple.core.Song;

import java.io.IOException;
import java.util.List;

public abstract class ServerControllerService
{
    protected HTTPControllerService httpController;

    public abstract void addSongToServerQueue(int songID, String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException;

    public abstract void logoutUser(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException;

    public abstract List<Song> getServerQueueWithToken(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException;

    public abstract List<Song> getServerLibraryWithToken(String securityToken)
    throws AuthenticationFailedException, IOException, InterruptedException;

    public abstract String authenticateUser(String userEmail, String userPassword)
    throws AuthenticationFailedException, IOException, InterruptedException;

    public abstract void setRequestURI(String requestURI);
}
