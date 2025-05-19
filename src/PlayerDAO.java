package src;
import java.sql.*;

public class PlayerDAO {
    public static void save(Player p, boolean gagne) {
        try {
            PreparedStatement st = ConnexionBDD.getConnection().prepareStatement(
                "INSERT INTO joueur (nom, victoires) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE victoires = victoires + ?"
            );
            st.setString(1, p.name);
            st.setInt(2, gagne ? 1 : 0); // Si victoire, 1 sinon 0
            st.setInt(3, gagne ? 1 : 0); // Si victoire, +1 sinon +0
            st.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}