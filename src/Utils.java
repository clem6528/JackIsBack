package src;
import java.util.List;

public class Utils {
    public static int handValue(List<Card> hand) {
        int sum = 0, aces = 0;
        for (Card c : hand) {
            switch (c.rank) {
                case ACE: aces++; sum += 11; break;
                case KING: case QUEEN: case JACK: sum += 10; break;
                default: sum += c.rank.ordinal() + 2;
            }
        }
        while (sum > 21 && aces > 0) { sum -= 10; aces--; }
        return sum;
    }
}