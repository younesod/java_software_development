package atl.g54314.model.jdbc;

import atl.g54314.model.config.ConfigManager;
import atl.g54314.model.exception.RepositoryException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

/**
 * The DBManager class is responsible for managing the database connection and
 * transactions.
 * 
 * @author jlc
 */
public class DBManager {

    private Connection connection;

    private DBManager() {
    }

    /**
     * 
     * Retrieves the connection to the database.
     * 
     * @return the database connection
     * 
     * @throws RepositoryException if a repository exception occurs
     */
    public Connection getConnection() throws RepositoryException {
        String jdbcUrl = "jdbc:sqlite:" + ConfigManager.getInstance().getProperties("db.url");
        System.out.println("YO DEBUG : URL -> " + ConfigManager.getInstance().getProperties("db.url"));
        System.out.println("YO DEBUG FILE " + new File(".").getAbsolutePath());
        String db = ConfigManager.getInstance().getProperties("db.url");
        // || connection.isClosed()
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException ex) {
                throw new RepositoryException("Connexion impossible: " + ex.getMessage());
            }
        }
        return connection;
    }

    /**
     * 
     * Starts a new transaction.
     * 
     * @throws RepositoryException if a repository exception occurs
     */
    void startTransaction() throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de démarrer une transaction: " + ex.getMessage());
        }
    }

    /**
     * 
     * Starts a new transaction with the specified isolation level.
     * 
     * @param isolationLevel the isolation level of the transaction
     * 
     * @throws RepositoryException if a repository exception occurs
     */
    void startTransaction(int isolationLevel) throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);

            int isol = 0;
            switch (isolationLevel) {
                case 0:
                    isol = Connection.TRANSACTION_READ_UNCOMMITTED;
                    break;
                case 1:
                    isol = Connection.TRANSACTION_READ_COMMITTED;
                    break;
                case 2:
                    isol = Connection.TRANSACTION_REPEATABLE_READ;
                    break;
                case 3:
                    isol = Connection.TRANSACTION_SERIALIZABLE;
                    break;
                default:
                    throw new RepositoryException("Degré d'isolation inexistant!");
            }
            getConnection().setTransactionIsolation(isol);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de démarrer une transaction: " + ex.getMessage());
        }
    }

    /**
     * 
     * Validates the current transaction.
     * 
     * @throws RepositoryException if a repository exception occurs
     */
    void validateTransaction() throws RepositoryException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de valider la transaction: " + ex.getMessage());
        }
    }

    /**
     * 
     * Cancels the current transaction.
     * 
     * @throws RepositoryException if a repository exception occurs
     */
    void cancelTransaction() throws RepositoryException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible d'annuler la transaction: " + ex.getMessage());
        }
    }

    /**
     * 
     * Retrieves the instance of the DBManager.
     * 
     * @return the instance of DBManager
     */
    public static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }
}
