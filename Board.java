 import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Board {
	
	private JButton[][] board;
	private HashMap<JButton, int[]> buttonIndexMap;
	private int N;
	private final int objective; // number of elements to win
	private final MorpionInterface inter;
	
	public Board(int N, int objective, MorpionInterface inter) {
		this.N = N;
		this.objective = objective;
		this.inter = inter;
		buttonIndexMap = new HashMap<JButton, int[]>();
		initializeBoard();
	}
	
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
	
	private boolean checkColumn(int i, int j){
		int k = i;
		int counter = 0; 
		String player = board[i][j].getText();
		
		while(k>=0 && board[k][j].getText() == player && counter < objective){
			k--;
			counter++;
		}
		k = i + 1;
		while(k<N && board[k][j].getText() == player && counter < objective){
			k++;
			counter++;
		}
		if( counter == objective){
			return true;
		}
		else{ return false;}
	}
	
	private boolean checkLine(int i, int j){
		int k = j; 
		int counter=0;
		String player = board[i][j].getText();
		
		while(k >= 0 && board[i][k].getText() == player && counter < objective){
			k--;
			counter++;
		}
		k = j + 1;
		while(k<N && board[i][k].getText() == player && counter < objective){
			k++;
			counter++;
		}
		if( counter == objective){
			return true;
		}
		else{ return false;}
	}
	
	private boolean checkDiagonals(int i, int j){
		int counter=0;
		String player = board[i][j].getText();
		
		int k=0;
		//antidiagonal
		while( (i+k)<N && (j-k)>=0 && board[i+k][j-k].getText() == player && counter < objective){
			counter++;
			k++;
		}
		k = 1;
		while( (i-k)>=0 && (j+k)<N && board[i-k][j+k].getText() == player && counter < objective){
			counter++;
			k++;
		}
		if( counter == objective){
			return true;
		}
		
		// diagonal
		k=0;
		counter = 0;
		while( (i+k)<N && (j+k)<N && board[i+k][j+k].getText() == player && counter < objective){
			counter++;
			k++;
		}
		k = 1;
		while( (i-k)>=0 && (j-k)>=0 && board[i-k][j-k].getText() == player && counter < objective){
			counter++;
			k++;
		}
		if( counter == objective){
			return true;
		}
		else{return false;}	
	}
	
	public boolean isGameOver(int i, int j) {
	
		if (checkColumn(i, j) || checkLine(i, j) || checkDiagonals(i, j) ) {		
			return true;
		}
		else{
			// we continue to play
			return false;
		}	
	}
	
	public void changeDimension(){
		//new board to save older board's informations
		JButton[][] copy = new JButton [N+2][N+2];
		// clear hashmap
		buttonIndexMap.clear();
		
		//sets the first line with empty cases (new line of the board)
		int i=0;
		int j=0; 
		for(j = 0; j<N+2; j++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,350/N));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(inter));
		}
		
		//sets the last line with empty cases (new line of the board)
		i=N+1; 
		for(j = 0; j<N+2; j++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,350/N));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(inter));
		}
		
		//sets the first column with empty cases
		j = 0;
		for(i = 0; i<N+2; i++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,350/N));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(inter));
		}
		
		//sets the last column with emtpy cases
		j = N+1;
		for(i = 0; i<N+2; i++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,350/N));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(inter));
		}
		
		//saves the information from the older board starting from line 1 so we don't loose the new empty line
		for(i=0; i<N; i++){
			for(j=0; j<N; j++){
				board[i][j].setFont(new Font(Font.SANS_SERIF,Font.BOLD, 350/N+2));
				copy[i+1][j+1] = board[i][j];
				// update index map
				buttonIndexMap.put(copy[i+1][j+1], new int[] {i+1, j+1});
			}
		}	
		board = copy;
		N = N+2;
	}
}

