package src;
import java.util.*;

public class Player {
    public String name;
    public int victories;
    public List<Card> hand = new ArrayList<>();
    public Player(String name) { this.name = name; }
}