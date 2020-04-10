import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class fenetreMorpion extends JFrame {
    private JFrame window = new JFrame();
    //private JButton but[] = new JButton[9];
    
    //pour saisie le Joueur 1
    private JLabel labelJoueur1;
    private JTextField champSaisieJoueur1 ;

    //pour la saisie le Joueur 2	
    private JLabel labelJoueur2;
    private JTextField champSaisieJoueur2;

    //pour le but du jeu
    private JLabel labelBut;
   
    //un bouton pour valider l'entree
    private JButton btnJouer;
    
    
    public fenetreMorpion(){
        super("Jeu Morpion");
		
		// ====== Instanciation des widgets de la fenetre entree =====
        labelJoueur1 = new JLabel("Joueur 1 : ");
        champSaisieJoueur1 = new JTextField("",10);

        labelJoueur2 = new JLabel("Joueur 2 : ");
        champSaisieJoueur2 = new JTextField("",10);

        labelBut = new JLabel("But: Aligner 5 pions", JLabel.CENTER);

        btnJouer = new JButton("Jouer!!!");
        btnJouer.addActionListener(new ListenerAccueil(this));
        
        
        // ====== Organisation structurelle ======
          
          
        window.setSize(300,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel( new GridLayout(2, 1));
		panel.setBackground(Color.white);
		panel.add(new JLabel(new ImageIcon("TicTacToe.jpeg")));
        panel.add(labelBut);
        labelBut.setFont(new Font("Serif", Font.PLAIN, 21));
	
		
		JPanel monPanelEntree= new JPanel();
        
        monPanelEntree.setBackground(Color.yellow);
        monPanelEntree.add(labelJoueur1);
        monPanelEntree.add(champSaisieJoueur1);
        monPanelEntree.add(labelJoueur2);
        monPanelEntree.add(champSaisieJoueur2);
        monPanelEntree.add(btnJouer)  ;
        
        
        window.add(panel, BorderLayout.CENTER);
        window.add(monPanelEntree, BorderLayout.SOUTH);

		
        window.setVisible(true);  
        
        window.setSize(300,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    
 }

