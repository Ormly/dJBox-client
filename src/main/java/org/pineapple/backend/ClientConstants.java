package org.pineapple.backend;

import java.io.File;

public final class ClientConstants
{
    public static final int GENERAL_ERROR_CODE = 500;
    public static final int SONG_NOT_FOUND_ERROR_CODE = 400;
    public static final int AUTH_FAILURE_ERROR_CODE = 401;
    public static final String NONSENSE_USER_DATA = "rO)1Vr47yY1kdi1<o=RQ";
    public static final int REGISTRATION_FAILURE_ERROR_CODE = 400;
    public static final int NO_CURRENT_SONG_ERROR_CODE = 204;

    public static final String RESOURCES_FOLDER_PATH = "src/main/resources/jukeboxes.properties";

    private ClientConstants()
    {

    };
}
