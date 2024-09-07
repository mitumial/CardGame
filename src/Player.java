
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
        boolean[] isSequenceStarter = new boolean[cards.length];

        // organiza en orden de pintas y valor de las cartas, para facilidad de manejo
        Arrays.sort(cards, Comparator.comparing(PlayingCard::getIndex));

        for (PlayingCard card : cards) {
            counters[card.getNameCard().ordinal()]++;
        }
        int p;  
        int k = 0;
        // for (int i = 0; i < cards.length; i++) {
        while (k < cards.length){
            p = 1; // se resetea al cambiar de carta
            // loop para encontrar cartas secuenciales a la carta en [i]
            for (int j = k + 1; j < cards.length; j++) {
                // checkea por pinta y por valor de carta secuencial
                if ((cards[k].getSuit() == cards[j].getSuit()) && (cards[k].getNameCard().ordinal()+p == cards[j].getNameCard().ordinal())) {
                    if (p==1){
                        isSequenceStarter[k] = true;
                        countersSequence[k]++; 
                    }
                    countersSequence[j]++; 
                    p++; // incrementa al encontrar una carta secuencial, para checkear por el siguiente valor en la secuencia
                }
            }
            k += p;
        }

        boolean areThereGroups = false;
        for (int i = 0; i < counters.length; i++) {
            
            if (counters[i] >= 2) {
                if (!areThereGroups){
                    areThereGroups = true;
                    msg = "The following groups were found:\n";
                }
                msg += Group.values()[counters[i]] + " of " + NameCard.values()[i] + "\n";                                                                                          
            } 
            // if ((counters[i] == 1)) {
            //     if (i == 0 || i > 9){
            //         score +=  10;
            //     } else {
            //         score += i + 1;
            //     }   
            // } 
        }
        for (int i = 0; i < cards.length; i++) {
            if (countersSequence[i] > 0) {
                if (isSequenceStarter[i]){
                    msg += "RUN of " + cards[i].getSuit() + " with "+ cards[i].getNameCard();
                    continue;
                }
                msg += ", "+ cards[i].getNameCard();
                if (countersSequence[i+1] == 0){
                    msg += "\n";
                }
            } 
        }
        
        // for (int i = 0; i < cards.length; i++) {
        //     if (countersSequence[i] > 1) {
        //         msg += "RUN of " + cards[i].getSuit() + " with ";
        //         for (int j = 0; j < countersSequence[i] - 1; j++) {
        //             msg += cards[i+j].getNameCard() + ", ";
        //         }
        //         msg += "and "+ cards[i+countersSequence[i]-1].getNameCard() +"\n";
        //     } else {
        //         if ((counters[cards[i].getNameCard().ordinal()] == 1) && (countersSequence[i] == 0)) {
        //             if ((cards[i].getNameCard().ordinal() == 0) || (cards[i].getNameCard().ordinal() >= 9)){
        //                 score +=  10;
        //             } else {
        //                 score += cards[i].getNameCard().ordinal() + 1;
        //             } 
        //         }
        //     }
        // }
        msg += "Your final score is " + String.valueOf(score);
        
        return msg;
    }
}
