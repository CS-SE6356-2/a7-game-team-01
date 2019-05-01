import java.util.LinkedList;
import java.util.List;

public class Cardpile {

	int numOfCards;
	String pileName;
	LinkedList<Card> cards;//front of the list is the top of the pile
	
	//default constructor
	public Cardpile() {
		this("Pile");
	}
	//Constructor, sets the name of the pile and initializes the pile to be empty
	public Cardpile(String name) {
		pileName = name;
		numOfCards = 0;
		cards = new LinkedList<Card>();
	}
	public Cardpile clone() {
		return new Cardpile(this.numOfCards,this.pileName,this.cards);
	}
	@SuppressWarnings("unchecked")
	private Cardpile(int numOfCards,String pileName,LinkedList<Card> cards) {
		this.numOfCards = numOfCards;
		this.pileName = pileName;
		this.cards = (LinkedList<Card>) cards.clone();
	}
	public int size() {
		return cards.size();
	}
	public LinkedList<Card> getCards(){
		return cards;
	}
	//Returns the specified number of cards from the pile. The first element of the returned list is the first card drawn.
	//If the pile contains less cards than requested, all the cards in the pile will be returned
	List<Card> takeCards(int amount){
		LinkedList<Card> taken = new LinkedList<Card>();
		
		if(amount > numOfCards) {//if there are not enough cards in the pile
			taken.addAll(cards);//return all cards
			cards.clear();//pile is emptied
			numOfCards = 0;
		}
		else {//otherwise if there are enough cards
			for(int i = 0;i < amount; i++) {
				taken.add(cards.removeFirst());//return one card at a time, each drawn card is added to the end of the returned list
			}
		numOfCards -= amount;//update the number of cards left in the pile;
		}
		
		return taken;
	}
	
	void addCardsOnTop(List<Card> cards) {
		this.cards.addAll(0, cards);//adds cards to the front of the list
		numOfCards += cards.size();//updates the number of cards in the pile
	}
	
	void addCardsOnBot(List<Card> cards) {
		this.cards.addAll(cards);//adds cards to the end of the list
		numOfCards += cards.size();//updates the number of cards in the pile
	}

	void addCardAtIndex(Card card, int position) {
		this.cards.add(position,card);
		numOfCards += 1;
	}
	//shuffles the card pile, emulating how a physical deck of cards is shuffled
	void shuffle() {
		for(int i = 0;i<7;i++) {//repeats the shuffling mechanic several times to ensure the cards are well shuffled
			//LinkedList<Card> half1 = (LinkedList<Card>) cards.subList(0,numOfCards/2); //Can't go up like this, changed to line below
			LinkedList<Card> half1 = new LinkedList<Card>(cards.subList(0,numOfCards/2));//split the deck in half
			LinkedList<Card> half2 = new LinkedList<Card>(cards.subList(numOfCards/2,cards.size()));
			cards.clear();
			
			while(!half1.isEmpty() && !half2.isEmpty()) {//while there are cards in both halfs
				if(Math.random() < 0.5) {//50% chance to chose half1 or half2
					cards.add(half1.removeFirst());//takes the card from the first half and adds it back into the pile
				}
				else{
					cards.add(half2.removeFirst());//takes the card from the second half and adds it back into the pile
				}
			}
			cards.addAll(half1);//adds remaining cards
			cards.addAll(half2);//one of these halves will be empty
		}
	}
	public String toString() {
		String s = "";
		for(Card c : cards) {
			s+=c.getCardInfo()+"  ";
		}
		return s;
	}
	
}
