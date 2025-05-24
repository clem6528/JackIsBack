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

    public BlackjackGUI() { // Constructeur de la classe BlackjackGUI
        setTitle("Blackjack"); // Titre de la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Ferme l'application lorsque la fenêtre est fermée
        setLayout(new BorderLayout()); // Définit la disposition de la fenêtre

        // Demande le nombre de joueurs et leurs noms
        int nbJoueurs = demanderNombreJoueurs();    
        ArrayList<String> noms = demanderNomsJoueurs(nbJoueurs);

        //récupérer les victoires
        ArrayList<Integer> victoire = new ArrayList<>(); // Liste pour stocker le nombre de victoires de chaque joueur
        for (int i =0; i < nbJoueurs; i++) { // Pour chaque joueur
            int victoires = PlayerDAO.getVictoires(noms.get(i)); // Récupère le nombre de victoires du joueur depuis la base de données
            victoire.add(victoires); // Ajoute le nombre de victoires à la liste
        }
        
        // Crée les panneaux pour chaque joueur
        playersPanel.setLayout(new GridLayout(1, nbJoueurs));
        for (int i = 0; i < nbJoueurs; i++) {
            PlayerPanel pp = new PlayerPanel(noms.get(i), victoire.get(i), deck); // il manquera les mises pour le rendre totalement fonctionnel
            playerPanels.add(pp);
            playersPanel.add(pp);
        }

        // Donne 2 cartes au croupier
        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());
        updateDealerLabel();

        add(playersPanel, BorderLayout.CENTER);
        add(dealerLabel, BorderLayout.NORTH);

        setSize(300 + 200 * nbJoueurs, 400); // Définit la taille de la fenêtre en fonction du nombre de joueurs
        setVisible(true); // Affiche la fenêtre

        // Démarre la partie
        new Thread(this::gameLoop).start();
    }

    private int demanderNombreJoueurs() { // Méthode pour demander le nombre de joueurs
        String input = JOptionPane.showInputDialog(this, "Nombre de joueurs (1-4) :", "2"); // Affiche une boîte de dialogue pour demander le nombre de joueurs
        try {
            int n = Integer.parseInt(input); // Convertit l'entrée en entier
            System.out.println(Math.max(1, Math.min(4, n))); // Assure que le nombre de joueurs est entre 1 et 4
            return Math.max(1, Math.min(4, n)); // Retourne le nombre de joueurs, limité entre 1 et 4
        } catch (Exception e) {
            return 2; // Si l'entrée n'est pas valide, retourne 2 par défaut
        }
    }

    private ArrayList<String> demanderNomsJoueurs(int n) { // Méthode pour demander les noms des joueurs
        ArrayList<String> noms = new ArrayList<>(); // Liste pour stocker les noms des joueurs
        for (int i = 1; i <= n; i++) {
            String nom = JOptionPane.showInputDialog(this, "Nom du joueur " + i + " :", "Joueur " + i);
            if (nom == null || nom.trim().isEmpty()) nom = "Joueur " + i; // Si le nom est vide ou annulé, utilise un nom par défaut
            noms.add(nom); // Ajoute le nom du joueur à la liste
        }
        return noms;
    }

    private void updateDealerLabel() { // Méthode pour mettre à jour le "label" (main) du croupier
        StringBuilder sb = new StringBuilder("Croupier: ");
        for (Card c : dealer.hand) sb.append("[").append(c).append("] ");
        sb.append("Score: ").append(dealer.getScore()); // Ajoute le score du croupier à la chaîne avec la méthode getScore() de la classe Dealer
        dealerLabel.setText(sb.toString());
    }

    private void gameLoop() { // Méthode principale de la boucle de jeu. Elle ne marche pas vraiment comme on le voudrait.
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
        while (dealer.getScore() < 17) { // Le croupier tire des cartes jusqu'à atteindre au moins 17
            dealer.addCard(deck.draw()); 
            updateDealerLabel();
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
        }
        updateDealerLabel();

        // Prépare les résultats
        int dealerScore = dealer.getScore(); // Récupère le score du croupier
        int minDiff = Integer.MAX_VALUE; // Initialise la différence minimale à une valeur très élevée
        ArrayList<PlayerPanel> winners = new ArrayList<>(); // Liste pour stocker les gagnants
        StringBuilder resultats = new StringBuilder(); // StringBuilder pour construire les résultats

        for (PlayerPanel pp : playerPanels) { // Pour chaque joueur
            int playerScore = pp.getPoints(); // Récupère le score du joueur
            String nom = pp.getPlayerName(); // Récupère le nom du joueur
            if (playerScore > 21) {
                resultats.append(nom).append(" a perdu !\n");
            } else if (dealerScore > 21) {
                resultats.append(nom).append(" a gagné !\n");
            } else {
                int diff = dealerScore - playerScore;
                if (playerScore <= 21 && diff >= 0) {
                    if (diff < minDiff) { // Si la différence est plus petite que la différence minimale trouvée jusqu'à présent. Permet de comparer les scores des joueurs
                        minDiff = diff;
                        winners.clear();
                        winners.add(pp);
                    } else if (diff == minDiff) { // Si la différence est égale à la différence minimale on ajoute le joueur à la liste des gagnants
                        winners.add(pp);
                    }
                }
            }
        }

        // Gestion BDD (enregistre les victoires) + messages pour les joueurs
        if (dealerScore <= 21 && !winners.isEmpty()) {
            PlayerPanel winner = winners.get(0); // Un seul gagnant
            resultats.append(winner.getPlayerName()).append(" est le plus proche du croupier et gagne !");
            for (PlayerPanel pp : playerPanels) {
                PlayerDAO.save(new Player(pp.getPlayerName()), pp == winner); // Enregistre la victoire du gagnant avec la méthode PlayerDAO.save
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