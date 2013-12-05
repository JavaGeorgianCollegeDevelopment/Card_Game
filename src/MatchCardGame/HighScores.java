/**
 * 
 */
package MatchCardGame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * @author Jean-Luc
 *
 */
public class HighScores extends JFrame 
	implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5524734037838069280L;
	private JPanel contentPane;
	private ArrayList<String> highScoreNames = new ArrayList<String>();
	private ArrayList<Integer> highScoreValues = new ArrayList<Integer>();
	private CardGameEngine gameEngine;
	private boolean highScorer = true;
	private JTextField newName;
	private final static String ADDBUTTONNAME = "addScoreButton";
	private final static String EXITBUTTONNAME = "exitButton";

	public HighScores(CardGameEngine gameEngine)
	{
		this.gameEngine 		= gameEngine;
		this.highScoreNames 	= gameEngine.getHighScoreName();
		this.highScoreValues 	= gameEngine.getHighScores();
		
		setJFrame();
		setContentPane();
		setLayout();
		initiate();
	}
	
	/**
	 * sets the main JFrame
	 */
	private void setJFrame()
	{
		setBounds(100, 100, 850, 400);
	}
	
	/**
	 * Sets the contentPane
	 */
	private void setContentPane()
	{
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		//this.contentPane.setMinimumSize(new Dimension(850,400));
	}
	
	/**
	 * Sets the layout for the contentpane
	 */
	private void setLayout()
	{
		int rowAdder = 3;		
		System.out.print(highScoreNames.size());
		GridLayout mainBox = new GridLayout(highScoreNames.size() + rowAdder, 4, 5, 5); // set grid size based on number of high scores
		System.out.print(mainBox.getColumns());
		this.contentPane.setLayout(mainBox);
	}
	
	private void initiate()
	{				
		Iterator<String> nameNav 	= highScoreNames.iterator();
		Iterator<Integer> scoreNav	= highScoreValues.iterator();
		
		JLabel header 	= new JLabel("HighScores");
		JLabel nameHeader 	= new JLabel("Name");
		JLabel scoreHeader	= new JLabel("High Score");
		

		contentPane.add(header);
		contentPane.add(nameHeader);
		contentPane.add(scoreHeader);
		
		int i = 1;
		
		while(nameNav.hasNext() && scoreNav.hasNext())
		{			
			// Set place Label
			JLabel placeLabel = new JLabel(Integer.toString(i));
			contentPane.add(placeLabel);
			
			// Set name label
			String name 	= nameNav.next();
			
			JLabel nameLabel = new JLabel(name);
			contentPane.add(nameLabel);
			
			// set score name
			Integer score	= scoreNav.next();
			
			JLabel scoreLabel = new JLabel(Integer.toString(score));
			contentPane.add(scoreLabel);
			
			i++;
		}
		

		if(gameEngine.checkHighScore() == 5)
		{
			this.highScorer = false;
		}
		
		// Display Users score		
		// if user had a highscore make name alterable, otherwise set as label
		if(highScorer)
		{			
			JLabel newHeader  = new JLabel("");
			this.newName 	= new JTextField();
			newName.setToolTipText("Enter Name Here");
			contentPane.add(newHeader);
			contentPane.add(newName);			
		}
		else
		{
			JLabel newName = new JLabel("Your Score");
			contentPane.add(newName);
		}
		
		// show last of users score
		JLabel newScore 	= new JLabel(Integer.toString(gameEngine.getScore()));
		contentPane.add(newScore);
		
		JLabel placeHolder1 	= new JLabel("");
		contentPane.add(placeHolder1);
		if(highScorer)
		{
			JButton addScoreButton = new JButton("Save");
			addScoreButton.setName(ADDBUTTONNAME);
			addScoreButton.addMouseListener(this);
			contentPane.add(addScoreButton);
		}
		else
		{
			JLabel placeHolder2 = new JLabel("");
			contentPane.add(placeHolder2);
		}
		
		JButton exitButton = new JButton("Exit");
		exitButton.setName(EXITBUTTONNAME);
		exitButton.addMouseListener(this);
		contentPane.add(exitButton);
		
		GridLayout mainBox = (GridLayout) contentPane.getLayout();
		System.out.print(mainBox.getColumns());		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == 1)
		{
			Component item = arg0.getComponent();
			
			try
			{
				JButton button = JButton.class.cast(item);
				
				if(button.getName() == EXITBUTTONNAME)
				{
					this.dispose();
				}
				else if(button.getName() == ADDBUTTONNAME)
				{
					if(newName.getText().trim() != "")
					{
						gameEngine.changeInitials(gameEngine.checkHighScore(), newName.getText());
						this.dispose();
					}
				}
			}
			catch(ClassCastException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
