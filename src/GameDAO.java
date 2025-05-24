package src;
import java.sql.*;

public class GameDAO { // Classe pour gérer les opérations de la base de données liées aux parties. Pas utilisée par manque de temps. Mais aurais permis l'enregistrement de l'état de jeux actuel sur la bdd
    public static void saveGame(int id, int scoreJ1, int scoreJ2) {
        try {
            PreparedStatement st = ConnexionBDD.getConnection().prepareStatement(
                "INSERT INTO partie (id, score_j1, score_j2) VALUES (?, ?, ?)"
            );
            st.setInt(1, id);
            st.setInt(2, scoreJ1);
            st.setInt(3, scoreJ2);
            st.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}