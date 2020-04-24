import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerBoard implements ActionListener{
	private MorpionInterface inter;
	
	public ListenerBoard(MorpionInterface inter){
		this.inter = inter;
	}
	
	public void actionPerformed(ActionEvent e){
		JButton btn = ((JButton)e.getSource()); // On trouve quel button a été appuyé
		int[] index = inter.board.getButtonPosition(btn); //  Ici index[0] répresente l'indice i, index[1] répresente l'indice j de la position du bouton
		
		// Si on joue à deux personnes (=l'ordinateur ne joue pas)
		if(!inter.doesComputerPlay()){
			if(!btn.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Case indisponible");
			}
			else{
				btn.setText(inter.getCurrentPlayer());
				if (inter.board.isGameOver(index[0], index[1])) {
					JOptionPane.showMessageDialog(null,"Joueur " + inter.getCurrentPlayer() + " a gagne");
					inter.resetGame();
				}
				else {
					inter.switchPlayers();
					int N = inter.board.getBoardSize();
					
					if(index[0] == 0 || index[0]==N-1 || index[1] == 0 ||index[1] == N-1 && !inter.board.isGameOver(index[0], index[1])){
						inter.board.changeDimension();
						inter.updatePane();
					}
				}
			}
		}
		
		// Si on joue contre l'ordinateur
		else{
		
		}
		
	}
}
	

