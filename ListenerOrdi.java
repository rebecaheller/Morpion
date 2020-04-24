import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerOrdi implements ActionListener{

	private fenetreMorpion fen;
	private String nomUniqueJoueur;
	private String ordinateur;
	
	public ListenerOrdi(fenetreMorpion fen){
		this.fen = fen;
	}
	
	public void actionPerformed(ActionEvent e){
		int x = 3;
		String nomUniqueJoueur = fen.getNomJoueur(x);
		String nomJoueur2 = "Ordinateur";
		
		System.out.println(nomUniqueJoueur);
		if(nomUniqueJoueur==null || nomUniqueJoueur.equals("")){ //on met condition "" car au départ la case n'est jamais null
			JOptionPane.showMessageDialog(null, "Inserez votre nom!");
		}else{
			System.out.println("create interface");
			new MorpionInterface(nomUniqueJoueur, nomJoueur2);
		}
	}
}
	

