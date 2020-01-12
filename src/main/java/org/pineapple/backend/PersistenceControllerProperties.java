package org.pineapple.backend;

import org.pineapple.backend.interfaces.PersistenceControllerService;
import org.pineapple.core.JukeBoxIPNamePair;

import java.util.List;
import java.util.Properties;

public class PersistenceControllerProperties implements PersistenceControllerService
{
    private Properties properties;
    private String rootPath;

    public PersistenceControllerProperties()
    {
        rootPath = "src/main/resources/jukeboxes.properties";
        properties = new Properties();
    }

    @Override
    public void writeEntryToPersistence(String key, String value)
    {

    }

    @Override
    public List<JukeBoxIPNamePair> readAllEntriesFromPersistence()
    {
        return null;
    }

    @Override
    public JukeBoxIPNamePair readEntryFromPersistence(String key)
    {
        return null;
    }

    @Override
    public void deleteAllEntriesFromPersistence()
    {

    }

    @Override
    public void deleteEntryFromPersistence(String key)
    {

    }
}
