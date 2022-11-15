package BorderLayout;
import java.awt.*;

import javax.swing.*;


public class BorderLayoutClass extends JFrame {

	public static void main(String[] args) {
		new BorderLayoutClass();

	}
	
	BorderLayoutClass(){
		setTitle("BorderLayout Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		add(new JButton("Package Explorer"),BorderLayout.WEST);
		add(new JButton("Test.java"),BorderLayout.CENTER);
		add(new JButton("Console"),BorderLayout.SOUTH);
		
		setSize(1000,700);
		setVisible(true);
	}

}
