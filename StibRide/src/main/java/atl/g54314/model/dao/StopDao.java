package atl.g54314.model.dao;

import atl.g54314.model.dto.StationDto;
import atl.g54314.model.dto.StopDto;
import atl.g54314.model.exception.RepositoryException;
import atl.g54314.model.jdbc.DBManager;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The StopDao class provides access to the 'stops' table in the database.
 * It implements the Dao interface with Pair<Integer, Integer> as the key and
 * StopDto as the value.
 */
public class StopDao implements Dao<Pair<Integer, Integer>, StopDto> {
    private final Connection connexion;

    /**
     * Creates a new instance of StopDao.
     *
     * @throws RepositoryException if a repository exception occurs
     */
    public StopDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    /**
     * Gets the instance of the StopDao class.
     *
     * @return the instance of the StopDao class
     * @throws RepositoryException if a repository exception occurs
     */
    public static StopDao getInstance() throws RepositoryException {
        return StopDaoHolder.getInstance();
    }

    @Override
    public Pair<Integer, Integer> insert(StopDto item) throws RepositoryException {
        throw new UnsupportedOperationException("Insérer un élement ne sera pas utilisé ici");
    }

    @Override
    public void delete(Pair<Integer, Integer> key) throws RepositoryException {
        throw new UnsupportedOperationException("Delete un élement ne sera pas utilisé ici");
    }

    @Override
    public void update(StopDto item) throws RepositoryException {
        throw new UnsupportedOperationException("Update un élement ne sera pas utilisé ici");
    }

    @Override
    public StopDto select(Pair<Integer, Integer> key) throws RepositoryException {
        throw new UnsupportedOperationException("Selectionner un élement ne sera pas utilisé ici");
    }

    @Override
    public List<StopDto> selectAll() throws RepositoryException {
        List<StopDto> stops = new ArrayList<>();
        try {
            String query = "SELECT id_line, id_station, id_order, name FROM stops JOIN "
                    + "lines line ON line.id = id_line " +
                    "JOIN stations station ON station.id = id_station ORDER BY id_line, id_order";
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                StopDto dto = new StopDto(rs.getInt("id_line"),
                        new StationDto(rs.getInt("id_station"), rs.getString("name")),
                        rs.getInt("id_order"));
                stops.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stops;
    }

    /**
     * Retrieves all StopDto objects that have the same station ID.
     *
     * @param key the station ID to search for
     * @return a list of StopDto objects with the same station ID
     * @throws RepositoryException if a repository exception occurs
     */
    public List<StopDto> selectSame(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Error with given object");
        }

        List<StopDto> stops = new ArrayList<>();
        try {
            String query = "SELECT id_line, id_station, id_order, name FROM stops JOIN "
                    + "lines line ON line.id = id_line " +
                    "JOIN stations station ON station.id = id_station WHERE "
                    + "id_station = ? ORDER BY id_line, id_order";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StopDto dto = new StopDto(rs.getInt("id_line"),
                        new StationDto(rs.getInt("id_station"), rs.getString("name")),
                        rs.getInt("id_order"));
                stops.add(dto);
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stops;
    }

    /**
     * Retrieves a list of StopDto objects that are adjacent to the given list of
     * stops.
     *
     * @param stops the list of stops
     * @return a list of adjacent StopDto objects
     * @throws RepositoryException if a repository exception occurs
     */
    public List<StopDto> selectStopAdjacent(List<StopDto> stops) throws RepositoryException {
        if (stops == null) {
            throw new RepositoryException("Error with given objects");
        }
        StringBuilder query = new StringBuilder("SELECT id_line, id_station, id_order, name "
                + "FROM stops JOIN lines line ON " +
                "line.id = id_line JOIN stations station ON station.id = id_station WHERE");
        List<Integer> preparedList = new ArrayList<>();
        stops.forEach(stop -> {
            if (stops.get(0) != stop) {
                query.append(" OR ");
            }
            query.append("(id_line = ? AND (id_order = ? OR id_order = ?))");
            preparedList.add(stop.getLine());
            preparedList.add(stop.getOrder() - 1);
            preparedList.add(stop.getOrder() + 1);
        });
        query.append("GROUP BY name");
        List<StopDto> previousNextStops = new ArrayList<>();
        try {
            PreparedStatement stmt = connexion.prepareStatement(query.toString());
            for (int i = 0; i < preparedList.size(); i++) {
                stmt.setInt(i + 1, preparedList.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StopDto dto = new StopDto(rs.getInt("id_line"),
                        new StationDto(rs.getInt("id_station"), rs.getString("name")),
                        rs.getInt("id_order"));
                previousNextStops.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return previousNextStops;
    }

    /**
     * The StopDaoHolder class holds the instance of the StopDao class.
     */
    private static class StopDaoHolder {

        /**
         * Gets the instance of the StopDao class.
         *
         * @return the instance of the StopDao class
         * @throws RepositoryException if a repository exception occurs
         */
        private static StopDao getInstance() throws RepositoryException {
            return new StopDao();
        }
    }
}
