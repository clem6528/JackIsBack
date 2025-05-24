package src;

import java.util.*;

public class Deck { // Classe représentant un paquet de cartes pour le jeu de Blackjack
    private List<Card> cards = new ArrayList<>(); // Liste de cartes dans le paquet
    public Deck() { // Constructeur de la classe Deck
        for (Card.Suit suit : Card.Suit.values()) { // Pour chaque couleur de carte
            for (Card.Rank rank : Card.Rank.values()) { // Pour chaque valeur de carte
                cards.add(new Card(suit, rank)); // Ajoute une nouvelle carte au paquet
            }
        }
        System.out.println("Paquet " + cards);
        shuffle(); // Mélange le paquet de cartes lors de la création
        System.out.println("Paquet mélangé " + cards);
    }

    public void shuffle() { // Méthode pour mélanger le paquet de cartes
        Collections.shuffle(cards); // Utilise la méthode shuffle de Collections pour mélanger les cartes
    }

    public Card draw() { // Méthode pour tirer une carte du paquet
        return cards.remove(cards.size() - 1); // Retire la dernière carte de la liste
    }
}