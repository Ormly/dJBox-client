package org.pineapple.backend.interfaces;

import org.pineapple.core.JukeBoxIPNamePair;

import java.io.IOException;
import java.util.List;

public abstract class PersistenceControllerService
{
    protected List<JukeBoxIPNamePair> jukeBoxIPNamePairs;

    public abstract void writeEntryToPersistence(String key, String value);

    public abstract List<JukeBoxIPNamePair> readAllEntriesFromPersistence();

    public abstract JukeBoxIPNamePair readEntryFromPersistence(String key);

    public abstract void deleteAllEntriesFromPersistence();

    public abstract void deleteEntryFromPersistence(String key);

    public abstract void editEntryName(String oldName, String newName);

    public abstract void editEntryIP(String key, String newIP);
}
