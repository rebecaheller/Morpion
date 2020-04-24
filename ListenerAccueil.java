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
		int x = 1;
		int y = 2;
		
		String nomJoueur1 = fen.getNomJoueur(x);
		String nomJoueur2 = fen.getNomJoueur(y);
		
		if(nomjoueur1==null || nomJoueur1.equals("")|| nomJoueur2==null || nomJoueur2.equals("")){ //on met condition "" car au départ la case n'est jamais null
			JOptionPane.showMessageDialog(null, "Inserez le nom des joueurs!");
		}else{
			new MorpionInterface(nomJoueur1, nomJoueur2);
		}
	}
}
