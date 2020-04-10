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
	private JPanel support;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel center;
	private JLabel playerLabel;
	private JLabel leftLabel;
    private JLabel rightLabel;
    public int N=5; //taille initiale de la matrice
	
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
		center= new JPanel();
		center.setPreferredSize(new Dimension(100,100));
		center.setBackground(Color.BLUE);
		center.add(pane);
		
		newGame = new JButton("New Game");
		newGame.addActionListener(new ListenerNewGame(this));
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
		board = new JButton[N][N];
		buttonIndexMap = new HashMap<JButton, int[]>();
		currentPlayer = "x";
		initializeBoard();
		
	}
	
	
	//methods
	public String getCurrentPlayer(){
		return currentPlayer;
	}
	
	public int[] getButtonPosition(JButton btn) {
		return this.buttonIndexMap.get(btn);
	}
	
	public void resetBoard(){
		currentPlayer = "x";
		for(int i = 0; i<N; i++){
			for(int j = 0; j<N; j++){
				board[i][j].setText("");
			}
		}
	}
	private void initializeBoard(){
		for(int i = 0; i<N; i++){
			for(int j = 0; j<N; j++){
				JButton btn = new JButton();
				btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,100));
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
	
	private void changeDimension(){
		//copie du board
		JButton[][] copie = new JButton [N+2][N+2];
		for(int i=0; i<N; i++){
			for(int j = 0; j<N; j++){
				copie[i][j]=board[i][j];
			}
		}
	
	}
	
	
	

}

