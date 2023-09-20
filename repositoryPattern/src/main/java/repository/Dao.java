package repository;

import java.io.IOException;
import java.util.List;

public interface Dao<T> {
    public void insert(T item) throws IOException;
    public void delete(T item) throws IOException;
    public void update(T item) throws IOException;
    public T get(T item) throws IOException;
    public List<T> getAll() throws IOException;
}
