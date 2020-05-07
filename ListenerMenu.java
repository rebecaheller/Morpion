import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerMenu implements ActionListener{
	private MorpionInterface inter;
	private fenetreAccueil fen;

	public ListenerMenu(MorpionInterface inter){
		this.inter = inter;
	}

	public void actionPerformed(ActionEvent e){
		inter.dispose();
	}

}
