import java.util.Arrays; 
import java.util.Collections; 
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Computer{

	// Attributs
	private Board board;
	private int [] indexMaxSolution;
	private int lengthMaxSolution;
	
	
	//Constructeur
	public Computer(Board board){
		this.board=board;
		indexMaxSolution = new int[] {0, 0}; 
		lengthMaxSolution=0;
	}
	
	// Methodes
	
	//Retourne le nombre d'extremités libres dans une ligne à partir de la position (i,j)
	private int emptyExtremitiesLine(int i, int j){
		int N = board.getBoardSize();
		int emptyCases = 0;
		int k = j;
		
		while (k >= 0 && board.getButton(i, k).getText() == "o") {
			k--;
		}
		if (k >= 0 && board.getButton(i, k).getText() != "x") {
			emptyCases++;
		}
		k = j;
		while (k < N && board.getButton(i, k).getText() == "o") {
			k++;
		}
		if (k < N && board.getButton(i, k).getText() != "x") {
			emptyCases++;
		}
		return emptyCases;
	}
	
	//Retourne le nombre d'extremités libres dans une colonne à partir de la position (i,j)
	private int emptyExtremitiesColumn(int i, int j){
		int N = board.getBoardSize();
		int emptyCases = 0;
		int k = i;
		
		while (k >= 0 && board.getButton(k, j).getText() == "o") {
			k--;
		}
		if (k >= 0 && board.getButton(k, j).getText() != "x") {
			emptyCases++;
		}
		k = i;
		while (k < N && board.getButton(k, j).getText() == "o") {
			k++;
		}
		if (k < N && board.getButton(k, j).getText() != "x") {
			emptyCases++;
		}
		return emptyCases;
	}

	//Retourne le nombre d'extremités libres dans une diagonale à partir de la position (i,j)
	private int emptyExtremitiesDiagonal(int i, int j){
		int N = board.getBoardSize();
		int emptyCases = 0;
		
		int k = 1;
		while (i-k >= 0 && j-k >= 0 && board.getButton(i-k, j-k).getText() == "o") {
			k++;
		}
		if (i-k >= 0 && j-k >= 0 && board.getButton(i-k, j-k).getText() != "x") {
			emptyCases++;
		}
		k = 1;
		while (i+k < N && j+k < N && board.getButton(i+k, j+k).getText() == "o") {
			k++;
		}
		if (i+k < N && j+k < N && board.getButton(i+k, j+k).getText() != "x") {
			emptyCases++;
		}
		return emptyCases;
	}
	
	//Retourne le nombre d'extremités libres dans une antidiagonale à partir de la position (i,j)
	private int emptyExtremitiesAntiDiagonal(int i, int j){
		int N = board.getBoardSize();
		int emptyCases = 0;
		
		int k = 1;
		while (i-k >= 0 && j+k < N && board.getButton(i-k, j+k).getText() == "o") {
			k++;
		}
		if (i-k >= 0 && j+k < N && board.getButton(i-k, j+k).getText() != "x") {
			emptyCases++;
		}
		k = 1;
		while (i+k < N && j-k >= 0 && board.getButton(i+k, j-k).getText() == "o") {
			k++;
		}
		if (i+k < N && j-k >= 0 && board.getButton(i+k, j-k).getText() != "x") {
			emptyCases++;
		}
		return emptyCases;
	}
	
	private void updateMaxSolution(int maxCounter, int i, int j){
		if (lengthMaxSolution < maxCounter) {
			lengthMaxSolution = maxCounter;
			indexMaxSolution = new int[] {i, j};
		}
	}

	private boolean tryToWin(int i, int j){
		// essayer de gagner
		board.getButton(i, j).setText("x");
		
		Integer[] counters = new Integer[4];
		counters[0] = board.counterLine(i, j);
		counters[1] = board.counterColumn(i, j);
		counters[2] = board.counterDiagonal(i, j);
		counters[3] = board.counterAntiDiagonal(i, j);
		int maxCounter = Collections.max(Arrays.asList(counters));
		
		updateMaxSolution(maxCounter, i, j);
		
		//On remet la case à vide
		board.getButton(i, j).setText("");
							
		if (maxCounter >= board.getObjective()) {
			return true;
		}
		else {
			return false;
		}
	}
	 
	private boolean stopOpponent(int i, int j){
		// prevenir l'autre joueur de gagner
		boolean preventLosing = false;
		board.getButton(i, j).setText("o");
		int objective = board.getObjective();
		
		if (board.counterLine(i, j) >= objective || board.counterColumn(i, j) >= objective || board.counterDiagonal(i, j) >= objective || board.counterAntiDiagonal(i, j) >= objective) {
			// retourne la position qui evite l'autre joueur de gagner dans ce tour
			preventLosing = true;
		}
		//Retourne la position qui empeche l'autre joueur d'arriver à mettre 4 pions ensemble avec extremités libres pour qu'il ne gagne pas dans le prochain tour
		else {
			if (board.counterLine(i, j) == objective - 1 && emptyExtremitiesLine(i, j) == 2) {
				preventLosing = true;
			}
			if (board.counterColumn(i, j) == objective - 1 && emptyExtremitiesColumn(i, j) == 2) {
				preventLosing = true;
			}
			if (board.counterDiagonal(i, j) == objective - 1 && emptyExtremitiesDiagonal(i, j) == 2) {				
				preventLosing = true;
			}	
			if (board.counterAntiDiagonal(i, j) == objective - 1 && emptyExtremitiesAntiDiagonal(i, j) == 2) {
				preventLosing = true;				
			}
		}
		//On remet la case à vide
		board.getButton(i, j).setText("");
		
		return preventLosing;
	}

	public int[] computerPlays(boolean easy){
		
		if (easy == true) { // Si on joue le mode facile (positions aléatoires)
			return new int[] {0, 0};
		}
		else {
			// reset maxSolution found
			indexMaxSolution = new int[] {0, 0}; 
			lengthMaxSolution=0;
			
			int N = board.getBoardSize();
			for (int i=0; i < N; i++) {
				for (int j=0; j < N; j++) {
					
					if (board.getButton(i, j).getText() == "") {
						
						if (tryToWin(i, j)) {
							return new int[] {i, j};
						}
						else {
							// prevenir l'autre joueur de gagner 
							if (stopOpponent(i, j)) {
								return new int[] {i, j};
							}							
						}
							
					}	
				}		
			}	
			
			// grandir la plus grande solution de "x"
			return indexMaxSolution;
		}
	}
}


