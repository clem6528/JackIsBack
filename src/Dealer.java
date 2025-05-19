package src;

public class Dealer extends Player {
    public Dealer() {
        super("Croupier");
    }

    public boolean shouldDraw() {
        return getScore() < 17;
    }
}