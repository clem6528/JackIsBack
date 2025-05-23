package src;

import java.util.*;

public class Player { // Classe représentant un joueur
    public String name; // Nom du joueur
    public int victories = 0; // Nombre de victoires du joueur
    public List<Card> hand = new ArrayList<>(); // Main initiale du joueur
    public boolean hasLost = false; // Indicateur de perte du joueur

    public Player(String name) { // Constructeur de la classe Player
        this.name = name; // Nom du joueur
    }

    public void addCard(Card card) { // Ajoute une carte à la main du joueur
        hand.add(card); // Ajoute la carte à la main
    }

    public int getScore() { // Calcule le score de la main du joueur
        int score = 0; // Score initial
        int aceCount = 0; // Compteur d'As
        for (Card card : hand) { // Pour chaque carte de la main
            score += card.getValue(); // Ajoute la valeur de la carte au score
            if (card.rank == Card.Rank.AS) aceCount++; // Incrémente le compteur d'As
        }
        while (score > 21 && aceCount > 0) { // Si le score dépasse 21 et qu'il y a des As
            score -= 10; // Réduit le score de 10 pour chaque As
            aceCount--; // Décrémente le compteur d'As
        }
        return score; // Retourne le score final
    }

    public boolean isBusted() { // Vérifie si le joueur a dépassé 21
        return getScore() > 21; // Retourne vrai si le score dépasse 21
    }

    public String handToString() { // Convertit la main du joueur en chaîne de caractères
        StringBuilder sb = new StringBuilder(); // Constructeur de la chaîne
        for (Card c : hand) sb.append(c).append(", "); // Ajoute chaque carte à la chaîne
        return sb.toString(); // Retourne la chaîne de caractères
    }
}