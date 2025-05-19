package src;

import java.util.*;

public class Game {
    private Deck deck;
    private List<Player> players;
    private Dealer dealer;
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        deck = new Deck();
        players = new ArrayList<>();
        dealer = new Dealer();
    }

    public void setup() {
        System.out.print("Nombre de joueurs : ");
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 1; i <= n; i++) {
            System.out.print("Nom du joueur " + i + " : ");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }
    }

    public void dealInitialCards() {
        for (Player p : players) {
            p.addCard(deck.draw());
            p.addCard(deck.draw());
        }
        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());
    }

    public void play() {
        setup();
        dealInitialCards();
        for (Player p : players) {
            while (true) {
                System.out.println(p.name + " : " + p.handToString() + "Score : " + p.getScore());
                if (p.isBusted()) {
                    System.out.println(p.name + " a perdu !");
                    p.hasLost = true;
                    break;
                }
                System.out.print("Tirer une carte (o/n) ? ");
                String rep = scanner.nextLine();
                if (rep.equalsIgnoreCase("o")) {
                    p.addCard(deck.draw());
                } else {
                    break;
                }
            }
        }
        // Dealer's turn
        System.out.println("Croupier : " + dealer.handToString() + "Score : " + dealer.getScore());
        while (dealer.shouldDraw()) {
            System.out.println("Le croupier tire une carte.");
            dealer.addCard(deck.draw());
            System.out.println("Croupier : " + dealer.handToString() + "Score : " + dealer.getScore());
        }
        if (dealer.isBusted()) {
            System.out.println("Le croupier a perdu !");
        }
        // Results
        int dealerScore = dealer.getScore();
        for (Player p : players) {
            if (p.hasLost) continue;
            int score = p.getScore();
            if (score > 21) {
                System.out.println(p.name + " a perdu !");
            } else if (dealer.isBusted() || score > dealerScore) {
                System.out.println(p.name + " a gagné !");
            } else if (score == dealerScore) {
                System.out.println(p.name + " égalité avec le croupier !");
            } else {
                System.out.println(p.name + " a perdu !");
            }
        }
    }

    public static void main(String[] args) {
        new Game().play();
    }
}