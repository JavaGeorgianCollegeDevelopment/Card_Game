package MatchCardGame;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Display extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Display frame = new Display();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Display() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // on clicking exit, close the program
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setMinimumSize(new Dimension(450,600));
		GridLayout mainBox = new GridLayout(5, 1, 5, 5);
		contentPane.setLayout(mainBox);		
		for (int i = 0; i < 4; i++) {
			Box box = new Box(1);
			if(i == 0)
			{
				Button newGameButton = new Button("New Game");
				box.add(newGameButton);
			}
			contentPane.add(box);
		}
		// loop through cards and set to display
		for (int i = 0; i < 16; i++) {
			try 
			{
				Image img = ImageIO.read(getClass().getResource("resources/Card Images/cardback.png"));
				contentPane.add(new JButton(new ImageIcon(img)));
			}
			catch(IOException ex) 
			{				
			}			
		}
	}

}
