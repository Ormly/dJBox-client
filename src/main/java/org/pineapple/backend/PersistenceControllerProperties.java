package org.pineapple.backend;

import org.pineapple.backend.exceptions.JukeBoxIPNamePairNotFoundException;
import org.pineapple.backend.interfaces.PersistenceControllerService;
import org.pineapple.core.JukeBoxIPNamePair;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PersistenceControllerProperties implements PersistenceControllerService
{
    private final Properties properties;
    private final String rootPath;
    private final List<JukeBoxIPNamePair> jukeBoxIPNamePairs;


    public PersistenceControllerProperties(String rootPath)
    throws IOException
    {
        this.rootPath = rootPath;
        properties = new Properties();
        jukeBoxIPNamePairs = new ArrayList<>();
        properties.load(new FileReader(rootPath));
    }

    @Override
    public void writeEntryToPersistence(String key, String value)
    {
        properties.setProperty(key,value);
    }

    @Override
    public List<JukeBoxIPNamePair> readAllEntriesFromPersistence()
    {
        jukeBoxIPNamePairs.clear();

        Set<String> keys = properties.stringPropertyNames();
        for(String key : keys)
            jukeBoxIPNamePairs.add(new JukeBoxIPNamePair(key, properties.getProperty(key)));

        return jukeBoxIPNamePairs;
    }

    @Override
    public JukeBoxIPNamePair readEntryFromPersistence(String key)
    {
        String name = properties.getProperty(key);
        if(name == null)
            throw new JukeBoxIPNamePairNotFoundException("Requested pair not in persistence file.");
        else
            return new JukeBoxIPNamePair(key,name);
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
        writeEntryToPersistence(key,newIP);
    }

    public void storeToFile()
    {
        try
        {
            FileWriter fileWriter = new FileWriter(rootPath);
            properties.store(fileWriter,"Storing PersistenceControllerProperties to File.");
        }
        catch(IOException ex)
        {
            //...
        }

    }
}
