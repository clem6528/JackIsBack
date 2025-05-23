package src;
import java.util.List;

public class Utils { // Classe utilitaire pour le jeu de Blackjack
    public static int handValue(List<Card> hand) { // Calcule la valeur d'une main de cartes
        int sum = 0, aces = 0; // Somme des valeurs et compteur d'As
        for (Card c : hand) { // Pour chaque carte de la main
            switch (c.rank) { // Selon la valeur de la carte
                case AS: aces++; sum += 11; break; // As vaut 11
                case ROI: case REINE: case VALET: sum += 10; break; // Valets, Reines et Rois valent 10
                default: sum += c.rank.ordinal() + 2; // Autres cartes valent leur valeur numérique.
                //Ordinal prends la valeur de l'énumération du tableau (rank classe card) en commençant de 0 c'est pourquoi on ajoute 2
            }
        }
        while (sum > 21 && aces > 0) { sum -= 10; aces--; } // Si la somme dépasse 21 et qu'il y a des As, on réduit la valeur de l'As de 10
        return sum; // Retourne la valeur finale de la main
    }
}