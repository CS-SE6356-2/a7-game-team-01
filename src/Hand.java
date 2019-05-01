
/* @author Jacob  */

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/* Represents the cards in a specific Player's possession. */
public class Hand {
	/* Data */
	/* Number of active and inactive cards in the hand */
	int numOfCards;

	/* All the cards that are able to be played */
	private LinkedList<Card> activeCards;

	/* Cards the hand owns but cannot use (e.g. matched cards) */
	private LinkedList<Card> inactiveCards;

	/* Public methods */

	/* Constructor */
	public Hand()
	{
		this.activeCards = new LinkedList<Card>();
		this.inactiveCards = new LinkedList<Card>();
	}

	/*
	 * Looks at the activeCards for matches and returns all unique pairs of matching
	 * cards. Games requiring a more sophisticated matching function would need to
	 * override this function
	 */
	public LinkedList<Card> checkMatches() {
		LinkedList<Card> matchingCards = new LinkedList<Card>();

		for (int card1Index = 0; card1Index < this.activeCards.size(); ++card1Index) {
			for (int card2Index = card1Index + 1; card2Index < this.activeCards.size(); ++card2Index) {
				Card card1 = this.activeCards.get(card1Index);
				Card card2 = this.activeCards.get(card2Index);

				if (card1.equals(card2))
				{
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

	/*
	 * Removes all the cards in the list from the active cards, returning a list of
	 * all cards successfully removed
	 */
	public LinkedList<Card> removeCards(List<Card> list) {
		LinkedList<Card> removedCards = new LinkedList<Card>();
		for (int index = 0; index < list.size(); ++index) {
			for (int j = 0; j < activeCards.size(); j++) {
				if (list.get(index).getCardInfo().equals(activeCards.get(j).getCardInfo())) {
					removedCards.add(activeCards.remove(j));
					break;
				}
			}
		}
		return removedCards;
	}

	/*
	 * Transfers all the cards in the list from active cards to inactive cards and
	 * returns a list of all cards successfully transferred
	 */
	public LinkedList<Card> transferActiveToInactive(LinkedList<Card> cards) {
		LinkedList<Card> transferredCards = new LinkedList<Card>();
		for (int index = 0; index < cards.size(); ++index) {
			Card cardToTransfer = cards.get(index);
			if (this.activeCards.remove(cardToTransfer)) {
				this.inactiveCards.add(cardToTransfer);
				transferredCards.add(cardToTransfer);
			}
		}
		return transferredCards;
	}

	/*
	 * Transfers all the cards in the list from inactive cards to active cards and
	 * returns a list of all cards successfully transferred
	 */
	public LinkedList<Card> transferInactiveToActive(LinkedList<Card> cards) {
		LinkedList<Card> transferredCards = new LinkedList<Card>();
		for (int index = 0; index < cards.size(); ++index) {
			Card cardToTransfer = cards.get(index);
			if (this.inactiveCards.remove(cardToTransfer)) {
				this.activeCards.add(cardToTransfer);
				transferredCards.add(cardToTransfer);
			}
		}
		return transferredCards;
	}

/* Getters */
	public LinkedList<Card> getActiveCards() {
		return activeCards;
	}
	
	public LinkedList<Card> getInactiveCards() {
		return inactiveCards;
	}
	
	//These both are used for the hand used in the ClientGUI
	//Both make shallow copies of the lists
	public void setActiveCards(List<Card> activeCards) {
		activeCards = new LinkedList<Card>(activeCards);
	}
	
	public void setInactiveCards(List<Card> inactiveCards) {
		inactiveCards = new LinkedList<Card>(inactiveCards);
	}

	public int size() {
		return activeCards.size() + inactiveCards.size();
	}

	public boolean inHand(String cardName) {
		String checker = (cardName.equals("WILD")) ? "0W" : cardName;
		for (Card c : activeCards) {
			if (c.getCardInfo().equals(checker)
					|| c.getCardInfo().equals(checker.replaceAll("[0-9]", "") + checker.replaceAll("[^0-9]", ""))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Hand))
			return false;
		Hand other = (Hand) obj;
		return Objects.equals(activeCards, other.activeCards) && Objects.equals(inactiveCards, other.inactiveCards);
	}

	@Override
	public int hashCode() {
		return Objects.hash(activeCards, inactiveCards);
	}
}
