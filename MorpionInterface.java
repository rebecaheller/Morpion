import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MorpionInterface extends JFrame{
	//attributs
	private JPanel pane;
	private String currentPlayer;
	private JButton[][] board;
	private HashMap<JButton, int[]> buttonIndexMap;
	private final int objective=5; // number of elements to win
	private JButton quit;
	private JButton newGame;
	private JButton menu;
	private JPanel support;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel center;
	private JLabel playerLabel;
	private JLabel leftLabel;
    private JLabel rightLabel;
    private int N=5; //taille initiale de la matrice
	
	//constructors
	public MorpionInterface(){
		
		super();
		this.support= new JPanel(new BorderLayout());
		pane = new JPanel(new GridLayout(5,5));
		northPanel= new JPanel();
		northPanel.setPreferredSize(new Dimension(50,100));
		southPanel= new JPanel();
		southPanel.setPreferredSize(new Dimension(100,100));
		rightPanel= new JPanel();
		rightPanel.setPreferredSize(new Dimension(100,100));
		leftPanel= new JPanel();
		leftPanel.setPreferredSize(new Dimension(100,100));
		
		newGame = new JButton("New Game");
		newGame.addActionListener(new ListenerNewGame(this));
		menu = new JButton("Menu");
		menu.addActionListener(new ListenerMenu(this));
		quit = new JButton("Quit");
		quit.addActionListener(new ListenerQuit(this));
		
		leftLabel= new JLabel("  ");
		rightLabel = new JLabel("  ");
		
		leftPanel.add(leftLabel);
		rightPanel.add(rightLabel);
		
		newGame.setBackground(Color.GREEN);
		quit.setBackground(Color.RED);
		southPanel.add(newGame);
		southPanel.add(quit);
		southPanel.add(menu);
		
		
		playerLabel = new JLabel("Player");
		northPanel.add(playerLabel);
		support.add(northPanel, BorderLayout.NORTH);
		support.add(southPanel, BorderLayout.SOUTH);
		support.add(leftPanel, BorderLayout.EAST);
		support.add(rightPanel, BorderLayout.WEST);
        support.add(pane, BorderLayout.CENTER);
        
        setTitle("Morpion");
        this.setSize(10000,10000);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(support);
		setVisible(true);
		buttonIndexMap = new HashMap<JButton, int[]>();
		currentPlayer = "x";
		initializeBoard();
	}
	
	
	//methods
	public String getCurrentPlayer(){
		return currentPlayer;
	}
	
	public int getBoardSize(){
		return board[0].length;
	}
	
	private void initializeBoard(){
		board = new JButton[N][N];
		for(int i = 0; i<N; i++){
			for(int j = 0; j<N; j++){
				JButton btn = new JButton();
				btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 100));
				board[i][j] = btn;
				buttonIndexMap.put(btn, new int[] {i, j});
				btn.addActionListener(new ListenerBoard(this));
				pane.add(btn);
			}
			
		}
	}
	
	public void switchPlayers(){
		if(currentPlayer.equals("x")){
			currentPlayer="o";
		}
		else {
			currentPlayer="x";
		}
	}
	
	public int[] getButtonPosition(JButton btn) {
		return this.buttonIndexMap.get(btn);
	}
	
	public boolean checkColumn(int i, int j){
		int k = i;
		int N = board[0].length; 
		int counter=0; 
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
	
	public boolean checkLine(int i, int j){
		int k = j;
		int N = board[0].length; 
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
	
	public boolean checkDiagonals(int i, int j){
		int N = board[0].length;
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
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,100));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(this));
		}
		
		//sets the last line with empty cases (new line of the board)
		i=N+1; 
		for(j = 0; j<N+2; j++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,100));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(this));
		}
		
		//sets the first column with empty cases
		j = 0;
		for(i = 0; i<N+2; i++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,100));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(this));
		}
		
		//sets the last column with emtpy cases
		j = N+1;
		for(i = 0; i<N+2; i++){
			JButton btn = new JButton();
			btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,100));
			copy[i][j] = btn;
			buttonIndexMap.put(btn, new int[] {i, j});
			btn.addActionListener(new ListenerBoard(this));
		}
		
		//saves the information from the older board starting from line 1 so we don't loose the new empty line
		for(i=0; i<N; i++){
			for(j=0; j<N; j++){
				copy[i+1][j+1] = board[i][j];
				// update index map
				buttonIndexMap.put(copy[i+1][j+1], new int[] {i+1, j+1});
			}
		}	
		board = copy;
		N=N+2;
	}
	
	public void updatePane(){
		support.remove(pane);
		pane = new JPanel(new GridLayout(N, N));
		for(int i=0; i < N; i++){
			for(int j=0; j < N; j++){
				pane.add(board[i][j]);
			}
		}
		support.add(pane, BorderLayout.CENTER);
	}
	
	public void resetBoard(){ // à refaire
		currentPlayer = "x";
		buttonIndexMap.clear(); // clear hashmap
		N=5;
		initializeBoard();
		support.revalidate();
		support.repaint();
		updatePane();
	}
	

}

