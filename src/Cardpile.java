import java.util.LinkedList;
import java.util.List;

public class Cardpile {
	private List<Card> cards; // front of the list is the top of the pile
	
	// Constructor, sets the name of the pile and initializes the pile to be empty
	public Cardpile() {
		cards = new LinkedList<>();
	}
	
	public Cardpile(Cardpile o) {
		cards = new LinkedList<>(o.cards);
	}
	
	public int size() {
		return cards.size();
	}

	public List<Card> getCards() {
		return cards;
	}
	
	// Returns the specified number of cards from the pile. The first element of the returned list is the first card drawn.
	// If the pile contains less cards than requested, all the cards in the pile will be returned
	List<Card> takeCards(int amount) {
		List<Card> taken = new LinkedList<>();
		
		if(amount > cards.size()) { // if there are not enough cards in the pile
			taken.addAll(cards); // return all cards
			cards.clear(); // pile is emptied
		}
		else { // otherwise if there are enough cards
			for(int i = 0; i < amount; i++) {
				taken.add(cards.remove(0)); // return one card at a time, each drawn card is added to the end of the returned list
			}
		}
		
		return taken;
	}
	
	void addCardsOnTop(List<Card> cards) {
		cards.addAll(0, cards); // adds cards to the front of the list
	}
	
	void addCardsOnBot(List<Card> cards) {
		cards.addAll(cards); // adds cards to the end of the list
	}

	void addCardAtIndex(Card card, int position) {
		cards.add(position, card);
	}

	//shuffles the card pile, emulating how a physical deck of cards is shuffled
	void shuffle() {
		for(int i = 0;i<7;i++) {//repeats the shuffling mechanic several times to ensure the cards are well shuffled
			//LinkedList<Card> half1 = (LinkedList<Card>) cards.subList(0,numOfCards/2); //Can't go up like this, changed to line below
			List<Card> half1 = new LinkedList<>(cards.subList(0, size() / 2));//split the deck in half
			List<Card> half2 = new LinkedList<>(cards.subList(size() / 2, cards.size()));
			cards.clear();
			
			while(!half1.isEmpty() && !half2.isEmpty()) {//while there are cards in both halfs
				if(Math.random() < 0.5) {//50% chance to chose half1 or half2
					cards.add(half1.remove(0)); // takes the card from the first half and adds it back into the pile
				}
				else{
					cards.add(half2.remove(0)); // takes the card from the second half and adds it back into the pile
				}
			}
			cards.addAll(half1); // adds remaining cards
			cards.addAll(half2); // one of these halves will be empty
		}
	}
	
	@Override
	public String toString() {
		if (size() == 0)
			return "";
		
		final StringBuilder ret = new StringBuilder();
		{ // handle the 1st card separately so there is no trailing newline
			Card card = cards.get(0);
			ret.append(card.getVal()).append(" ").append(card.getCategory());
		}
		for (int i = 1; i < size(); i++) {	// for each card in the pile, print its information
			ret.append("\n");
			Card card = cards.get(i);
			ret.append(card.getVal()).append(" ").append(card.getCategory());
		}
		return ret.toString();
	}
}