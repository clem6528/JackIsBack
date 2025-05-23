package src;
public class Bet { // Classe représantant un pari
    public Player player; // Joueur qui a parié
    public int amount; // Quantité d'argent pariée
    public Bet(Player player, int amount) { // Constructeur de la classe Bet
        this.player = player; // Joueur qui a parié
        this.amount = amount; // Quantité d'argent pariée
    }
}