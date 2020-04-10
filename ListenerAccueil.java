import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerAccueil implements ActionListener{
	private fenetreMorpion fen;
	//private MorpionInterface inter;
	
	public ListenerAccueil(fenetreMorpion fen){
		this.fen = fen;
	}
	
	public void actionPerformed(ActionEvent e){
		new MorpionInterface();
	}
	
}
