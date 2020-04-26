import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class fenetreMorpion extends JFrame {
    private JFrame window = new JFrame();
   
    //pour la saisie du Joueur 1
    private JLabel labelJoueur1;
    private JTextField champSaisieJoueur1 ;

    //pour la saisie du Joueur 2	
    private JLabel labelJoueur2;
    private JTextField champSaisieJoueur2;

    //pour le but du jeu
    private JLabel labelBut;
   
    //un bouton pour jouer à 2
    private JButton btnJouer;
    
    //pour jouer contre l'ordinateur
    private JLabel labelOrdi;
    private JTextField champSaisieOrdi;
    private JButton btnOrdi;
        
    public fenetreMorpion(){
        super("Jeu Morpion");
		
		// ====== Instanciation des widgets de la fenetre entree =====
        labelJoueur1 = new JLabel("Joueur 1 : ");
        champSaisieJoueur1 = new JTextField("",10);

        labelJoueur2 = new JLabel("Joueur 2 : ");
        champSaisieJoueur2 = new JTextField("",10);

		
        labelBut = new JLabel("<html><center><font size=25>C'est un Morpion infini! Quand vous vous rapprochez des bords, le tableau augmente <br>"+"<B><U>But:</B></U>"+" Aligner "+"<font color=red>5 </font>"+"pions</font>",JLabel.CENTER);
        
        btnJouer = new JButton("Jouer a deux!");
        btnJouer.addActionListener(new ListenerAccueil(this));
        
        labelOrdi = new JLabel("Joueur unique: ");
        champSaisieOrdi = new JTextField("",10);
        btnOrdi = new JButton("Jouer contre l'ordinateur!");
        btnOrdi.addActionListener(new ListenerOrdi(this));
        
        // ====== Organisation structurelle ======
          
          
        window.setSize(300,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel( new GridLayout(2, 1));
		panel.setBackground(Color.white);
		panel.add(new JLabel(new ImageIcon("TicTacToe (1).jpeg")));
        panel.add(labelBut);
        labelBut.setFont(new Font("Serif", Font.PLAIN, 21));
			
		JPanel monPanelEntree= new JPanel();
        
        monPanelEntree.setBackground(Color.yellow);
        monPanelEntree.add(labelJoueur1);
        monPanelEntree.add(champSaisieJoueur1);
        monPanelEntree.add(labelJoueur2);
        monPanelEntree.add(champSaisieJoueur2);
        monPanelEntree.add(btnJouer)  ;
        monPanelEntree.add(labelOrdi);
        monPanelEntree.add(champSaisieOrdi);
        monPanelEntree.add(btnOrdi);
            
        window.add(panel, BorderLayout.CENTER);
        window.add(monPanelEntree, BorderLayout.SOUTH);

        window.setVisible(true);  
        
        window.setSize(300,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	public String getNomJoueur(int x){
		if (x==1){
			return champSaisieJoueur1.getText();
		}else if (x==2){
			return champSaisieJoueur2.getText();
		}
		else if(x==3){
			return champSaisieOrdi.getText();
		}
		else {
			return "";
		}
		
	}
 }
    

