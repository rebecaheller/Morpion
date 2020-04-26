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
		int i = index[0];
		int j = index[1];
		
		System.out.println(inter.getCurrentPlayer());
		
		if(!btn.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Case indisponible");
			return;
		} 
		else {
			play(inter.getCurrentPlayer(), i, j);
			inter.switchPlayers();
			
			//Si on joue contre l'ordinateur
			if(inter.doesComputerPlay()){
				index = inter.board.computerPlays(i, j);
				play(inter.getCurrentPlayer(), index[0], index[1]);
				inter.switchPlayers();				
			}

		}
	}
	
	public void play(String currentPlayer, int i, int j){

		// JButton btn = ;
		inter.board.changeText(currentPlayer, i, j);
		// btn.;
		
		if (inter.board.isGameOver(i, j)) {
			JOptionPane.showMessageDialog(null,"Joueur " + currentPlayer + " a gagne");
			inter.resetGame();
		}							
		
		//On vérifie si on est sur l'un des bords et si personne n'a gagné. Alors on augmente le tableau
		int N = inter.board.getBoardSize();
		if(i == 0 || i==N-1 || j == 0 || j == N-1 && !inter.board.isGameOver(i, j)){
			inter.board.changeDimension();
			inter.updatePane();
		}
	}	
}
