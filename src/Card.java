/*
	Programmer: Tyler Heald
	Date: 3/30/2019
	Description:
	The Card class is meant only to hold the value and category of a Card(number and suite).
	It contains that data, ways to access it, and a method to print its data
	
	METHODS:
	printCard()
		Prints the cards data in the format "value category"
*/

public class Card {
	// DATA FIELDS
	String value;
	String category;

	/**** CONSTRUCTORS ****/
	public Card(String v, String c) {
		value = v.trim();
		category = c.trim();
	}

	public Card(String card) 
	{
		int delimiter = card.indexOf(" ");
		if(delimiter>=0) {
			value = card.substring(0, delimiter);
			category = card.substring(delimiter+1, card.length());
		}else {
			value = card.substring(0, 1);
			category = card.substring(1, 2);
		}
	}

	/**** FUNCTIONS ****/
	// Method to print card stats
	public void printCard() {
		System.out.println(value + " " + category);
	}

	/**** GETTERS/SETTERS ****/
	void setVal(String v) {
		value = v;
	}

	String getVal() {
		return value;
	}

	void setCategory(String c) {
		category = c;
	}

	String getCategory() {
		return category;
	}

	String getCardInfo() {
		return "" + value + category;
	}
}