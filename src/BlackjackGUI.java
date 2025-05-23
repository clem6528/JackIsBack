package src;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BlackjackGUI extends JFrame { // Classe principale de l'interface graphique du jeu Blackjack
    private ArrayList<PlayerPanel> playerPanels = new ArrayList<>(); // Liste des panneaux de joueurs
    private Deck deck = new Deck(); // Paquet de cartes du jeu
    private Dealer dealer = new Dealer(); // Croupier
    private JLabel dealerLabel = new JLabel(); // Label pour afficher les cartes du croupier
    private JPanel playersPanel = new JPanel(); // Panneau pour afficher les joueurs

    public BlackjackGUI() {
        setTitle("Blackjack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Demande le nombre de joueurs et leurs noms
        int nbJoueurs = demanderNombreJoueurs();
        ArrayList<String> noms = demanderNomsJoueurs(nbJoueurs);

        //récupérer les victoires
        ArrayList<Integer> victoire = new ArrayList<>();
        for (int i =0; i < nbJoueurs; i++) {
            int victoires = PlayerDAO.getVictoires(noms.get(i));
            victoire.add(victoires);
        }
        
        playersPanel.setLayout(new GridLayout(1, nbJoueurs));
        for (int i = 0; i < nbJoueurs; i++) {
            PlayerPanel pp = new PlayerPanel(noms.get(i), victoire.get(i), deck);
            playerPanels.add(pp);
            playersPanel.add(pp);
        }

        // Donne 2 cartes au croupier
        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());
        updateDealerLabel();

        add(playersPanel, BorderLayout.CENTER);
        add(dealerLabel, BorderLayout.NORTH);

        setSize(300 + 200 * nbJoueurs, 400);
        setVisible(true);

        // Démarre la partie
        new Thread(this::gameLoop).start();
    }

    private int demanderNombreJoueurs() {
        String input = JOptionPane.showInputDialog(this, "Nombre de joueurs (1-4) :", "2");
        try {
            int n = Integer.parseInt(input);
            System.out.println(Math.max(1, Math.min(4, n)));
            return Math.max(1, Math.min(4, n));
        } catch (Exception e) {
            return 2;
        }
    }

    private ArrayList<String> demanderNomsJoueurs(int n) {
        ArrayList<String> noms = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            String nom = JOptionPane.showInputDialog(this, "Nom du joueur " + i + " :", "Joueur " + i);
            if (nom == null || nom.trim().isEmpty()) nom = "Joueur " + i;
            noms.add(nom);
        }
        return noms;
    }

    private void updateDealerLabel() {
        StringBuilder sb = new StringBuilder("Croupier: ");
        for (Card c : dealer.hand) sb.append("[").append(c).append("] ");
        sb.append("Score: ").append(dealer.getScore());
        dealerLabel.setText(sb.toString());
    }

    private void gameLoop() {
        // Tour des joueurs
        for (PlayerPanel pp : playerPanels) {
            if (!pp.hasPlayed()) {
                SwingUtilities.invokeLater(pp::newTurn);
                while (!pp.hasPlayed()) {
                    try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                }
            }
        }

        // Tour du croupier
        while (dealer.getScore() < 17) {
            dealer.addCard(deck.draw());
            updateDealerLabel();
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
        }
        updateDealerLabel();

        // Prépare les résultats
        int dealerScore = dealer.getScore();
        int minDiff = Integer.MAX_VALUE;
        ArrayList<PlayerPanel> winners = new ArrayList<>();
        StringBuilder resultats = new StringBuilder();

        for (PlayerPanel pp : playerPanels) {
            int playerScore = pp.getPoints();
            String nom = pp.getPlayerName();
            if (playerScore > 21) {
                resultats.append(nom).append(" a perdu !\n");
            } else if (dealerScore > 21) {
                resultats.append(nom).append(" a gagné !\n");
            } else {
                int diff = dealerScore - playerScore;
                if (playerScore <= 21 && diff >= 0) {
                    if (diff < minDiff) {
                        minDiff = diff;
                        winners.clear();
                        winners.add(pp);
                    } else if (diff == minDiff) {
                        winners.add(pp);
                    }
                }
            }
        }

        // Gestion BDD : enregistre les victoires
        if (dealerScore <= 21 && !winners.isEmpty()) {
            PlayerPanel winner = winners.get(0); // Un seul gagnant
            resultats.append(winner.getPlayerName()).append(" est le plus proche du croupier et gagne !");
            for (PlayerPanel pp : playerPanels) {
                PlayerDAO.save(new Player(pp.getPlayerName()), pp == winner); // Enregistre la victoire du gagnant
            }
        } else if (dealerScore <= 21 && winners.isEmpty()) {
            resultats.append("Aucun joueur n'a battu le croupier !");
            for (PlayerPanel pp : playerPanels) {
                PlayerDAO.save(new Player(pp.getPlayerName()), false);
            }
        } else {
            // Si le croupier a perdu, tous les joueurs <=21 gagnent
            for (PlayerPanel pp : playerPanels) {
                int playerScore = pp.getPoints();
                PlayerDAO.save(new Player(pp.getPlayerName()), playerScore <= 21);
            }
        }

        // Affiche les résultats et propose de relancer
        int choix = JOptionPane.showOptionDialog(
            this,
            resultats.toString() + "\n\nVoulez-vous rejouer ?",
            "Résultats",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Nouvelle partie", "Quitter"},
            "Nouvelle partie"
        );

        if (choix == JOptionPane.YES_OPTION) {
            this.dispose(); // Ferme la fenêtre actuelle
            SwingUtilities.invokeLater(BlackjackGUI::new); // Relance une nouvelle partie
        } else {
            System.exit(0); // Ferme l'application
        }
    }
}