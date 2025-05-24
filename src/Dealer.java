package src;

public class Dealer extends Player { // Classe représentant le croupier dans le jeu de Blackjack, héritant de la classe Player
    public Dealer() {
        super("Croupier");
    }

    public boolean shouldDraw() { // Méthode pour déterminer si le croupier doit tirer une carte
        return getScore() < 17;
    }
}