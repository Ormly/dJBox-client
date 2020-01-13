package org.pineapple.backend.exceptions;

import org.pineapple.backend.interfaces.PersistenceControllerService;

public class PersistenceStoreException extends RuntimeException
{
    public PersistenceStoreException(String reason)
    {
        super(reason);
    }
}
