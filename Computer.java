import java.util.Arrays; 
import java.util.Collections; 
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Computer{

	// Attributs
	private Board board;
	private int [] indexMaxSolution; // Un tableau qui va garder la position de la solution maximale de l'ordinateur
	private int lengthMaxSolution; // Taille de la solution maximale
	
	
	//Constructeur
	public Computer(Board board){
		this.board=board;
		indexMaxSolution = new int[] {0, 0}; 
		lengthMaxSolution=0; // Initialement il n'y a acune solution, donc la longueur est 0
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
	
	// Actualise la position de la solution maximale à partir d'un parametre maxCounter
	private void updateMaxSolution(int maxCounter, int i, int j){
		if (lengthMaxSolution < maxCounter) {
			lengthMaxSolution = maxCounter;
			indexMaxSolution = new int[] {i, j};
		}
	}
	
	// Essaye de gagner. Retourne true si l'ordi peut gagner en jouant dans cette position (i,j).
	private boolean tryToWin(int i, int j){
		
		board.getButton(i, j).setText("x"); // On met un x dans chaque bouton pour qu'il soit compté dans les compteurs 
		
		Integer[] counters = new Integer[4]; // C'est un tableau dont chaque case represent un compteur des 4 directions possibles
		counters[0] = board.counterLine(i, j);
		counters[1] = board.counterColumn(i, j);
		counters[2] = board.counterDiagonal(i, j);
		counters[3] = board.counterAntiDiagonal(i, j);
		int maxCounter = Collections.max(Arrays.asList(counters)); // Le max counter est la solution plus longue
		
		updateMaxSolution(maxCounter, i, j); // On actualise la position de la solution maximale
		
		board.getButton(i, j).setText(""); //On remet la case à vide
							
		if (maxCounter >= board.getObjective()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Empeche l'autre joueur de gagner. Retourne true si l'ordi peut perdre si l'autre joueur joue dans cette position (i,j).
	private boolean stopOpponent(int i, int j){
		boolean possibleLoss = false;
		board.getButton(i, j).setText("o"); // On met un o aussi pour que la case soit compté par le compteur
		int objective = board.getObjective();
		
		//  Evite l'autre joueur de gagner dans ce tour
		if (board.counterLine(i, j) >= objective || board.counterColumn(i, j) >= objective || board.counterDiagonal(i, j) >= objective || board.counterAntiDiagonal(i, j) >= objective) {
			
			possibleLoss = true;
		}
		// Empeche l'autre joueur d'arriver à mettre 4 pions ensemble avec 2 extremités libres (pour qu'il ne gagne pas dans le prochain tour)
		else {
			if (board.counterLine(i, j) == objective - 1 && emptyExtremitiesLine(i, j) == 2) {
				possibleLoss = true;
			}
			if (board.counterColumn(i, j) == objective - 1 && emptyExtremitiesColumn(i, j) == 2) {
				possibleLoss = true;
			}
			if (board.counterDiagonal(i, j) == objective - 1 && emptyExtremitiesDiagonal(i, j) == 2) {				
				possibleLoss = true;
			}	
			if (board.counterAntiDiagonal(i, j) == objective - 1 && emptyExtremitiesAntiDiagonal(i, j) == 2) {
				possibleLoss = true;				
			}
		}
		//On remet la case à vide
		board.getButton(i, j).setText("");
		
		return possibleLoss;
	}
	
	//Retourne la position que l'ordinateur doit jouer 
	public int[] computerPlays(){
		// On réinitialise la solution maximale
		indexMaxSolution = new int[] {0, 0};  
		lengthMaxSolution=0;
		
		int N = board.getBoardSize();
		for (int i=0; i < N; i++) {
			for (int j=0; j < N; j++) {
				
				// On verifie d'abbord si la case est vide
				if (board.getButton(i, j).getText() == "") {
					
					//1- on essaye de gagner
					if (tryToWin(i, j)) {
						return new int[] {i, j};
					}
					else {
					//2- si on peut pas gagner, on empeche l'autre joueur de gagner	
						if (stopOpponent(i, j)) {
							return new int[] {i, j};
						}							
					}
						
				}	
			}		
		}	
		
		// 3-Si l'autre joueur n'est pas proche de gagner, on grandi la plus grande solution de l'ordinateur
		return indexMaxSolution;
	}
	
}


