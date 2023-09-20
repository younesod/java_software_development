package atl.grade;

import java.sql.*;

public class ex2 extends Demo {

    @Override
    public void execute(String url) {
        try {
            Connection connexion = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement stmt = connexion.createStatement();

            String query = "INSERT INTO LESSONS(acronym) values('ANL')";

            int count = stmt.executeUpdate(query);
            System.out.println("\t Nombre de record modifié : " + count);

            System.out.println("\n" + getTitle() + "\n");
            query = "SELECT acronym FROM Lessons";
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                System.out.println("\t Acronym : " + result.getString("acronym"));
            }
            System.out.println("\n" + getTitle() + "\n");

            query="DELETE FROM LESSONS where acronym='ANL'";
            count= stmt.executeUpdate(query);
            System.out.println("\t Nombre de record supprimé : " + count);
            System.out.println("\n" + getTitle() + "\n");

            query = "SELECT acronym FROM Lessons";
            result = stmt.executeQuery(query);
            while (result.next()) {
                System.out.println("\t Acronym : " + result.getString("acronym"));
            }


        } catch (SQLException ex) {
            System.out.println("DEMO_INSERT | Erreur " + ex.getMessage() + " SQLState " + ex.getSQLState());
        }
    }

    @Override
    public String getTitle() {
        return "Ajout de ANL | Affichage de LESSONS | Suppression de ANL | Affichage de LESSONS";
    }
}
