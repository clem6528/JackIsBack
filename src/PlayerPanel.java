package src;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;

public class PlayerPanel extends JPanel {
    private JLabel lblMise;
    private JLabel lblCartes;
    private JLabel lblScore;
    private JButton btnMiser;
    private JButton btnTirer;
    private JButton btnRester;
    private int mise = 0;
    private int points = 0;
    private List<Card> hand = new ArrayList<>();
    private Deck deck;
    private boolean hasPlayed = false;

    public PlayerPanel(String name, Deck deck) {
        this.deck = deck;
        setBorder(BorderFactory.createTitledBorder(name));
        setLayout(new GridLayout(6, 1));

        lblMise = new JLabel("Mise: 0€");
        lblCartes = new JLabel("Cartes: []");
        lblScore = new JLabel("Score: 0");
        btnMiser = new JButton("Miser +10€");
        btnTirer = new JButton("Tirer une carte");
        btnRester = new JButton("Rester");

        // Donne 2 cartes au départ
        hand.add(deck.draw());
        hand.add(deck.draw());
        updateHandDisplay();

        btnMiser.addActionListener(e -> {
            mise += 10;
            lblMise.setText("Mise: " + mise + "€");
        });

        btnTirer.addActionListener(e -> {
            if (!hasPlayed && deck != null && deckSize() > 0 && points < 21) {
                Card c = deck.draw();
                hand.add(c);
                updateHandDisplay();
                points = Utils.handValue(hand);
                lblScore.setText("Score: " + points);
                hasPlayed = true;
                btnTirer.setEnabled(false);
                btnRester.setEnabled(false);
                if (points >= 21) {
                    if (points > 21) {
                        JOptionPane.showMessageDialog(this, "Dépassement de 21 ! Vous avez perdu.");
                    } else if (points == 21) {
                        JOptionPane.showMessageDialog(this, "Blackjack ou 21 ! Tour terminé.");
                    }
                }
            }
        });

        btnRester.addActionListener(e -> {
            hasPlayed = true;
            btnTirer.setEnabled(false);
            btnRester.setEnabled(false);
        });

        add(lblMise);
        add(lblCartes);
        add(lblScore);
        add(btnMiser);
        add(btnTirer);
        add(btnRester);
    }

    public void newTurn() {
        hasPlayed = false;
        if (points < 21) {
            btnTirer.setEnabled(true);
            btnRester.setEnabled(true);
        }
    }

    private void updateHandDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Card c : hand) {
            sb.append("[").append(c.toString()).append("] ");
        }
        lblCartes.setText("Cartes: " + sb.toString());
        points = Utils.handValue(hand);
        lblScore.setText("Score: " + points);
    }

    private int deckSize() {
        try {
            java.lang.reflect.Field f = deck.getClass().getDeclaredField("cards");
            f.setAccessible(true);
            java.util.List<?> cards = (java.util.List<?>) f.get(deck);
            return cards.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getPoints() {
        return points;
    }

    public List<Card> getHand() {
        return hand;
    }
}