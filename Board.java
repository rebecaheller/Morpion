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
	
	public int getObjective() {
		return objective;
	}
	
	public int[] getButtonPosition(JButton btn) {
		return buttonIndexMap.get(btn);
	}
	
	// Vérifie si on a 5 pions alignés dans une colonne. On fixe j et on fait varier i. (i et j obtenus avec le hashmap des positions des boutons) 
	public int counterColumn(int i, int j){
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
	public int counterLine(int i, int j){
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
	public int counterDiagonal(int i, int j){
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
	
	public int counterAntiDiagonal(int i, int j) {
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
}
