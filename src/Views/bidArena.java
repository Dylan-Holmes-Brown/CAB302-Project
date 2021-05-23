package Views;

import javax.swing.*;
import java.awt.*;

public class bidArena extends JFrame{
    private static JButton button;
    private static JLabel success;

    public bidArena() {
        JLabel label = new JLabel();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame.setTitle("Bid Arena");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,400));

        JLabel userNameLabel = new JLabel("Items List:");
        userNameLabel.setBounds(40,40,180,25);
        panel.add(userNameLabel);

        JButton buttonBidAnother = new JButton("BID");
        buttonBidAnother.setBounds(250,100,60, 20);
        panel.add(buttonBidAnother);

        JButton buttonBid = new JButton("BID");
        buttonBid.setBounds(250,130,60, 20);
        panel.add(buttonBid);

        JButton buttonAdd = new JButton("BID");
        buttonAdd.setBounds(250,160,60, 20);
        panel.add(buttonAdd);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }


    public static void main(String[] args){

        new bidArena();
    }
}
