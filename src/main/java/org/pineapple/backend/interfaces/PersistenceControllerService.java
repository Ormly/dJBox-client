package org.pineapple.backend.interfaces;

import org.pineapple.core.JukeBoxIPNamePair;

import java.io.IOException;
import java.util.List;

public interface PersistenceControllerService
{
    void writeEntryToPersistence(String key, String value);

    List<JukeBoxIPNamePair> readAllEntriesFromPersistence();

    JukeBoxIPNamePair readEntryFromPersistence(String key);

    void deleteAllEntriesFromPersistence();

    void deleteEntryFromPersistence(String key);

    void editEntryName(String oldName, String newName);

    void editEntryIP(String key, String newIP);

    void storeToFile();
}
