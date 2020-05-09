import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerOrdi implements ActionListener{

	private fenetreAccueil fen;
	private String nomUniqueJoueur;
	private String ordinateur;
	private MorpionInterface inter;
	
	public ListenerOrdi(fenetreAccueil fen){
		this.fen = fen;
	}
	
	public void actionPerformed(ActionEvent e){
		int x = 3; // Quand x=3 on obtient le nom du single player mis dans le champ saisie ordi 
		String ordi = "Ordinateur"; // L'ordi sera la joueur 1
		String nomUniqueJoueur = fen.getNomJoueur(x); // Le single player sera le jouer 2
		
		if(nomUniqueJoueur==null || nomUniqueJoueur.equals("")){ //on met condition "" car au départ la case n'est jamais null
			JOptionPane.showMessageDialog(null, "Inserez votre nom!");
			
		}else{
			MorpionInterface inter = new MorpionInterface(ordi, nomUniqueJoueur);
			inter.switchPlayers(); //comme ça le board est initilisé avec le premier mouvement de l'ordi et le jeu commence avec le joueur 2
		}
	}
}
	

