package MatchCardGame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Main Gui class
 * @author Jean-Luc Desroches
 *
 */
public class Display extends JFrame 
	implements MouseListener{

	/**
	 * variables
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardGameEngine gameEngine;
	private JButton newGameButton;
	private java.awt.Label messageBox, scoreBox, timerBox;
	private JButton[] clickedCards = new JButton[2];
	private JButton[] cardButtons;
	private Image backImg;
	private boolean resetCards;
	private Timer gameTime;
	private static final int GAMETIME = 60;
	private boolean gameEnd = false;

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
	 * Create the GUI to be displayed to be the user
	 */
	public Display() 
	{		
		setJFrame();
		setContentPane();
		setLayout();
		startNewGame();
		setTimer();
	}
	
	/**
	 * Re starts the game by reseting the card array, and reassigning buttons
	 */
	private void restart()
	{
		gameTime.cancel();
		// ensure content pane is empty
		if(contentPane != null)
		{
			this.contentPane.removeAll();
		}
		setLayout();
		startNewGame();
		setTimer();
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	/**
	 * Starts the main game, creates all of the necessary buttons, and sets listeners
	 */
	private void startNewGame()
	{
		this.gameEngine = new CardGameEngine(); // instantiate the game engine
		
		
		// read image from file and set as card back
		try 
		{
			this.backImg = ImageIO.read(getClass().getResource("resources/Card Images/cardback.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		for (int i = 0; i < 4; i++) {
			Box box = new Box(1);
			if(i == 0)
			{
				this.newGameButton = new JButton("New Game");
				this.newGameButton.setName("newGameButton");
				this.newGameButton.addMouseListener(this);
				box.add(this.newGameButton);
			}
			else if(i==1)
			{
				this.messageBox = new Label();
				this.messageBox.setName("messageBox");
				box.add(this.messageBox);
			}
			else if(i==2)
			{
				this.timerBox = new Label();
				this.timerBox.setText(Integer.toString(GAMETIME));
				box.add(this.timerBox);
			}
			else if(i==3)
			{
				this.scoreBox = new java.awt.Label();
				updateScoreBox();
				box.add(this.scoreBox);
			}
			this.contentPane.add(box);
		}
		
		// variable declaration
		ArrayList<String> cards = this.gameEngine.getCards();
		Iterator<String> nav 	= cards.iterator(); // iterator to navigate the card arraylist
		String cardName; //name of the card
		cardButtons = new JButton[cards.size()]; // set array size from size of array
		int i = 0;
		
		// loop through cards and set to display
		while(nav.hasNext()) {
			cardName = nav.next();				
				
			// create card button				
			cardButtons[i] = new JButton(new ImageIcon(backImg));
			cardButtons[i].setName(cardName); // sets name of the button to the card name
			cardButtons[i].setToolTipText(Integer.toString(i));
			cardButtons[i].addMouseListener(this);
				
			// add button to display
			this.contentPane.add(cardButtons[i]);
			i++;						
		}
	}
	
	/**
	 * sets the main JFrame
	 */
	private void setJFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // on clicking exit, close the program
		setBounds(100, 100, 450, 600);
	}
	
	/**
	 * Sets the contentPane
	 */
	private void setContentPane()
	{
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setMinimumSize(new Dimension(450,600));
	}
	
	/**
	 * Sets the layout for the contentpane
	 */
	private void setLayout()
	{
		GridLayout mainBox = new GridLayout(5, 1, 5, 5);
		this.contentPane.setLayout(mainBox);	
	}
	
	/**
	 * Sets the timer for gameplay
	 */
	private void setTimer()
	{
		gameTime = new Timer();
		gameTime.scheduleAtFixedRate(iterateTime(), 1000, 1000);
	}
	
	/**
	 * Return the task that the timer will perform at each iteration
	 * @return TimerTask task to perform
	 */
	private TimerTask iterateTime()
	{			
		return new TimerTask() {
			
			/**
			 * Main running task
			 */
			@Override
			public void run() {
				int time = Integer.parseInt(timerBox.getText()); // get the time from the time box
				
				// decrement the time and set the time
				time--;
				timerBox.setText(Integer.toString(time));	
				
				// check if the game is over
				gameEnd = checkEnd();	
				
				if(gameEnd)
				{
					gameFinish();
				}
			}
		};		
	}
	
	/**
	 * Refreshes the score to the textbox
	 */
	private void updateScoreBox()
	{
		this.scoreBox.setText(Integer.toString(this.gameEngine.getScore()));
	}

	/**
	 * Clear out the array of clicked cards
	 */
	private void clearClickedCards()
	{
		clickedCards = new JButton[2];
	}
	
	/**
	 * Custom pause feature to avoid thread halting
	 * @param millis time to pause in milliseconds
	 */
	private void pause(int millis)
	{
		 Date start = new Date();
		 Date end = new Date();
		 while(end.getTime() - start.getTime() < millis)
		 {
			 end = new Date();
		 }
	}

	private boolean checkEnd()
	{		
		int time = Integer.parseInt(timerBox.getText());
		
		// Disable cards if player is out of time, otherwise check to see if player has finished the game
		if(time == 0)
		{
			for(JButton card:cardButtons)
			{
				card.setEnabled(false);
			}
			gameTime.cancel(); // stops the timer
			return true;
		}
		// Check if any cards are enabled
		for(JButton card : cardButtons)
		{
			if(card.isEnabled())
			{
				return false;
			}
		}
		
		return true;
	}
	
	private void gameFinish()
	{
		gameTime.cancel();
		gameEngine.addScore(Integer.parseInt(timerBox.getText()));
		updateScoreBox();
		messageBox.setText("Game Over!");
		displayHighscore();
	}

	private void displayHighscore()
	{
		try {
			HighScores highScores = new HighScores(this.gameEngine);
			highScores.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * On mouse click flip the card, if it is the second card, use the game engine to check if it is a match and update 
	 * messages, and score
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if( e.getButton() == 1)
		{
			Component item = e.getComponent();
			
			try{
				JButton button = JButton.class.cast(item);
				
				if(button.isEnabled() && !button.equals(clickedCards[0]))
				{
					// check if button is the new game button
					if(button.getName() != this.newGameButton.getName())
					{		
						Image img = ImageIO.read(getClass().getResource("resources/Card Images/" + button.getName() + ".png"));
						
						// ensure image was set successfully
						if(img != null)
						{
							cardButtons[Integer.parseInt(button.getToolTipText())].setIcon(new ImageIcon(img));
							
							// get message whether cards were a match or not
							String message = gameEngine.checkMatch(Integer.parseInt(button.getToolTipText()));
							messageBox.setText(message);
							
							// store the clicked card
							if(clickedCards[0] == null)
							{
								clickedCards[0] = button;
							}
							else
							{
								clickedCards[1] = button;
							}
							
							// check message to see if the player won or not
							if(message == "Right!")
							{
								for(JButton card : clickedCards)
								{
									cardButtons[Integer.parseInt(card.getToolTipText())].setEnabled(false);
								}
								clearClickedCards();								
							}
							else if(message == "Wrong!")
							{
								resetCards = true;	// set card flipping flag to on							
							}							
						}
						else
						{
							System.out.print("Image not found for "+ button.getName());
						}
						updateScoreBox(); // updates the score message
					}
					else
					{
						restart(); // restart the game
					}
				}
				this.contentPane.repaint();
			}
			catch(ClassCastException ex)
			{
				System.out.print("Button was not caste");
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}  
			catch (NumberFormatException e1) 
			{
				e1.printStackTrace();
			} 
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(resetCards)
		{
			// Stop game for 1 second to display clicked cards before resuming timer and flipping cards back over
			gameTime.cancel();
			pause(1000);
			setTimer();
			
			// set the two cards that were selected back to card back image
			for(JButton card : clickedCards)
			{
				cardButtons[Integer.parseInt(card.getToolTipText())].setIcon(new ImageIcon(this.backImg));
			}
			
			// wipe the cards that have been selected
			clearClickedCards();
			
			// set reset flag back to false
			resetCards = false;
		}
		updateScoreBox();
		this.gameEnd = checkEnd();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
