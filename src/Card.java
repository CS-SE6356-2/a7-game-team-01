import java.util.Objects;

/*
	Programmer: Tyler Heald
	Date: 3/30/2019
	Description:
	The Card class is meant only to hold the value and category of a Card(number and suite).
	It contains that data, ways to access it, and a method to print its data
*/
public class Card {
	private final String value;
	private final String category;
	
	public Card(String v, String c) {
		if (v == null || c == null)
			throw new IllegalArgumentException();
		value = v;
		category = c;
	}
	
	/**
	 * Constructor.
	 * @param str A string containing the value and the category, separated by a space. Matches the format of toString()
	 */
	public Card(String str) {
		if (str == null)
			throw new IllegalArgumentException();
		String[] split = str.trim().split("\\s+", 1);
		if (split.length != 2)
			throw new IllegalArgumentException();
		value = split[0];
		category = split[1];
	}
	
	/****	GETTERS/SETTERS	****/
	String getVal() {
		return value;
	}
	String getCategory() {
		return category;
	}
	
	/****	FUNCTIONS	****/
	public String toString() {
		return value + " " + category;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		return Objects.equals(category, other.category) && Objects.equals(value, other.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(category, value);
	}	
}