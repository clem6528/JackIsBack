package src;
import java.util.List;

public class Utils {
    public static int handValue(List<Card> hand) {
        int sum = 0, aces = 0;
        for (Card c : hand) {
            switch (c.rank) {
                case "As": aces++; sum += 11; break;
                case "Roi": case "Dame": case "Valet": sum += 10; break;
                default: sum += Integer.parseInt(c.rank);
            }
        }
        while (sum > 21 && aces > 0) { sum -= 10; aces--; }
        return sum;
    }
}