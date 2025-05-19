package src;
import java.sql.*;

public class ConnexionBDD {
    private static Connection cn;
    public static void connect() {
        String url = "jdbc:mysql://mysql02.pedagogie.enit.fr/db_structure.php?server=1&db=GestioCommande";
        String login = "student";
        String password = "Enit@65";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection(url, login, password);
            System.out.println("Connexion réussie à la base de données.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return cn;
    }
}