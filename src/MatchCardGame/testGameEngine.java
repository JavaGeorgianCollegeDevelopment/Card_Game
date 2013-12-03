package MatchCardGame;

/**
 *
 *Handles game engine testing.
 *@author Kevin Kan
 *@version 1.3 
 *@since December 3 2013
 **/
public class testGameEngine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CardGameEngine cge = new CardGameEngine();
		System.out.println(cge.getCards());
		System.out.println(cge.checkMatch(1));
		System.out.println(cge.checkMatch(5));
		//cge.changeInitials(3,"KK");
		cge.addScore(6);
		cge.checkHighScore();
		System.out.println(cge.getHighScores());
		System.out.println(cge.getHighScoreName());
		//cge.saveScore();
	}

}
