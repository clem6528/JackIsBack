package src;
import java.util.*;

public class Deck {
    private List<Card> cards = new ArrayList<>();
    public Deck() {
        String[] suits = {"Coeur", "Carreau", "Trèfle", "Pique"};
        String[] ranks = {"2","3","4","5","6","7","8","9","10","Valet","Dame","Roi","As"};
        for (String s : suits)
            for (String r : ranks)
                cards.add(new Card(s, r));
        Collections.shuffle(cards);
    }
    public Card draw() { return cards.remove(0); }
}