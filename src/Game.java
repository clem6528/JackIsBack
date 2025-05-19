package src;
import java.util.*;

public class Game {
    public List<Player> players = new ArrayList<>();
    public Dealer dealer = new Dealer();
    public Deck deck = new Deck();
    public void start() {
        for (Player p : players) {
            p.hand.clear();
            p.hand.add(deck.draw());
            p.hand.add(deck.draw());
        }
        dealer.hand.clear();
        dealer.hand.add(deck.draw());
        dealer.hand.add(deck.draw());
    }
}