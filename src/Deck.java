/*
	Programmer: Tyler Heald
	Date: 3/30/2019
	Description:
	The Deck class contains the cards needed for playing a game.
	When the Deck object is initialized with certain parameters from an external
	file, it creates cards with the proper values. The only thing the deck can do
	is return cards, remove cards, and shuffle itself.
	
	File cardList:
	The file that contains cards to make for the deck is in the format:
	#ncards
	value1 category1
	value2 category2
	...
	valuen categoryn
	
	METHODS:
	shuffle()
		Shuffles the deck using Collections.shuffle()
	getCardAt(int i)
		Gets the card at a given index
	getNumOfCards()
		Gets the number of cards in the deck
	
	TODO:
	Add customization options to reading the cardList for easier processing.
	Example: C as the header so the program can generate cards with values in
	(values X category). Would make it easier to write a file for decks like the
	typical 52 card deck.
*/
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Deck {
	private List<Card> cards = new ArrayList<Card>();
	
	/**** CONSTRUCTORS ****/
	public Deck(File cardList) {
		try (Scanner input = new Scanner(cardList)) {
			while(input.hasNextLine()) {
				final String line = input.nextLine();
				if (line == null || line.isEmpty())
					continue;
				cards.add(new Card(input.nextLine()));
			}
		} catch (IOException e) {
			// file not found, output error and exit
			System.out.println("cardList file not found!");
			System.exit(1);
		}	
	}
	
	/**** FUNCTIONS ****/
	void shuffle() {
		Collections.shuffle(cards);
	}
	
	/**** GETTERS/SETTERS ****/
	public Card getCardAt(int i) {
		return cards.get(i);
	}
	
	public int size() {
		return cards.size();
	}

	public boolean exists(String s) {
		s = s.toUpperCase();
		if(s.equals("WILD") || s.equals("0W"))
			return true;
		System.out.println(s);
		String[] colors = {"R","G","B","Y"};
		for(String color:colors) {
			for(int i=1;i<=12;i++) {
				if(s.equals(color+i)||s.equals(i+color))
					return true;
			}
		}
		return false;
	}
}