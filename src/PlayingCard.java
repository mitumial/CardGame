
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayingCard {
    private int idx;

    public PlayingCard(Random rdm){
        idx = rdm.nextInt(52) + 1;
    }

    public void show(JPanel pnl, int x, int y){
        String urlImage = "./img/card_" + String.valueOf(idx) + ".png";
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(urlImage));
        Image image = imageIcon.getImage(); 
        Image newimg = image.getScaledInstance(imageIcon.getIconWidth()*2, imageIcon.getIconHeight()*2,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        
        JLabel lbl = new JLabel(imageIcon);
        lbl.setBounds(x, y, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        pnl.add(lbl);
    }

    public Suit getSuit(){
        if (idx <= 13) {
            return Suit.CLUBS;
        } else if (idx <= 26) {
            return Suit.SPADES;
        } else if (idx <= 39) {
            return Suit.HEARTS;
        } else {
            return Suit.DIAMONDS;
        }
    }

    public NameCard getNameCard(){
        int residue = idx % 13;
        if (residue == 0){
            residue = 13;
        }
        return NameCard.values()[residue - 1];
    }

    public int getIndex(){
        return idx;
    }
}
