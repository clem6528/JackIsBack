package src;
public class Main {
    public static void main(String[] args) {
        ConnexionBDD.connect(); // Active la BDD
        javax.swing.SwingUtilities.invokeLater(BlackjackGUI::new);
    }
}