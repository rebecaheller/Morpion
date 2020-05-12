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
		
		if(!btn.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Case indisponible");
			return;
		} 
		else {
			boolean gameEnded = play(inter.getCurrentPlayer(), i, j); 
			
			if(gameEnded){
				inter.switchPlayers();
				inter.resetGame();
				return;
			}
			
			inter.switchPlayers();
			
			//Si on joue contre l'ordinateur
			if(inter.doesComputerPlay()){
				int[] positionToPlay;
				positionToPlay = inter.computer.computerPlays();
				gameEnded = play(inter.getCurrentPlayer(), positionToPlay[0], positionToPlay[1]);
				
				if(gameEnded){
					inter.resetGame();
					return;
				}
				
				inter.switchPlayers();		
			}

		}
	}
	
	// Comme la demarche  principale est la même quand on joue à deux ou contre l'ordinateur, on fait une méthode qu'on utilise dans le deux types de jeu
	// Retourne true si le jeu a terminé après l'appui du bouton de position i,j
	public boolean play(String currentPlayer, int i, int j){
		
		inter.board.changeText(currentPlayer, i, j);
		
		// On vérifie si quelqu'un a gagné après l'appui du bouton
		if (inter.board.isGameOver(i, j)) {
			String nom=inter.getPlayerName();
			
			if(inter.doesComputerPlay() && nom=="Ordinateur"){
				JOptionPane.showMessageDialog(null, "L'ordinateur a gagne!");
				return true;
			}else{
				JOptionPane.showMessageDialog(null, nom + " a gagne!");
				return true;
			}
		}							
		
		//On vérifie si on est sur l'un des bords et si personne n'a gagné. Alors on augmente le tableau
		int n = inter.board.getBoardSize();
		if(i == 0 || i==n-1 || j == 0 || j == n-1){
			inter.board.changeDimension();
			inter.updatePane();
		}
		
		return false;
	}	
}
