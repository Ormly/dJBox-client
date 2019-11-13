/*
This interface allows decoupling Business layer logic objects from their persistence layer implementation.
source: https://www.baeldung.com/java-dao-pattern
 */
package org.pineapple.backend.interfaces;

import java.util.List;
import java.util.Optional;

public interface DAO<T>
{
    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);
}
