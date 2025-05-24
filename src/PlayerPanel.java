package src;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerPanel extends JPanel { // Classe représentant le panneau d'un joueur
    private JLabel lblMise; // Label pour afficher la mise
    private JLabel lblCartes; // Label pour afficher les cartes du joueur
    private JLabel lblScore; // Label pour afficher le score du joueur
    private JButton btnMiser; // Bouton pour miser
    private JButton btnTirer; // Bouton pour tirer une carte
    private JButton btnRester; // Bouton pour rester
    // initialisation des variables
    private int mise = 0; // Mise actuelle du joueur
    private int points = 0; // Points du joueur
    private List<Card> hand = new ArrayList<>(); // Main du joueur
    private Deck deck; // Paquet de cartes du jeu
    private boolean hasPlayed = false; // Indicateur si le joueur a joué son tour
    private String playerName; // Nom du joueur

    public PlayerPanel(String name, int victoire, Deck deck) { // Constructeur de la classe PlayerPanel
        this.deck = deck; // Paquet de cartes du jeu
        this.playerName = name; // Nom du joueur
        setBorder(BorderFactory.createTitledBorder(name +". A "+ victoire +" victoires")); // Crée une bordure avec le nom du joueur
        setLayout(new GridLayout(6, 1)); // Définit la disposition du panneau


        //constructeurs dans java swing
        lblMise = new JLabel("Mise: 0€"); // Label pour afficher la mise
        lblCartes = new JLabel("Cartes: []"); // Label pour afficher les cartes du joueur
        lblScore = new JLabel("Score: 0"); // Label pour afficher le score du joueur
        btnMiser = new JButton("Miser +10€"); // Bouton pour miser
        btnTirer = new JButton("Tirer une carte"); // Bouton pour tirer une carte
        btnRester = new JButton("Rester"); // Bouton pour rester

        // Donne 2 cartes au départ
        hand.add(deck.draw());
        hand.add(deck.draw());
        updateHandDisplay(); // Ligne N°90. Methode pour mettre à jour l'affichage de la main -> append


        // Pas terminé. Ne sert à rien pour l'instant
        btnMiser.addActionListener(e -> { // Action à effectuer lors du clic sur le bouton Miser. Méthode dans java swing
            mise += 10; // Incrémente la mise de 10€
            lblMise.setText("Mise: " + mise + "€"); // Met à jour le label de mise
        });

        // Ajoute les composants au panneau
        add(lblMise);
        add(lblCartes);
        add(lblScore);
        // Ajoute les boutons au panneau
        add(btnMiser);
        add(btnTirer);
        add(btnRester);

        btnTirer.addActionListener(e -> { // Action à effectuer lors du clic sur le bouton Tirer
            if (!hasPlayed && deck != null && deckSize() > 0 && points < 21) { // Si le joueur n'a pas encore joué, le paquet de cartes n'est pas vide et le score est inférieur à 21   
            Card c = deck.draw(); // Tire une carte du paquet
                hand.add(c); // Ajoute la carte à la main du joueur
                updateHandDisplay(); // Met à jour l'affichage de la main
                points = Utils.handValue(hand); // Calcule la valeur de la main avec la méthode Utils.handValue
                lblScore.setText("Score: " + points); // Met à jour le label de score
                if (points >= 21) { // Si le score est supérieur ou égal à 21
                    hasPlayed = true; // Indique que le joueur a joué son tour. Ne fonctionne pas encore méthode ligne N°87
                    btnTirer.setEnabled(false); // Désactive le bouton Tirer
                    btnRester.setEnabled(false); // Désactive le bouton Rester
                    if (points > 21) { // Si le score dépasse 21
                        JOptionPane.showMessageDialog(this, "Dépassement de 21 ! Vous avez perdu."); // Affiche un message de perte
                    } else if (points == 21) { // Si le score est égal à 21
                        JOptionPane.showMessageDialog(this, "Blackjack ou 21 ! Tour terminé."); // Affiche un message de victoire
                    }
                }
            }
        });

        btnRester.addActionListener(e -> { // Action à effectuer lors du clic sur le bouton Rester
            hasPlayed = true; // Indique que le joueur a joué son tour
            btnTirer.setEnabled(false); // Désactive le bouton Tirer
            btnRester.setEnabled(false); // Désactive le bouton Rester
        });
    }


    // La méthode ne foncionne pas...
    public void newTurn() { // Méthode pour commencer un nouveau tour
        hasPlayed = false; // Réinitialise l'indicateur de tour
        if (points < 21) { // Si le score est inférieur à 21
            btnTirer.setEnabled(true); // Active le bouton Tirer
            btnRester.setEnabled(true); // Active le bouton Rester
        }
    }

    private void updateHandDisplay() { // Met à jour l'affichage de la main du joueur
        StringBuilder sb = new StringBuilder(); // Constructeur de la chaîne
        for (Card c : hand) { // Pour chaque carte de la main
            sb.append("[").append(c.toString()).append("] "); // Ajoute la carte à la chaîne
        }
        lblCartes.setText("Cartes: " + sb.toString()); // Met à jour le label de cartes
        points = Utils.handValue(hand); // Calcule la valeur de la main méthode Utils.handValue
        lblScore.setText("Score: " + points); // Met à jour le label de score
    }

    private int deckSize() { // Retourne la taille du paquet de cartes
        try { // Utilise la réflexion (accède à des données d'une classe privée, gère les erreurs) pour accéder à la taille du paquet
            java.lang.reflect.Field f = deck.getClass().getDeclaredField("cards"); // Accède au champ "cards" du paquet
            f.setAccessible(true); // Rend le champ accessible
            java.util.List<?> cards = (java.util.List<?>) f.get(deck); // Récupère la liste des cartes
            return cards.size(); // Retourne la taille de la liste des cartes
        } catch (Exception e) { // Gère les exceptions
            return 0; // Retourne 0 en cas d'erreur
        }
    }

    public int getPoints() { // Retourne les points du joueur
        return points;
    }

    public boolean hasPlayed() { // Retourne si le joueur a joué son tour
        return hasPlayed;
    }

    public String getPlayerName() { // Retourne le nom du joueur
        return playerName;
    }
}