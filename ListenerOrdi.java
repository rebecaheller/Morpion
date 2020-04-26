import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerOrdi implements ActionListener{

	private fenetreMorpion fen;
	private String nomUniqueJoueur;
	private String ordinateur;
	private MorpionInterface inter;
	
	public ListenerOrdi(fenetreMorpion fen){
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
			inter.switchPlayers();
		}
	}
}
	

