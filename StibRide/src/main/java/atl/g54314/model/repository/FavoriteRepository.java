package atl.g54314.model.repository;

import atl.g54314.model.dao.FavoriteDao;
import atl.g54314.model.dto.FavoriteDto;
import atl.g54314.model.exception.RepositoryException;

import java.util.List;

/**
 * 
 * The FavoriteRepository class is responsible for interacting with the
 * FavoriteDao
 * 
 * to perform operations on FavoriteDto objects.
 */
public class FavoriteRepository implements Repository<String, FavoriteDto> {
    private final FavoriteDao dao;

    /**
     * 
     * Constructs a FavoriteRepository object with the default FavoriteDao instance.
     * 
     * @throws RepositoryException if a repository exception occurs
     */
    public FavoriteRepository() throws RepositoryException {
        this.dao = FavoriteDao.getInstance();
    }

    /**
     * 
     * Constructs a FavoriteRepository object with the specified FavoriteDao
     * instance.
     * 
     * @param dao the FavoriteDao instance
     */
    public FavoriteRepository(FavoriteDao dao) {
        this.dao = dao;
    }

    @Override
    public String add(FavoriteDto item) throws RepositoryException {
        if (item.getKey().equals("")) {
            throw new RepositoryException("Name cannot be empty");
        }
        return dao.insert(item);
    }

    @Override
    public void update(FavoriteDto item) throws RepositoryException {
        if (item.getKey().equals("")) {
            throw new RepositoryException("Name cannot be empty");
        }
        dao.update(item);
    }

    @Override
    public void remove(String key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<FavoriteDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavoriteDto get(String key) throws RepositoryException {
        return dao.select(key);
    }
}
