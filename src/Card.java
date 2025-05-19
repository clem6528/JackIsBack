package src;

public class Card {
    public enum Suit { HEARTS, DIAMONDS, CLUBS, SPADES }
    public enum Rank { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE }

    public final Suit suit;
    public final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue() {
        switch (rank) {
            case JACK: case QUEEN: case KING:
                return 10;
            case ACE:
                return 11;
            default:
                return rank.ordinal() + 2;
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}