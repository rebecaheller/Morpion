import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ListenerQuit implements ActionListener{
	private MorpionInterface inter;
	
	public ListenerQuit(MorpionInterface inter){
		this.inter = inter;
	}
	
	public void actionPerformed(ActionEvent e){
		System.exit(0);
	}
   
}
