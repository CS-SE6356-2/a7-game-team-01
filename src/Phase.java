//with reference from MaxRobinson
import java.util.*;

public class Phase {
    private Hand part1;
    private Hand part2;
    private Hand[] phasePart;
    private int numCards;
    private LinkedList<Card> phaseA;

    public static String phases[] = {"set,3,set,3", "set,3,run,4", "set,4,run,4", "run,7", "run,8", "run,9",
            "set,4,set,4", "color,7", "set,5,set,2", "set,5,set,3"};
    public static int numberPhases[] = {6, 7, 8, 7, 8, 9, 8, 7, 7, 8};

    public Phase(ArrayList<Card> firstPart, ArrayList<Card> secondPart) {
        part1 = new Hand();
        part2 = new Hand();
        phasePart = new Hand[2];
        phaseA = new LinkedList<Card>();

        if (firstPart != null) {
            numCards = firstPart.size();
            phaseA.addAll(firstPart);
            part1.getActiveCards().addAll(firstPart);
        }
        if (secondPart != null) {
            numCards += secondPart.size();
            phaseA.addAll(secondPart);
            part2.getActiveCards().addAll(secondPart);
        }

        phasePart[0] = part1;
        phasePart[1] = part2;
    }

    public Phase() {
        part1 = new Hand();
        part2 = new Hand();
        phasePart = new Hand[2];
        phaseA = new LinkedList<Card>();
        phasePart[0] = part1;
        phasePart[1] = part2;
    }

    public Hand[] getPhasePart() {
        return phasePart;
    }

    public void setPart(int part, LinkedList<Card> cards) {
        if (!(part < 2)) {
            return;
        }
        synchronized (cards) {
            phasePart[part].getActiveCards().clear();
            for (Card c: cards) {
                phasePart[part].addCards(cards);
            }
        }
    }

    public LinkedList<Card> getPhase() {
        return phaseA;
    }

    public int getNumCards() {
        this.numCards = part1.size() + part2.size();
        return numCards;
    }

}