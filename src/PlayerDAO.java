package src;
import java.sql.*;

public class PlayerDAO {
    public static void save(Player p, boolean gagne) {
        try {
            Connection cn = ConnexionBDD.getConnection();
            // Vérifie si le joueur existe déjà
            PreparedStatement check = cn.prepareStatement("SELECT id FROM joueur WHERE nom = ?");
            check.setString(1, p.name);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                // Le joueur existe, on met à jour le score seulement si victoire
                if (gagne) {
                    PreparedStatement update = cn.prepareStatement("UPDATE joueur SET victoires = victoires + 1 WHERE nom = ?"); // Prépare la requête de mise à jour
                    update.setString(1, p.name); // Met à jour le nombre de victoires
                    update.executeUpdate(); // Exécute la mise à jour
                    update.close(); // Ferme la requête préparée
                }
            } else {
                // Le joueur n'existe pas, on l'insère (avec 1 victoire si gagnant, 0 sinon)
                PreparedStatement insert = cn.prepareStatement("INSERT INTO joueur (nom, victoires) VALUES (?, ?)");
                insert.setString(1, p.name); // Insère le nom du joueur
                insert.setInt(2, gagne ? 1 : 0);
                insert.executeUpdate();
                insert.close();
            }
            rs.close();
            check.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Méthode pour récupérer le nombre de victoires. On l'affichera dans la GUI.
    public static int getVictoires(String nom) {
        int victoires = 0;
        try {
            Connection cn = ConnexionBDD.getConnection();
            PreparedStatement ps = cn.prepareStatement("SELECT victoires FROM joueur WHERE nom = ?");
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                victoires = rs.getInt("victoires");
                System.out.print(victoires);
            }
            rs.close();
            ps.close();
        } catch (Exception e) { e.printStackTrace(); }
        return victoires;
    }
}