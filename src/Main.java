package src;
public class Main {
    public static void main(String[] args) {
        ConnexionBDD.connect();
        new BlackjackGUI();
    }
}