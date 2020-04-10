import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerNewGame implements ActionListener{
	private MorpionInterface inter;
	
	public ListenerNewGame(MorpionInterface inter){
		this.inter = inter;
	}
	
	public void actionPerformed(ActionEvent e){
		inter.resetBoard();
	}
    
}
