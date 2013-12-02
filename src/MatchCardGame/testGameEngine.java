package MatchCardGame;

public class testGameEngine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CardGameEngine cge = new CardGameEngine();
		System.out.println(cge.getHighScores());
		System.out.println(cge.getHighScoreName());
		System.out.println(cge.getCards());
		System.out.println(cge.checkMatch(1));
		System.out.println(cge.checkMatch(5));
	}

}
