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
	
		int x=1;
		int y=2;
		
		String nomjoueur1=fen.getNomJoueur(x);
		String nomjoueur2=fen.getNomJoueur(y);
	
	
		if(nomjoueur1==null || nomjoueur1.equals("")|| nomjoueur2==null || nomjoueur2.equals("")){ //on met condition "" car au départ la case n'est jamais null
			
			JOptionPane.showMessageDialog(null,"Inserez le nom des joueurs!");
		}else{
			new MorpionInterface();
		
		}
	}
}
	
	

