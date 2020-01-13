package org.pineapple.backend.interfaces;

import org.pineapple.core.JukeBoxIPNamePair;

import java.io.IOException;
import java.util.List;

/**
 * Interface for any service offering persistent storage for JukeBoxIPNamePair objects curated by JukeBoxClient.
 */
public interface PersistenceControllerService
{
    /**
     * Writes a key-value pair representing a JukeBoxIPNamePair object to persistent storage.
     *
     * @param key name of JukeBoxIPNamePair to be stored.
     * @param value ip of JukeBoxIPNamePair to be stored.
     */
    void writeEntryToPersistence(String key, String value);

    /**
     * Fetches all key-value pairs representing JukeBoxIPNamePair objects from persistent storage.
     *
     * @return all key-value pairs representing JukeBoxIPNamePair objects stored in persistent storage.
     */
    List<JukeBoxIPNamePair> readAllEntriesFromPersistence();

    /**
     * Fetches a single JukeBoxIPNamePair, identified by key, from persistent storage.
     *
     * @param key name of JukeBoxIPNamePair to be fetched.
     * @return JukeBoxIPNamePair correspnonding to passed key.
     */
    JukeBoxIPNamePair readEntryFromPersistence(String key);

    /**
     * Deletes all key-value pairs representing JukeBoxIPNamePair objects from persistent storage.
     */
    void deleteAllEntriesFromPersistence();

    /**
     * Deletes a single JukeBoxIPNamePair, identified by key, from persistent storage.
     *
     * @param key name of JukeBoxIPNamePair to be deleted.
     */
    void deleteEntryFromPersistence(String key);

    /**
     * Changes the name of a JukeBoxIPNamePair, identified by key, in persistent storage, to newName.
     *
     * @param key name of JukeBoxIPNamePair to be changed.
     * @param newName name which is to replace the old name.
     */
    void editEntryName(String key, String newName);

    /**
     * Changes the ip of a JukeBoxIPNamePair, identified by key, in persistent storage, to newIP.
     *
     * @param key name of JukeBoxIPNamePair to be changed.
     * @param newIP ip which is to replace the old ip.
     */
    void editEntryIP(String key, String newIP);

    /**
     * Make changes to local intermediate objects persistent.
     */
    void makePersistent();
}
