import java.util.Arrays; 
import java.util.Collections; 
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Board {
	// Attributs
	public JButton[][] board;
	private HashMap<JButton, int[]> buttonIndexMap; // Hashmap de boutons qui a comme value (valeur) les indices des positions des boutons
	private int N;
	private boolean singlePlayer; 
	private final int objective; // Nombre de pions qu'il faut aligner pour gagner
	private final MorpionInterface inter;
	
	// Constructeur
	public Board(int N, int objective, MorpionInterface inter, boolean singlePlayer) {
		this.N = N;
		this.objective = objective;
		this.inter = inter;
		this.singlePlayer = singlePlayer;
		buttonIndexMap = new HashMap<JButton, int[]>();
		initializeBoard();
	}
	
	// Méthodes
	
	private void initializeBoard(){
		board = new JButton[N][N];
		for(int i = 0; i<N; i++){
			for(int j = 0; j<N; j++){
				JButton btn = new JButton();
				btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 350/N)); //setFont définit la police
				board[i][j] = btn;
				buttonIndexMap.put(btn, new int[] {i, j});
				btn.addActionListener(new ListenerBoard(inter));
			}
		}
		// Si on joue contre l'ordinateur, il est le premier à jouer. Alors on initialize le tableau avec le pion X au milieu (position stratégique)
		if(singlePlayer==true){
			board[2][2].setText("x");
			board[2][2].setFont(new Font(Font.SANS_SERIF,Font.BOLD, 350/N));
		}
	}
	
	public void changeText(String currentPlayer, int i, int j) {
		board[i][j].setText(currentPlayer);
	}
	
	public void resetBoard(int N) {
		this.N = N;
		buttonIndexMap.clear(); // clear hashmap
		initializeBoard();
	}
	
	public JButton getButton(int i, int j) {
		return board[i][j];
	}
	
	public int getBoardSize(){
		return N;
	}
	
	public int[] getButtonPosition(JButton btn) {
		return buttonIndexMap.get(btn);
	}
	
	// Vérifie si on a 5 pions alignés dans une colonne. On fixe j et on fait varier i. (i et j obtenus avec le hashmap des positions des boutons) 
	private int counterColumn(int i, int j){
		int k = i;
		int counter = 0; 
		String player = board[i][j].getText();
		
		while(k>=0 && board[k][j].getText() == player){
			k--;
			counter++;
		}
		k = i + 1;
		while(k<N && board[k][j].getText() == player){
			k++;
			counter++;
		}
		return counter;
	}
	
	// Vérifie si on a 5 pions alignés dans une ligne.  On fixe i et on fait varier j. (i et j obtenus avec le hashmap des positions des boutons) 
	private int counterLine(int i, int j){
		int k = j; 
		int counter=0;
		String player = board[i][j].getText();
		
		while(k >= 0 && board[i][k].getText() == player){
			k--;
			counter++;
		}
		k = j + 1; // On adicione 1 pour ne pas compter deux fois la même position
		while(k<N && board[i][k].getText() == player){
			k++;
			counter++;
		}
		return counter;
	}
	
	// Vérifie si on a 5 pions alignés dans une diagonale/antidiagonale. (i et j obtenus avec le hashmap des positions des boutons) 
	//L répresente la longueur qu'on cherche
	private int counterDiagonal(int i, int j){
		int counter=0;
		int k=0;
		String player = board[i][j].getText();
		
		// Diagonales:
		// On doit reinitialiser les variables 
		while( (i+k)<N && (j+k)<N && board[i+k][j+k].getText() == player){
			counter++;
			k++;
		}
		k = 1;
		while( (i-k)>=0 && (j-k)>=0 && board[i-k][j-k].getText() == player){
			counter++;
			k++;
		}
		return counter;	
	}
	
	private int counterAntiDiagonal(int i, int j) {
		int counter=0;
		int k=0;
		String player = board[i][j].getText();
		
		// Antidiagonales:
		while( (i+k)<N && (j-k)>=0 && board[i+k][j-k].getText() == player){
			counter++;
			k++;
		}
		k = 1; // On adicione 1 pour ne pas compter deux fois la même position 
		while( (i-k)>=0 && (j+k)<N && board[i-k][j+k].getText() == player){
			counter++;
			k++;
		}
		return counter;
	}
	
	// On vérifie si le jeu est fini: si il y a 5 pions sur une ligne, colonne, diagonale ou antidiagonale 
	public boolean isGameOver(int i, int j) {

		if (counterColumn(i, j) >= objective || counterLine(i, j) >= objective || counterDiagonal(i, j) >= objective || counterAntiDiagonal(i, j) >= objective ) {		
			return true;
		}
		else{
			// On continue à jouer
			return false;
		}	
	}
	
	// Cette methode adicone deux nouvelles lignes et deux nouvelles colonnes vides à notre ancien tableau
	public void changeDimension(){
		
		JButton[][] copy = new JButton [N+2][N+2]; // On crée un tableau pour copier l'ancien
		buttonIndexMap.clear(); // efface le hashmap
		
		// Initialise la première ligne du tableau avec boutons vides (nouvelle ligne). On fixe i et on varie j.
		int i=0;
		int j=0; 
		for(j = 0; j<N+2; j++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 350/N + 10));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(inter));
		}
		
		// Initialise la dernière ligne du tableau avec boutons vides (nouvelle ligne). On fixe i et on varie j.
		i=N+1; 
		for(j = 0; j<N+2; j++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,350/N +10));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(inter));
		}
		
		// Initialise la première colonne du tableau avec boutons vides (nouvelle colonne). On fixe j et on varie i.
		j = 0;
		for(i = 0; i<N+2; i++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,350/N +10));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(inter));
		}
		
		// Initialise la dernière colonne du tableau avec boutons vides (nouvelle colonne). On fixe j et on varie i.
		j = N+1;
		for(i = 0; i<N+2; i++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,350/N +10));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(inter));
		}
		
		// Enregistre l'information de l'ancien tableau, ça commence dans la position 1,1 pour ne pas perdre les nouveux ajouts	
		for(i=0; i<N; i++){
			for(j=0; j<N; j++){
				board[i][j].setFont(new Font(Font.SANS_SERIF,Font.BOLD, 350/N+2 +10));
				copy[i+1][j+1] = board[i][j];
				// Actualisation du hashmap:
				buttonIndexMap.put(copy[i+1][j+1], new int[] {i+1, j+1});
			}
		}	
		board = copy;
		N = N+2;
	}
	
	private int emptyExtremitiesLine(int i, int j){
		int emptyCases = 0;
		int k = j;
		
		while (k >= 0 && board[i][k].getText() == "o") {
			k--;
		}
		if (k >= 0 && board[i][k].getText() != "x") {
			emptyCases++;
		}
		k = j;
		while (k < N && board[i][k].getText() == "o") {
			k++;
		}
		if (k < N && board[i][k].getText() != "x") {
			emptyCases++;
		}
		return emptyCases;
	}
	
	private int emptyExtremitiesColumn(int i, int j){
		int emptyCases = 0;
		int k = i;
		
		while (k >= 0 && board[k][j].getText() == "o") {
			k--;
		}
		if (k >= 0 && board[k][j].getText() != "x") {
			emptyCases++;
		}
		k = i;
		while (k < N && board[k][j].getText() == "o") {
			k++;
		}
		if (k < N && board[k][j].getText() != "x") {
			emptyCases++;
		}
		return emptyCases;
	}
	
	private int emptyExtremitiesDiagonal(int i, int j){
		int emptyCases = 0;
		
		int k = 1;
		while (i-k >= 0 && j-k >= 0 && board[i-k][j-k].getText() == "o") {
			k++;
		}
		if (i-k >= 0 && j-k >= 0 && board[i-k][j-k].getText() != "x") {
			emptyCases++;
		}
		k = 1;
		while (i+k < N && j+k < N && board[i+k][j+k].getText() == "o") {
			k++;
		}
		if (i+k < N && j+k < N && board[i+k][j+k].getText() != "x") {
			emptyCases++;
		}
		return emptyCases;
	}
	
	private int emptyExtremitiesAntiDiagonal(int i, int j){
		int emptyCases = 0;
		
		int k = 1;
		while (i-k >= 0 && j+k < N && board[i-k][j+k].getText() == "o") {
			k++;
		}
		if (i-k >= 0 && j+k < N && board[i-k][j+k].getText() != "x") {
			emptyCases++;
		}
		k = 1;
		while (i+k < N && j-k >= 0 && board[i+k][j-k].getText() == "o") {
			k++;
		}
		if (i+k < N && j-k >= 0 && board[i+k][j-k].getText() != "x") {
			emptyCases++;
		}
		return emptyCases;
	}

	public int[] computerPlays(boolean easy){
		
		if (easy == true) { // Si on joue le mode facile (positions aléatoires)
			return new int[] {0, 0};
		}
		else {
			// 1. if about to win the game, win it (:
			// 2. prevent your opponent to win the game
			// 3. expand my best partial solution
			
			int[] indexMaxSolution = new int[] {0, 0}; 
			int lengthMaxSolution = 0;
			
			for (int i=0; i < N; i++) {
				for (int j=0; j < N; j++) {
					
					if (board[i][j].getText() == "") {
						// essayer de gagner
						board[i][j].setText("x");
						
						Integer[] counters = new Integer[4];
						counters[0] = counterLine(i, j);
						counters[1] = counterColumn(i, j);
						counters[2] = counterDiagonal(i, j);
						counters[3] = counterAntiDiagonal(i, j);
						
						int maxCounter = Collections.max(Arrays.asList(counters));
						
						if (lengthMaxSolution < maxCounter) {
							lengthMaxSolution = maxCounter;
							indexMaxSolution = new int[] {i, j};
						}
												
						if (maxCounter >= objective) {
							board[i][j].setText("");
							// retourne la position qui nous fait gagner le jeu
							return new int[] {i, j};
						}
						else {
							// prevenir l'autre joueur de gagner 
							board[i][j].setText("o");
							if (counterLine(i, j) >= objective || counterColumn(i, j) >= objective || counterDiagonal(i, j) >= objective || counterAntiDiagonal(i, j) >= objective) {
								board[i][j].setText("");
								// retourne la position qui evite l'autre joueur de gagner 
								return new int[] {i, j};
							}
							else {
								// prevenir l'autre joueur d'arriver à faire 4 avec extremités libres
								
								if (counterLine(i, j) == objective - 1) {
									System.out.println(counterLine(i, j));
									System.out.println(emptyExtremitiesLine(i, j));
									System.out.println("");
									
									if (emptyExtremitiesLine(i, j) == 2) {
										board[i][j].setText("");
										return new int[] {i, j};
									}
								}
								if (counterColumn(i, j) == objective - 1) {
									if (emptyExtremitiesColumn(i, j) == 2) {
										board[i][j].setText("");
										return new int[] {i, j};
									}
								}
								if (counterDiagonal(i, j) == objective - 1) {
									if (emptyExtremitiesDiagonal(i, j) == 2) {
										board[i][j].setText("");
										return new int[] {i, j};
									}
								}
								if (counterAntiDiagonal(i, j) == objective - 1) {
									if (emptyExtremitiesAntiDiagonal(i, j) == 2) {
										board[i][j].setText("");
										return new int[] {i, j};
									}
								}
							}
						}
						board[i][j].setText("");
					}	
				}		
			}	
			
			// grandir la plus grande solution de "x"
			return indexMaxSolution;
		}
	}
	
}

