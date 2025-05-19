package src;
public class Card {
    public String suit;
    public String rank;
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }
    public String toString() { return rank + " de " + suit; }

    // Retourne la valeur de la carte pour le Black Jack (hors gestion spéciale de l'As)
    public int getValue() {
        switch (rank) {
            case "As": return 11;
            case "Roi":
            case "Dame":
            case "Valet": return 10;
            default: return Integer.parseInt(rank);
        }
    }
}