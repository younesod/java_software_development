package atl.g54314.model.dao;

import atl.g54314.model.dto.FavoriteDto;
import atl.g54314.model.dto.StationDto;
import atl.g54314.model.exception.RepositoryException;
import atl.g54314.model.jdbc.DBManager;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The FavoriteDao class provides methods to insert, delete, update, and retrieve FavoriteDto objects from the database.
 */
public class FavoriteDao implements Dao<String, FavoriteDto> {
    private final Connection connexion;

    /**
     * Constructs a FavoriteDao object and initializes the database connection.
     *
     * @throws RepositoryException if a repository exception occurs
     */
    public FavoriteDao() throws RepositoryException {
        this.connexion = DBManager.getInstance().getConnection();
    }

    /**
     * Gets an instance of the FavoriteDao class.
     *
     * @return an instance of the FavoriteDao class
     * @throws RepositoryException if a repository exception occurs
     */
    public static FavoriteDao getInstance() throws RepositoryException {
        return FavoriteDao.FavoriteDaoHolder.getInstance();
    }

    @Override
    public String insert(FavoriteDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Bad given param");
        }
        String query = "INSERT INTO FAVORITES(id_source,id_destination,favorite_name) " +
                "values(?,?,?)";
        try {
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, item.getSource().getKey());
            stmt.setInt(2, item.getDestination().getKey());
            stmt.setString(3, item.getKey());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return item.getKey();
    }

    @Override
    public void delete(String key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Bad given param");
        }
        String query = "DELETE FROM FAVORITES where favorite_name= ?";
        try {
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void update(FavoriteDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Bad given param");
        }
        String query = "UPDATE FAVORITES SET id_source= ?, id_destination= ?, " +
                "favorite_name= ? where favorite_name= ?";
        try {
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, item.getSource().getKey());
            stmt.setInt(2, item.getDestination().getKey());
            stmt.setString(3, item.getKey());
            stmt.setString(4, item.getKey());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<FavoriteDto> selectAll() throws RepositoryException {
        String query = "SELECT src.name src_name, dest.name dest_name, id_source, "
                + "id_destination, favorite_name " +
                "FROM favorites " +
                "JOIN stations src ON id_source = src.id " +
                "JOIN stations dest ON id_destination = dest.id";
        List<FavoriteDto> allFavorite = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                FavoriteDto favoriteDto = new FavoriteDto(rs.getString("favorite_name"),
                        new StationDto(rs.getInt("id_source"), rs.getString("src_name")),
                        new StationDto(rs.getInt("id_destination"), rs.getString("dest_name")));
                allFavorite.add(favoriteDto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return allFavorite;
    }

    @Override
    public FavoriteDto select(String key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Bad given param");
        }
        String query = "SELECT src.name src_name, dest.name dest_name, id_source, "
                + "id_destination, favorite_name " +
                "FROM favorites " +
                "JOIN stations src ON id_source = src.id " +
                "JOIN stations dest ON id_destination = dest.id " +
                "WHERE favorite_name= ?";
        FavoriteDto favoriteDto = null;
        try {
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                favoriteDto = new FavoriteDto(rs.getString("favorite_name"),
                        new StationDto(rs.getInt("id_source"), rs.getString("src_name")),
                        new StationDto(rs.getInt("id_destination"), rs.getString("dest_name")));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return favoriteDto;
    }

    /**
     * Deletes all FavoriteDto objects from the 'favorites' table in the database.
     *
     * @throws RepositoryException if a repository exception occurs
     */
    public void deleteAll() throws RepositoryException {
        String query = "DELETE FROM FAVORITES";
        try {
            Statement stmt = connexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    /**
     * The FavoriteDaoHolder class holds the instance of the FavoriteDao class.
     */
    private static class FavoriteDaoHolder {

        private static FavoriteDao getInstance() throws RepositoryException {
            return new FavoriteDao();
        }
    }
}
