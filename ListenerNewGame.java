import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerNewGame implements ActionListener{
	private MorpionInterface inter;
	
	public ListenerNewGame(MorpionInterface inter){
		this.inter = inter;
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(inter.doesComputerPlay()) {
			
			String nom=inter.getPlayerName();
			//comme ça meme si on clique plusieurs fois sur le bouton NewGame cela n'alterne pas qui joue (toujours le player qui pose le 1er pion)
			if(nom!="Ordinateur"){
				inter.switchNames();
				inter.switchPlayers();
			}
		}
			
		inter.resetGame();
	
	}
    
}
