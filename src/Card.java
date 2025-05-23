package src;

public class Card { // Classe représentant une carte
    public enum Suit { COEUR, CARREAUX, TREFLE, PIQUE } // Couleurs des cartes
    public enum Rank { DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF, DIX, VALET, REINE, ROI, AS } // Valeurs des cartes

    public final Suit suit; // Couleur de la carte
    public final Rank rank; // Valeur de la carte

    public Card(Suit suit, Rank rank) { // Constructeur de la classe Card
        this.suit = suit; // Couleur de la carte
        this.rank = rank; // Valeur de la carte
    }

    public int getValue() { // Retourne la valeur de la carte
        switch (rank) { // Selon la valeur de la carte
            case VALET: case REINE: case ROI: // Valets, Reines et Rois valent 10
                return 10; // Retourne 10
            case AS:// As peut valoir 1 ou 11
                return 11; // Retourne 11
            default: // Autres cartes valent leur valeur numérique
                return rank.ordinal() + 2; // Retourne la valeur numérique de la carte
                ////Ordinal prends la valeur de l'énumération du tableau (rank classe card) en commençant de 0 c'est pourquoi on ajoute 2
        }
    }

    @Override // Méthode toString pour afficher la carte
    public String toString() { // Retourne une chaîne de caractères représentant la carte
        return rank + " de " + suit; // Retourne la valeur et la couleur de la carte
    }
}