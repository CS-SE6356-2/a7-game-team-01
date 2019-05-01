import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CardGame
{
	//// Member Variables ////
	private Player[] players; // Holds the data for each player
	private Deck cardDeck; // Holds the information for each card
	private List<Cardpile> piles;			
	
	//// Constructor ////
	public CardGame(int numOfPlayers, List<String> playerNames, List<Socket> clientSocks, File cardList) {
		players = new Player[numOfPlayers]; // Create a list of Players
		cardDeck = new Deck(cardList); // Create the deck of cards. The Card Game class thus has a reference to all cards
		piles = new ArrayList<>(); // Create the list of piles, will give amount that fits a specific card game
		
		// Create Card Piles
		piles.add(new Cardpile());
		piles.add(new Cardpile());
		
		//Create Players
		createPlayers(playerNames, clientSocks);
	}
	
	/**
	 * 
	 */
	void dealCards()
	{
		int currentCard = 0;
		List<Card> temp = new LinkedList<>();
		for (Player player: players) {
			for(;temp.size() < 10; currentCard++)			//Get a list of cards that will be of even size to a player. UNO starts off with players having 7 cards
				temp.add(cardDeck.cards.get(currentCard));		//add card reference to list
				//Give players their cards
			player.addCards(temp);
			temp.clear();									//Clear the list so we can give the next player their cards
		}
		
		//Give rest of cards to draw pile
		for (; currentCard < cardDeck.numOfCards; currentCard++) {
			temp.add(cardDeck.cards.get(currentCard));
		}
		piles.get(0).addCardsOnTop(temp);
		temp.clear();
		
		//Put the first card on top of the draw deck on to the used pile
		piles.get(1).addCardsOnTop(piles.get(0).takeCards(1));
	}
	public void shuffleCards() { cardDeck.shuffle(); }
	
	private void createPlayers(List<String> playerNames, List<Socket> clientSocks) {
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(playerNames.get(i),"Solo", clientSocks.get(i));
		}
	}
	
	/**
	 * Sorts the list of players initially in a game by finding the dealer, adding them and the other players into a circular linked list called playerQueue
	 * @author Chris
	 * @return playerQueue
	 */
	public PlayerQueue sortPlayersInPlayOrder() {
		//CLIENTSOCKS AND CLIENTLABELS are automatically sorted within the playerQueue as they are part of the Player object
		
		int dealerNum;	//Track the index of the dealer
		 //Index through array until dealer is found, if not then stop at end of list
		for(dealerNum = 0;dealerNum < players.length && !players[dealerNum].getRole().equals("Dealer"); dealerNum++);
		
		//Move number to next in list as dealer doesn't usually go first
		dealerNum = (dealerNum+1)%players.length;
		//Create the playerQueue
		PlayerQueue playOrder = new PlayerQueue();
		
		for(int i = 0; i < players.length; i++)							//For each player
			playOrder.enqueue(players[(dealerNum+i)%players.length]);	//Starting at the dealer, add them to the queue
		
		return playOrder;	//Return  the queue
	}
	
	/**
	 * Assigns the given player as the new dealer.
	 * @author Chris
	 * @param newDealer
	 * @return True if a new dealer has been assigned | False if not
	 */
	public boolean assignDealear(String newDealer) {
		for(Player p: players) {
			if(p.getTeamName().equals(newDealer))
			{
				p.assignRole("Dealer");
				return true;
			}
		}
		return false;
	}

	//" played BLANK Pile ### Position ###"
	//or
	//" played BLANK BLANK BLANK BLANK BLANK ( to create new pile )
	public boolean isLegalMove(Player focusPlayer, String move) {
		//Depends on game type
		move = move.replaceAll(".*played ","");
		move = move.replaceAll("WILD","0W");
		move = move.replaceAll("W0","0W");
		String[] params = move.trim().split(" ");
		if(params.length<2)
			return false;
		// placing a new card onto an existing pile
		if(focusPlayer.hasPhaseBeenMet()&&params[1].equals("Pile")&&inHand(params[0],focusPlayer.getHand())) {
			int pile, position;
			try {
				pile = Integer.parseInt(params[2]);
				position = Integer.parseInt(params[4]);
			}catch (Exception e) {
				return false;
			}
			// We have a valid pile, position, and card   so we just need to check the validity of the move
			return isValidPlacement(params[0],pile,position);
		// creating a new pile
		} else {
			for(int i = 0; i < params.length; i++)
				if (!inHand(params[i],focusPlayer.getHand()))
					return false;
			
			Cardpile newPile = new Cardpile();
			List<Card> cc = new LinkedList<Card>();
			for(int i=0;i<params.length;i++) {
				cc.add(new Card(params[i].replaceAll("[^0-9]",""),params[i].replaceAll("[0-9]","")));
			}
			
			newPile.addCardsOnTop(cc);
			if(isValidPile(newPile)) {
				piles.add(newPile);
				focusPlayer.getHand().removeCards(newPile.getCards());
				return true;
			}
			return false;
		}
	}
	
	public boolean isValidPile(Cardpile c) {
		return isRun(c)||isSet(c)||isAllOneColor(c);
	}
	
	public boolean isValidPlacement(String cardName, int pile, int position) {
		Cardpile newPile = new Cardpile(piles.get(pile));
		newPile.addCardAtIndex(new Card(cardName.substring(0,1),cardName.substring(1,2)),position);
		return isValidPile(newPile);
	}
	
	public boolean isRun(Cardpile c) {
		if(c.size()<4)
			return false;
		List<Card> cards = c.getCards();
		for(int i=0;i<c.size()-1;i++) {
			if(!(Integer.parseInt(cards.get(i).getVal())<Integer.parseInt(cards.get(i+1).getVal())||cards.get(i).getCategory().equals("W")||cards.get(i+1).getCategory().equals("W")))
				return false;
		}
		return true;
	}
	
	public boolean isSet(Cardpile c) {
		if(c.size() < 2)
			return false;
		List<Card> cards = c.getCards();
		for(int i = 0; i < c.size(); i++) {
			for(int j = 0; j < c.size(); j++)
			if(!(Integer.parseInt(cards.get(i).getVal()) == Integer.parseInt(cards.get(j).getVal()) || cards.get(i).getCategory().equals("W")||cards.get(j).getCategory().equals("W")))
				return false;
		}
		return true;
	}
	
	public boolean isAllOneColor(Cardpile c) {
		if(c.size()<7)
			return false;
		List<Card> cards = c.getCards();
		for(int i=0;i<c.size();i++) {
			for(int j=0;j<c.size();j++)
			if(!(cards.get(i).getCategory().equals(cards.get(j).getCategory()) || cards.get(i).getCategory().equals("W")||cards.get(j).getCategory().equals("W")))
				return false;
		}
		return true;
	}
	
	// Returns whether a card is valid to use
	public boolean inHand(String cardName, Hand h) {
		if(!cardDeck.exists(cardName))
			return false;
		
		return h.inHand(cardName);
	}
	/**
	 * Checks to see if a player has met the winning conditions
	 * @author Chris
	 * @return
	 */
	public boolean checkWinCondition(Player focusPlayer, String move)
	{
		if(focusPlayer.getNumOfCards() == 0)
			return true;
		return false;
	}
	
	public List<Cardpile> getPiles(){
		return piles;
	}

	public Card draw() {
		Card drawnCard = piles.get(0).takeCards(1).get(0);
		if(piles.get(0).size() == 0) {
			piles.get(0).addCardsOnTop(piles.get(1).takeCards(piles.get(1).size()));
		}
		return drawnCard;
	}
}