package org.pineapple.backend;

import org.pineapple.backend.exceptions.JukeBoxIPNamePairNotFoundException;
import org.pineapple.backend.interfaces.PersistenceControllerService;
import org.pineapple.core.JukeBoxIPNamePair;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PersistenceControllerProperties extends PersistenceControllerService
{
    private Properties properties;
    private String rootPath;

    public PersistenceControllerProperties()
    throws IOException
    {
        rootPath = ClientConstants.RESOURCES_FOLDER_PATH;
        properties = new Properties();
        jukeBoxIPNamePairs = new ArrayList<>();
        properties.load(new FileReader(rootPath));
    }

    @Override
    public void writeEntryToPersistence(String key, String value)
    {
        properties.setProperty(key,value);
        addNewPairToList(key,value);
    }

    @Override
    public List<JukeBoxIPNamePair> readAllEntriesFromPersistence()
    {
        return jukeBoxIPNamePairs;
    }

    @Override
    public JukeBoxIPNamePair readEntryFromPersistence(String key)
    {
        for(JukeBoxIPNamePair pair : jukeBoxIPNamePairs)
            if(pair.getJukeBoxName().equals(key))
                return pair;

        throw new JukeBoxIPNamePairNotFoundException("Requested pair not in persistence file.");
    }

    @Override
    public void deleteAllEntriesFromPersistence()
    {
        properties.clear();
        jukeBoxIPNamePairs.clear();
    }

    @Override
    public void deleteEntryFromPersistence(String key)
    {
        properties.remove(key);
        deleteEntryFromList(key);
    }

    @Override
    public void editEntryName(String oldName, String newName)
    {
        String ip = properties.getProperty(oldName);
        deleteEntryFromPersistence(oldName);
        writeEntryToPersistence(newName,ip);
    }

    @Override
    public void editEntryIP(String key, String newIP)
    {
        properties.setProperty(key,newIP);
        deleteEntryFromList(key);
        addNewPairToList(key,newIP);
    }

    private void deleteEntryFromList(String key)
    {
        for(JukeBoxIPNamePair pair : jukeBoxIPNamePairs)
            if(pair.getJukeBoxName().equals(key))
                jukeBoxIPNamePairs.remove(pair);
    }

    private void addNewPairToList(String key, String value)
    {
        JukeBoxIPNamePair newPair = new JukeBoxIPNamePair(key,value);
        jukeBoxIPNamePairs.add(newPair);
    }
}
