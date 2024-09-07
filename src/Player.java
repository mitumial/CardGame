
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JPanel;

public class Player {
    private final int TOTAL_CARDS = 10;
    private final int MARGIN = 30;
    private final int DISTANCE = 75;

    private PlayingCard[] cards = new PlayingCard[TOTAL_CARDS];
    private Random rdm = new Random();

    public void deal(){
        int i = 0;
        for (PlayingCard card : cards) {
            cards[i++] = new PlayingCard(rdm);
        }
    }

    public void show(JPanel pnl){
        pnl.removeAll();

        int p = 1;
        for (PlayingCard card : cards) {
            card.show(pnl, MARGIN + TOTAL_CARDS * DISTANCE - p++ * DISTANCE, MARGIN);
        }

        pnl.repaint();
    }

    public String getGroups(){
        String msg = "There weren't any groups found \n";
        int score = 0;

        int[] counters = new int[NameCard.values().length];
        int[] countersSequence = new int[cards.length];
        boolean[] isSequenceStarter = new boolean[cards.length]; //es true cuando una secuencia comienza

        // organiza en orden de pintas y valor de las cartas, para facilidad de manejo
        Arrays.sort(cards, Comparator.comparing(PlayingCard::getIndex));

        for (PlayingCard card : cards) {
            counters[card.getNameCard().ordinal()]++;
        }

        int p;  
        int k = 0;
        while (k < cards.length){
            p = 1; // se resetea al cambiar de carta
            // loop para encontrar cartas secuenciales a la carta en [i]
            for (int j = k + 1; j < cards.length; j++) {
                // checkea por pinta y por valor de carta secuencial
                if ((cards[k].getSuit() == cards[j].getSuit()) && (cards[k].getNameCard().ordinal()+p == cards[j].getNameCard().ordinal())) {
                    if (p==1){
                        isSequenceStarter[k] = true;    // en carta k comienza secuencia
                        countersSequence[k]++; 
                    }
                    countersSequence[j]++; // cada carta dentro de una secuencia tiene contador=1
                    p++; // incrementa al encontrar una carta secuencial, para checkear por el siguiente valor en la secuencia
                }
            }
            k += p; // salta las cartas dentro de la secuencia ya encontrada
        }

        boolean areThereGroups = false;
        for (int i = 0; i < counters.length; i++) {
            if (counters[i] >= 2) {
                if (!areThereGroups){
                    areThereGroups = true;
                    msg = "The following groups were found:";
                }
                msg += "\n" + Group.values()[counters[i]] + " of " + NameCard.values()[i];                                                                                          
            }
        }
        for (int i = 0; i < cards.length; i++) {
            // si el contador de secuencia es mayor a 0, la carta es parte de una secuencia
            if (countersSequence[i] > 0) {
                if (!areThereGroups){
                    areThereGroups = true;
                    msg = "The following groups were found:";
                }
                // mensaje especial para comenzar una secuencia
                if (isSequenceStarter[i]){
                    msg += "\nRUN of " + cards[i].getSuit() + " with "+ cards[i].getNameCard();
                    continue;
                }
                // mensaje especial para carta en medio de una secuencia
                msg += ", "+ cards[i].getNameCard(); 
            } else if (counters[cards[i].getNameCard().ordinal()] == 1){ // verificar que no son cartas en pares/ternas
                // si son cartas ACE, o TEN, JACK, QUEEN, KING
                if ((cards[i].getNameCard().ordinal() == 0) || (cards[i].getNameCard().ordinal() >= 9)){
                    score +=  10;
                } else {
                    score += cards[i].getNameCard().ordinal() + 1;
                }
            } 
        }
        msg += "\nYour final score is " + score;
        return msg;
    }
}
