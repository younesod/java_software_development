package repository;

import java.io.IOException;
import java.util.List;

public interface Repository<T> {
    public void add(T item) throws IOException;
    public void remove(T item) throws IOException;
    public T get(T item) throws IOException;
    public List<T> getAll() throws IOException;
    public boolean contains(T item) throws IOException;
}
