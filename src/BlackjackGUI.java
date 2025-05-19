package src;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BlackjackGUI extends JFrame {
    private ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    private Deck deck = new Deck(); // Deck partagé

    public BlackjackGUI() {
        setTitle("Blackjack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new GridLayout(1, 2));

        // Passe le deck à chaque PlayerPanel
        PlayerPanel joueur1 = new PlayerPanel("Joueur 1", deck);
        PlayerPanel joueur2 = new PlayerPanel("Joueur 2", deck);
        playerPanels.add(joueur1);
        playerPanels.add(joueur2);

        playersPanel.add(joueur1);
        playersPanel.add(joueur2);

        add(playersPanel, BorderLayout.CENTER);
        add(new JLabel("Croupier: [cartes ici]"), BorderLayout.NORTH);

        setSize(600, 400);
        setVisible(true);
    }
}