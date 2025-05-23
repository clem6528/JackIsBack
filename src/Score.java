package src;
public class Score { // Classe représentant un score
    public Player player; // Joueur associé au score
    public int value; // Valeur du score
    public Score(Player player, int value) { // Constructeur de la classe Score
        this.player = player; // Joueur associé au score
        this.value = value; // Valeur du score
    }
}