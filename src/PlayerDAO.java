package src;
import java.sql.*;

public class PlayerDAO {
    public static void save(Player p) {
        try {
            PreparedStatement st = ConnexionBDD.getConnection().prepareStatement(
                "INSERT INTO joueur (nom, victoires) VALUES (?, ?) ON DUPLICATE KEY UPDATE victoires=?"
            );
            st.setString(1, p.name);
            st.setInt(2, p.victories);
            st.setInt(3, p.victories);
            st.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}