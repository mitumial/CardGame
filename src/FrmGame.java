import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmGame extends JFrame {
    private JButton btnDeal;
    private JButton btnCheck;
    private JTabbedPane tpPlayers;
    private JPanel pnlPlayer1;
    private JPanel pnlPlayer2;
    private Player player1;
    private Player player2;

    public FrmGame(){
        btnDeal = new JButton();
        btnCheck = new JButton();
        tpPlayers = new JTabbedPane();
        pnlPlayer1 = new JPanel();
        pnlPlayer2 = new JPanel();

        setSize(900, 430);
        setTitle("Card Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pnlPlayer1.setBackground(new Color(252, 246, 177));
        pnlPlayer1.setLayout(null);
        pnlPlayer2.setBackground(new Color(209, 227, 221));
        pnlPlayer2.setLayout(null);

        tpPlayers.setBounds(10, 50, 860, 300);
        tpPlayers.addTab("Martín Estrada Contreras", pnlPlayer1);
        tpPlayers.addTab("Raul Vidal", pnlPlayer2);

        btnDeal.setBounds(20, 10, 90, 30);
        btnDeal.setText("Deal");
        btnDeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDealClick(e);
            }
        });

        btnCheck.setBounds(120, 10, 90, 30);
        btnCheck.setText("Check");
        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCheckClick(e);
            }
        });

        getContentPane().setLayout(null);
        getContentPane().add(tpPlayers);
        getContentPane().add(btnDeal);
        getContentPane().add(btnCheck);

        player1 = new Player();
        player2 = new Player();
    }
    private void btnDealClick(ActionEvent e) {
        player1.deal();
        player2.deal();

        player1.show(pnlPlayer1);
        player2.show(pnlPlayer2);
    }

    private void btnCheckClick(ActionEvent e) {
        switch (tpPlayers.getSelectedIndex()) {
            case 0:
                JOptionPane.showMessageDialog(null, player1.getGroups());
                break;
            case 1:
            JOptionPane.showMessageDialog(null, player2.getGroups());
                break; 
            default:
                throw new AssertionError();
        }
    }
}
