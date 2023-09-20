package atl.g54314.model.repository;

import atl.g54314.model.dao.StopDao;
import atl.g54314.model.dto.StopDto;
import atl.g54314.model.exception.RepositoryException;
import javafx.util.Pair;

import java.util.List;

/**
 * 
 * The StopRepository class is responsible for interacting with the StopDao
 * 
 * to perform operations on StopDto objects.
 */
public class StopRepository implements Repository<Pair<Integer, Integer>, StopDto> {
    private final StopDao dao;

    /**
     * 
     * Constructs a StopRepository object with the specified StopDao instance.
     * 
     * @param dao the StopDao instance
     */
    public StopRepository(StopDao dao) {
        this.dao = dao;
    }

    /**
     * 
     * Constructs a StopRepository object with the default StopDao instance.
     * 
     * @throws RepositoryException if a repository exception occurs
     */
    public StopRepository() throws RepositoryException {
        dao = StopDao.getInstance();
    }

    @Override
    public Pair<Integer, Integer> add(StopDto item) throws RepositoryException {
        return dao.insert(item);
    }

    @Override
    public void update(StopDto item) throws RepositoryException {
        dao.update(item);
    }

    @Override
    public void remove(Pair<Integer, Integer> key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopDto get(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key);
    }

    /**
     * 
     * Retrieves a list of StopDto objects with the same key.
     * 
     * @param key the key to search for
     * @return a list of StopDto objects with the same key
     * @throws RepositoryException if a repository exception occurs
     */
    public List<StopDto> getSame(Integer key) throws RepositoryException {
        return dao.selectSame(key);
    }

    /**
     * 
     * Retrieves a list of StopDto objects adjacent to the specified list of stops.
     * 
     * @param stops the list of stops
     * @return a list of StopDto objects adjacent to the specified stops
     * @throws RepositoryException if a repository exception occurs
     */
    public List<StopDto> getStopAdjacent(List<StopDto> stops) throws RepositoryException {
        return dao.selectStopAdjacent(stops);
    }

}
