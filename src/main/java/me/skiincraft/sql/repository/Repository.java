package me.skiincraft.sql.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    Optional<T> get(int index);
    Optional<T> getById(ID id);
    List<T> getAll();
    boolean contains(T item);

    long size();
    void remove(int index);
    void remove(T index);
    void save(T item);

}
