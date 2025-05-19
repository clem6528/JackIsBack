package src;

import java.util.*;

public class Player {
    public String name;
    public int victories = 0;
    public List<Card> hand = new ArrayList<>();
    public boolean hasLost = false;

    public Player(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getScore() {
        int score = 0;
        int aceCount = 0;
        for (Card card : hand) {
            score += card.getValue();
            if (card.rank == Card.Rank.ACE) aceCount++;
        }
        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }
        return score;
    }

    public boolean isBusted() {
        return getScore() > 21;
    }

    public String handToString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : hand) sb.append(c).append(", ");
        return sb.toString();
    }
}