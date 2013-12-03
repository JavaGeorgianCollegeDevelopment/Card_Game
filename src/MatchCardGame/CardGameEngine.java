package MatchCardGame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 *Handles back end of the card game programming.
 * @author Kevin Kan
 *@version 1.3 
 *@since December 3 2013
 */
public class CardGameEngine {
	//var
	private static final int CARDS_IN_DECK = 52;
	private String[] fullCardList= new String[CARDS_IN_DECK];
	private ArrayList<Integer> highScores=new ArrayList<Integer>();
	private ArrayList<String> selectedCards=new ArrayList<String>(),highScoreName= new ArrayList<String>();
	private String checkCard="";
	private int score;
	final int NUMBER_OF_PAIRS = 8;
	final int NUMBER_OF_HIGHSCORES=5;

	/**
	 * default constructor that sets the full card list values and resest the score to zero
	 */
	public CardGameEngine(){//add all possible card names
		for (int i=0; i<13; i++){
			fullCardList[i]="card_"+(i+1)+"s";
			fullCardList[i+13]="card_"+(i+1)+"c";
			fullCardList[i+26]="card_"+(i+1)+"d";
			fullCardList[i+39]="card_"+(i+1)+"h";
			/*// debug full card list.
			System.out.println(fullCardList[i]);
			System.out.println(fullCardList[i+13]);
			System.out.println(fullCardList[i+26]);
			System.out.println(fullCardList[i+39]);*/
		}
		score=0; 
	}
	private void readHighScores(){
		try{
			FileInputStream file = new FileInputStream(new File(getClass().getResource("resources/highScores/highScores.xml").getPath()));
		    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder =  builderFactory.newDocumentBuilder();
		    Document xmlDocument = builder.parse(file);
		    XPath xPath =  XPathFactory.newInstance().newXPath();
		    for(int t=1;t<6;t++){
		    	String expressionNames = "/highScores/scores[@place='"+t+"']/name";
		    	String expressionScore = "/highScores/scores[@place='"+t+"']/score";
		    	try{
		    		String scoreNameTemp=xPath.compile(expressionNames).evaluate(xmlDocument);
		    		String scoreTemp;
		    		scoreTemp=(xPath.compile(expressionScore).evaluate(xmlDocument));
		    		if((scoreTemp=="")||(scoreNameTemp=="")){
		    			/*System.out.println("test name temp"+scoreNameTemp);
		    			System.out.println("test score temp "+scoreTemp);
		    			highScores.add(0);
			    		highScoreName.add("AAA");*/
		    		}
		    		else{
		    			highScores.add(Integer.parseInt(scoreTemp));
			    		highScoreName.add(scoreNameTemp);
		    		}
		    		}
		    	catch(NumberFormatException e){
		    		System.out.println(e.getMessage());
		    	}
		    	catch(NullPointerException e){
		    		System.out.println(e.getMessage());
		    	}
		    }
		if(file!=null){file.close();}
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
	    }
		catch (SAXException e) {
			System.out.println(e.getMessage());
	    } 
		catch (IOException e) {
			System.out.println(e.getMessage());
	    } 
		catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
	    } 
		catch (XPathExpressionException e) {
			System.out.println(e.getMessage());
		} 
	}
	/**
	 * reset's score to zero and picks new cards
	 * @return  ArrayList<String> selectedCards is the array list that contains all of the cards that were selected for this game's file name.
	 * returns an arraylist size 16 that has been shuffled and made up of 8 pairs.
	 */
	public ArrayList<String> getCards(){// function getCards (), return string array with picture names. formmated by card_#suit
		score=0;
		long seed = System.nanoTime()*15215/6512;//get system time to use as a seed for random
		selectedCards.clear();//clear previous set of selected cards.
		String cardName;
		Collections.shuffle(Arrays.asList(fullCardList),new Random (seed));
		Collections.shuffle(Arrays.asList(fullCardList),new Random (seed*5));
		Collections.shuffle(Arrays.asList(fullCardList),new Random (seed*6));
		for (int j=0; j<NUMBER_OF_PAIRS; j++){
			cardName= fullCardList[j];
			selectedCards.add(cardName);
			selectedCards.add(cardName);
		}
		seed = System.nanoTime()*42/3;//re-shuffle seed
		Collections.shuffle(selectedCards,new Random (seed));//scramble the selectedCards array randomly.
		Collections.shuffle(selectedCards,new Random (seed+90));//scramble the selectedCards array randomly.
		Collections.shuffle(selectedCards,new Random (seed/100+60));//scramble the selectedCards array randomly.
		return selectedCards;
	}
	
	/**takes the first card value and saves it. Then checks the second card if they match. 
	 * @param checkSelcetedCardArrayIndex is the integer index of the array
	 * @return string message that ranges from empy in case of first card selected, Right or wrong depending if the card files matches.
	 */
	public String checkMatch(int checkSelcetedCardArrayIndex){//TODO fuction checkMatch(int arraylocation),check if cards if first card is selected and second matches return string message value. 
		if(checkCard==""){
			checkCard=selectedCards.get(checkSelcetedCardArrayIndex);
			return"";
		}
		else{ 
			String message;
			if (checkCard.equals(selectedCards.get(checkSelcetedCardArrayIndex))){
				addScore(1);
				message="Right!";
			}
			else{
				message="Wrong!";
			}
			checkCard="";
		return message;
		}
	}
	/**add on score value
	 * @param addValue
	 */
	public void addScore(int addValue){
		score = score+addValue;
	}
	/**returns the score to the user
	 * @return int score
	 */
	public int getScore(){//TODO function getScore (),return int score
		return score;
	}
	/**This function checks to see if the user has toped the high score
	 * @return y which is the index of highscores. 0 being first place and 4 being fifth place. Returns 5 if did not make it to highScores.
	 * also automatically assigns associated score name with score as "AAA"
	 */
	public int checkHighScore(){//TODO create a function checkHighScore (), returns int 0 - 5 where 5 is not in top five.
		this.readHighScores();
		for(int y=0; y<highScores.size()&&y<NUMBER_OF_HIGHSCORES; y++){
			if(this.getScore()>highScores.get(y)){
				//replace the old score with new one and bump every on else down one
				highScores.add(y,this.getScore());
				highScoreName.add(y,"AAA");
				return y;
			}
		}
		if(highScores.size()<NUMBER_OF_HIGHSCORES){//if there is less than 5 high scores and did not beat others, add to end
			highScores.add(this.getScore());
			highScoreName.add("AAA");
			System.out.println("test scores");
			return highScores.size();//returns last position of arraylist
		}
		return NUMBER_OF_HIGHSCORES;//failing that not better than any other score return 5
	}
	
	/** Changes the highscore's name based on the place they ranked.
	 * @param place integer that denotes where they ranked. 
	 * @param newName string of new name they wish to change the score to.
	 */
	public void changeInitials(int place, String newName){//TODO create a function changeInitials (int arraylocation, string newName)
		if((place< highScoreName.size())&&(place>=0)){
			highScoreName.set(place,newName);
			this.saveScore();
		}
	}
	
	/**Gets arraylist of highScorers Names
	 * @return highScoresName ArrayList<String>
	 */
	public ArrayList<String> getHighScoreName(){
		return highScoreName;
	}
	/**Gets array list of highScore scores
	 * @return highScores ArrayList<Integer>
	 */
	public ArrayList<Integer> getHighScores(){
		return highScores;
	}
	
	/** This function saves the score for updates and changes.
	 * 
	 */
	public void saveScore(){
		XMLOutputFactory xof =  XMLOutputFactory.newInstance();
		XMLStreamWriter xtw=null;
		try {
			FileOutputStream outFile=new FileOutputStream(new File(getClass().getResource("resources/highScores/highScores.xml").getPath()),false);
			//FileOutputStream outFile=new FileOutputStream("test.xml",false);
			xtw = xof.createXMLStreamWriter(outFile,"UTF-8");
			xtw.writeStartDocument("UTF-8", "1.0"); 
			xtw.writeStartElement("highScores");
			//System.out.println("xml outfile test");
				for(int x=0; (x<highScores.size())&&(x<NUMBER_OF_HIGHSCORES); x++){
					//System.out.println("xml start record"+x);
					xtw.writeStartElement("scores");
					//System.out.println("xml start scores "+x);
					xtw.writeAttribute( "place",String.valueOf((x+1)));
						xtw.writeStartElement("name");
						//System.out.println("xml name record"+x);
							xtw.writeCharacters(highScoreName.get(x).toString());
						xtw.writeEndElement();
						xtw.writeStartElement("score");
							xtw.writeCharacters(highScores.get(x).toString());
						xtw.writeEndElement();
					xtw.writeEndElement();
					//System.out.println("xml end record"+x);
				}
			xtw.writeEndElement();
			xtw.writeEndDocument();
			//System.out.println("end of try");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		finally
		{  //System.out.println("start finally");
			if (xtw != null)
			{
				try
				{
					//System.out.println("xtw if not null");
					xtw.flush();
					xtw.close();
				}
			catch (XMLStreamException e)
		    	{
				e.printStackTrace();
		    	}
			}
		}
	}
}
