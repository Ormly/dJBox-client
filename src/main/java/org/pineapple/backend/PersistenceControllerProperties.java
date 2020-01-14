package org.pineapple.backend;

import org.pineapple.backend.exceptions.JukeBoxIPNamePairNotFoundException;
import org.pineapple.backend.exceptions.PersistenceStoreException;
import org.pineapple.backend.interfaces.PersistenceControllerService;
import org.pineapple.core.JukeBoxIPNamePair;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Exposes functionality to JukeBoxClient to store JukeBoxIPNamePair objects into persistent file storage via the native Properties API.
 */
public class PersistenceControllerProperties implements PersistenceControllerService
{
    private final Properties properties;
    private final String rootPath;
    private final List<JukeBoxIPNamePair> jukeBoxIPNamePairs;

    /**
     * Initializes all fields and loads data from Properties persistence file into Properties member.
     *
     * @param rootPath path to Properties file.
     * @throws IOException if File could not be read.
     */
    public PersistenceControllerProperties(String rootPath)
    throws IOException
    {
        this.rootPath = rootPath;
        properties = new Properties();
        jukeBoxIPNamePairs = new ArrayList<>();
        properties.load(new FileReader(rootPath));
    }

    /**
     * Stores given key-value pair to properties member.
     *
     * @param key name of JukeBoxIPNamePair to be stored.
     * @param value ip of JukeBoxIPNamePair to be stored.
     */
    @Override
    public void writeEntryToPersistence(String key, String value)
    {
        properties.setProperty(key,value);
    }

    /**
     * Fetches all entries from properties member, stores them in member List and returns the list.
     *
     * @return List containing all JukeBoxIPNamePairs in persistent storage.
     */
    @Override
    public List<JukeBoxIPNamePair> readAllEntriesFromPersistence()
    {
        jukeBoxIPNamePairs.clear();

        Set<String> keys = properties.stringPropertyNames();
        for(String key : keys)
            jukeBoxIPNamePairs.add(new JukeBoxIPNamePair(key, properties.getProperty(key)));

        return jukeBoxIPNamePairs;
    }

    /**
     * Fetches entry specified by key from properties member, stores it in a JukeBoxIPNamePair object and returns it.
     * May return null if key is not found in persistent storage.
     *
     * @param key name of JukeBoxIPNamePair to be fetched.
     * @return JukeBoxIPNamePair object corresponding to key parameter.
     */
    @Override
    public JukeBoxIPNamePair readEntryFromPersistence(String key)
    {
        String name = properties.getProperty(key);
        if(name == null)
            throw new JukeBoxIPNamePairNotFoundException("Requested pair not in persistence file.");
        else
            return new JukeBoxIPNamePair(key,name);
    }

    /**
     * Clears all entries from member properties object and member List.
     */
    @Override
    public void deleteAllEntriesFromPersistence()
    {
        properties.clear();
        jukeBoxIPNamePairs.clear();
    }

    /**
     * Removes entry specified by key from properties member.
     *
     * @param key name of JukeBoxIPNamePair to be deleted.
     */
    @Override
    public void deleteEntryFromPersistence(String key)
    {
        properties.remove(key);
    }

    /**
     * Edits name of entry specified by key to newName.
     *
     * @param key name of JukeBoxIPNamePair to be edited.
     * @param newName name which is to replace the old name.
     */
    @Override
    public void editEntryName(String key, String newName)
    {
        String ip = properties.getProperty(key);
        deleteEntryFromPersistence(key);
        writeEntryToPersistence(newName,ip);
    }

    /**
     * Edits ip of entry specified by key to newIP.
     *
     * @param key name of JukeBoxIPNamePair to be changed.
     * @param newIP ip which is to replace the old ip.
     */
    @Override
    public void editEntryIP(String key, String newIP)
    {
        writeEntryToPersistence(key,newIP);
    }

    /**
     * Stores key-value pair contents of property member to jukeboxes.properties file in resource folder.
     */
    public void makePersistent()
    {
        try
        {
            FileWriter fileWriter = new FileWriter(rootPath);
            properties.store(fileWriter,"Storing PersistenceControllerProperties to File.");
        }
        catch(IOException ex)
        {
            throw new PersistenceStoreException(ex.getMessage());
        }
    }
}
