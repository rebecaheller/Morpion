import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerAccueil implements ActionListener{
	private fenetreMorpion fen;
	private String nomjoueur1;
	private String nomjoueur2;
	
	public ListenerAccueil(fenetreMorpion fen){
		this.fen = fen;
	}
	
	public void actionPerformed(ActionEvent e){
		int x = 1; // Quand X=1, on obtient le nom du joueur 1
		int y = 2; // Quand X=2, on obtient le nom du joueur 2
		
		String nomJoueur1 = fen.getNomJoueur(x);
		String nomJoueur2 = fen.getNomJoueur(y);
		
		if( nomJoueur1==null || nomJoueur1.equals("")|| nomJoueur2==null || nomJoueur2.equals("")){ //on met condition "" car au départ la case n'est jamais null
			JOptionPane.showMessageDialog(null, "Inserez le nom des joueurs!");
		
		}else{
			new MorpionInterface(nomJoueur1, nomJoueur2);
		}
	}
}
