/* @author Jacob */

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/* Represents the cards in a specific Player's possession. */
public class Hand {
	/* DATA */

	/* All the cards that are able to be played */
	private List<Card> activeCards;

	/* Cards the hand owns but cannot use (e.g. matched cards) */
	private List<Card> inactiveCards;

	/* PUBLIC METHODS */

	/* Constructor */
	public Hand() {
		this.activeCards = new LinkedList<>();
		this.inactiveCards = new LinkedList<>();
	}

	/* Looks at the activeCards for matches and returns all unique pairs
	 * of matching cards. Games requiring a more sophisticated 
	 * matching function would need to override this function */
	public LinkedList<Card> checkMatches() {
		LinkedList<Card> matchingCards = new LinkedList<>();

		for (int card1Index = 0; card1Index < this.activeCards.size(); ++card1Index) {
			for (int card2Index = card1Index + 1; card2Index < this.activeCards.size(); ++card2Index) {
				Card card1 = this.activeCards.get(card1Index);
				Card card2 = this.activeCards.get(card2Index);

				if (card1.equals(card2)) {
					matchingCards.add(card1);
					matchingCards.add(card2);
				}
			}
		}
		return matchingCards;
	}

	/* Adds all the cards in the list to the active cards */
	public void addCards(List<Card> cards) {
		for (int index = 0; index < cards.size(); ++index) {
			Card cardToAdd = cards.get(index);
			this.activeCards.add(cardToAdd);
		}
	}

	/* Removes all the cards in the list from the active cards,
	 * returning a list of all cards successfully removed */
	public List<Card> removeCards(List<Card> cards) {
		List<Card> removedCards = new LinkedList<>();
		for (int index = 0; index < cards.size(); ++index) {
			Card cardToRemove = cards.get(index);
			if (this.activeCards.remove(cardToRemove)) {
				removedCards.add(cardToRemove);
			}
		}
		return removedCards;
	}

	/* Transfers all the cards in the list from active cards to inactive cards 
	 * and returns a list of all cards successfully transferred */
	public List<Card> transferActiveToInactive(List<Card> cards) {
		List<Card> transferredCards = new LinkedList<>();
		for (int index = 0; index < cards.size(); ++index) {
			Card cardToTransfer = cards.get(index);
			if (activeCards.remove(cardToTransfer)) {
				inactiveCards.add(cardToTransfer);
				transferredCards.add(cardToTransfer);
			}
		}
		return transferredCards;
	}

	/* Transfers all the cards in the list from inactive cards to active cards
	 * and returns a list of all cards successfully transferred */
	public List<Card> transferInactiveToActive(List<Card> cards) {
		List<Card> transferredCards = new LinkedList<>();
		for (int index = 0; index < cards.size(); ++index) {
			Card cardToTransfer = cards.get(index);
			if (inactiveCards.remove(cardToTransfer)) {
				activeCards.add(cardToTransfer);
				transferredCards.add(cardToTransfer);
			}
		}
		return transferredCards;
	}

	/* GETTERS */
	public List<Card> getActiveCards() {
		return activeCards;
	}
	
	public List<Card> getInactiveCards() {
		return inactiveCards;
	}
	
	//These both are used for the hand used in the ClientGUI
	//Both make shallow copies of the lists
	public void setActiveCards(List<Card> activeCards) {
		activeCards = new LinkedList<>(activeCards);
	}
	public void setInactiveCards(List<Card> inactiveCards) {
		inactiveCards = new LinkedList<>(inactiveCards);
	}

	public int getNumOfCards() {
		return activeCards.size() + inactiveCards.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Hand))
			return false;
		Hand other = (Hand)obj;
		return Objects.equals(activeCards, other.activeCards) && Objects.equals(inactiveCards, other.inactiveCards);
	}

	@Override
	public int hashCode() {
		return Objects.hash(activeCards, inactiveCards);
	}
}
