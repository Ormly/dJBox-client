package org.pineapple.backend.interfaces;

import org.pineapple.core.JukeBoxIPNamePair;

import java.util.List;

public interface PersistenceControllerService
{
    public void writeEntryToPersistence(String key, String value);

    public List<JukeBoxIPNamePair> readAllEntriesFromPersistence();

    public JukeBoxIPNamePair readEntryFromPersistence(String key);

    public void deleteAllEntriesFromPersistence();

    public void deleteEntryFromPersistence(String key);
}
