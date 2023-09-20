package atl.grade.selection;

import atl.grade.Demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jlc
 */
public class SelectAllLessons extends Demo {

    @Override
    public void execute(String url) {
        try {
            Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement stmt = connexion.createStatement();

            String query = "SELECT acronym FROM Lessons";

            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                System.out.println("\t Acronym : " + result.getString("acronym"));
            }
        } catch (SQLException ex) {
            System.out.println("SELECT_ALL_LESSONS | Erreur " + ex.getMessage() + " SQLState " + ex.getSQLState());
        }
    }

    @Override
    public String getTitle() {
        return "La liste des leçons en DB";
    }
}
