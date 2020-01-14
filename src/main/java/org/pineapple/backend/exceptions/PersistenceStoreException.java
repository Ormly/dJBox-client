package org.pineapple.backend.exceptions;

import org.pineapple.backend.interfaces.PersistenceControllerService;

/**
 * Custom exception signifying that something went wrong during the storing process of the Name-IP persistence.
 */
public class PersistenceStoreException extends RuntimeException
{
    public PersistenceStoreException(String reason)
    {
        super(reason);
    }
}
