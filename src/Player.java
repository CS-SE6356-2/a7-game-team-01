/* @author Jacob */

import java.util.LinkedList;
import java.util.List;
import java.net.Socket;

/* Represents one of the people playing the game */
public class Player
{
/* Data */
	/* Name of the team that the player belongs to */
	private String teamName;

	/* Identifier marking the role the player has in the game */
	private String role;

	/* Cards the player possesses (both active and inactive) */
	private Hand hand;
	
	//Socket that the player is connected on
	private Socket playerSock;

	// Phase of the current player for games like Phase 10, which players can be on different Phases
	private int phase;

	private boolean hasCompletedPhaseReq;

	/* PUBLIC METHODS */
	
	/* Constructor */
	public Player(String cTeamName, String cRole, Socket cSock)
	{
		this.teamName = cTeamName;
		this.role = cRole;
		this.hand = new Hand();
		this.playerSock = cSock;
		this.phase = 1;
		hasCompletedPhaseReq = false;
	}

	/* Adds all the cards in the list to the player's active cards */
	public void addCards(List<Card> cards) {
		hand.addCards(cards);
	}

	/* Removes all the cards in the list from the player's active cards
	 * and returns a list of all cards successfully removed */
	public LinkedList<Card> removeCards(List<Card> cards) {
		return hand.removeCards(cards);
	}
	
	/**
	 * Returns the number of cards this player has
	 * @author Chris
	 * @return
	 */
	public int getNumOfCards() { return hand.size(); }
	/**
	 * Returns this player's role
	 * @author Chris
	 * @return
	 */
	public void assignRole(String newRole) {
		this.role = newRole;
	}
	
	public Hand getHand() { return hand; }
	public String getRole() { return role; }
	public String getTeamName() { return teamName; }
	public Socket getSock() { return playerSock; }
	
	public void metPhase() {
		hasCompletedPhaseReq = true;
	}
	public void resetPhase() {
		hasCompletedPhaseReq = false;
	}
	public int getPhase() {
		return phase;
	}
	public boolean hasPhaseBeenMet() {
		return hasCompletedPhaseReq;
	}
	
	public int getID() {
		return phase;
	}

	/* Transfers all the cards in the list from the player's active cards
	 * to their inactive cards and returns a list of all cards successfully
	 * transferred */
	public LinkedList<Card> transferActiveToInactive(LinkedList<Card> cards)
	{
		return this.hand.transferActiveToInactive(cards);
	}

	/* Transfers all the cards in the list from the player's inactive cards
	 * to their active cards and returns a list of all cards successfully
	 * transferred */
	public LinkedList<Card> transferInactiveToActive(LinkedList<Card> cards)
	{
		return this.hand.transferInactiveToActive(cards);
	}

	/* Used to perform game-specific actions that go beyond
	 * manipulating one's cards. Returns result of action as a String */
	public String takeAction(String action)
	{
		String result = "";
		return result;
		/* TODO */
	}

/* Getters */
	public List<Card> getActiveCards() {
		return hand.getActiveCards();
	}

	public List<Card> getInactiveCards() {
		return hand.getInactiveCards();
	}
	
	/**
	 * The players card list uses 3 delimiters
	 *	The ';' delimits the active list form the inactive list. ActiveCards|InactiveCards
	 *	The ',' delimits the cards in a list from each other. Card1;Card2;Card3
	 *	The ' ' delimits the specifics of a card. CardValue CardCategory
	 * @author Chris
	 * @return
	 */
	public String getCardListForUTF() {
		StringBuilder cardList = new StringBuilder();
		
		if (getActiveCards().size() > 0) {
			for(Card card: getActiveCards())
				cardList.append(card.getVal()+""+card.getCategory()+",");
			cardList.setCharAt(cardList.lastIndexOf(","), ';');
		} else
			cardList.append(" ;");
		
		if (getInactiveCards().size() > 0) {
			for(Card card: getInactiveCards())
				cardList.append(card.getVal()+" "+card.getCategory()+",");
			cardList.deleteCharAt(cardList.lastIndexOf(","));
		} else
			cardList.append(' ');
		
		return cardList.toString();
	}
}
