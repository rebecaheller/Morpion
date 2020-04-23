import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MorpionInterface extends JFrame{
	//attributs
	private final int objective=5; // number of elements to win
	private final int N=5; //taille initiale de la matrice

	public Board board; // set as public to avoid writting a getter
	
	private JPanel pane;
	private String currentPlayer;
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
    private String nom1;
    private String nom2;
	
	//constructors
	public MorpionInterface(String name1,String name2){
		
		super();
		nom1 = name1;
		nom2 = name2;
		this.support = new JPanel(new BorderLayout());
		pane = new JPanel(new GridLayout(N, N));
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(50, 100));
		southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(100, 100));
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 100));
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, 100));
		
		menu = new JButton("Menu");
		menu.addActionListener(new ListenerMenu(this));
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
		southPanel.add(menu);
		playerLabel = new JLabel("Player: "+name1);
		northPanel.add(playerLabel);
		support.add(northPanel, BorderLayout.NORTH);
		support.add(southPanel, BorderLayout.SOUTH);
		support.add(leftPanel, BorderLayout.EAST);
		support.add(rightPanel, BorderLayout.WEST);
        support.add(pane, BorderLayout.CENTER);
        
        setTitle("Morpion");
        this.setSize(10000, 10000);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(support);
		setVisible(true);
		
		currentPlayer = "x";
		board = new Board(N, objective, this);
		updatePane();
	}
		
	//methods
	public String getCurrentPlayer(){
		return currentPlayer;
	}
	
	public void switchPlayers(){
		if(currentPlayer.equals("x")){
			currentPlayer="o";
			playerLabel.setText("Player: "+nom2);
		}
		else {
			currentPlayer="x";
			playerLabel.setText("Player: "+nom1);
		}
	}
	
	public void updatePane(){
		support.remove(pane);
		int boardDimension = board.getBoardSize();
		pane = new JPanel(new GridLayout(boardDimension, boardDimension));
		for(int i=0; i < boardDimension; i++){
			for(int j=0; j < boardDimension; j++){
				pane.add(board.getButton(i, j));
			}
		}
		support.add(pane, BorderLayout.CENTER);
	}
	
	public void resetGame(){
		currentPlayer = "x";
		board.resetBoard(N);
		support.revalidate();
		support.repaint();
		updatePane();
	}
}
