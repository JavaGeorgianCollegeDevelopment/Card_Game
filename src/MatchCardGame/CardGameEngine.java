package MatchCardGame;
import java.util.ArrayList;
import java.util.Random;
/**
 * 
 * @author Kevin Kan
 *@version 1 
 *@since November 19 2013
 *Handles back end of the card game programming.
 */
public class CardGameEngine {
	//var
	private static final int CARDS_IN_DECK = 52;
	private String[] fullCardList= new String[CARDS_IN_DECK];
	private ArrayList<String> selectedCards;
	private String checkCard="";
	private int score;
	//defualt constructor
	CardGameEngine(){//add all possible card names
		for (int i=1; i<14; i++){
			fullCardList[i]="card_"+i+"s";
			fullCardList[i]="card_"+i+"c";
			fullCardList[i]="card_"+i+"d";
			fullCardList[i]="card_"+i+"h";
		}
		score=0;
	}
	public ArrayList<String> getCards(){// function getCards (), return string array with picture names. formmated by card_#suit
		final int NUMBER_OF_CARDS = 8;
		score=0;
		selectedCards.clear();//clear previous set of selected cards.
		String cardName;
		Random randomIndex = new Random();
		for (int j=0; j<NUMBER_OF_CARDS; j++){
			cardName= fullCardList[randomIndex.nextInt(CARDS_IN_DECK-1)];
			selectedCards.add(cardName);
		}
		return selectedCards;
	}
	public String checkMatch(int checkSelcetedCardArrayIndex){//TODO fuction checkMatch(int arraylocation),check if cards if first card is selected and second matches return string message value. 
		if(checkCard==""){
			checkCard=selectedCards.get(checkSelcetedCardArrayIndex);
			return"";
		}
		else{ String message;
			if (checkCard==selectedCards.get(checkSelcetedCardArrayIndex)){
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
	private void addScore(int addValue){
		score = score+addValue;
	}
	public int getScore(){//TODO function getScore (),return int score
		return score;
	}
	public int checkHighScore(){//TODO create a function checkHighScore (), returns int 0 - 5 where 5 is not in top five.
		return 0;
	}
	
	//TODO create a function changeInitials (int arraylocation, string newName)
}
