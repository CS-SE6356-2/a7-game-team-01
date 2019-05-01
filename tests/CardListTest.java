import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardListTest {
	private Deck deck;
	
    @BeforeEach
    void setUp() {
    	deck = new Deck(new File("cardlist.txt"));
    }

    @AfterEach
    void tearDown() {
    }

    /*
     * The deck should contain:
     * - 4 colors/categories: red (R), green (G), blue (B), yellow (Y)
     * - the same # of cards of each color
     */
    @Test
    void cardsCategoryTest() {
    	final String[] categories = new String[] { "R", "G", "B", "Y" };
    	
    	final int[] categoryCount = new int[categories.length];
    	for (int i = 0; i < deck.size(); ++i) {
    		final Card card = deck.getCardAt(i);
    		final int categoryNum = Arrays.asList(categories).indexOf(card.getCategory());
    		assertTrue(categoryNum >= 0); // each card must have a valid category
    		++categoryCount[categoryNum];
    	}
    	
    	// each category must have the same number of cards
    	for (int i = 1; i < categories.length; ++i) {
    		assertEquals(categoryCount[0], categoryCount[i]);
    	}
    }

    /*
     * The deck should contain:
     * - 8 cards of each number from 1-12
     * - 8 wild cards (W)
     * - 4 skip cards (S)
     */
    @Test
    void cardsValueTest() {
    	final String[] values = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "W", "S" };
    	
    	final int[] valueCount = new int[values.length];
    	for (int i = 0; i < deck.size(); ++i) {
    		final Card card = deck.getCardAt(i);
    		final int valueNum = Arrays.asList(values).indexOf(card.getVal());
    		assertTrue(valueNum >= 0); // each card must have a valid value
    		++valueCount[valueNum];
    	}
    	
    	// there must be exactly 8 cards of each number from 1-12
    	for (int i = 0; i < 12; ++i) {
    		assertEquals(valueCount[i], 8);
    	}
    	assertEquals(valueCount[12], 8); // there must be exactly 8 wild cards
    	assertEquals(valueCount[13], 4); // there must be exactly 8 skip cards
    }
}