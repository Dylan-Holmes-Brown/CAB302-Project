package Views;

import javax.swing.*;
import java.awt.*;

public class userOptions extends JFrame{
    private static JLabel userLabel;
    private static JButton button;
    private static JLabel success;

    public userOptions() {
        JLabel label = new JLabel();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame.setTitle("User Options");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,400));

        JLabel userNameLabel = new JLabel("Select An Option:");
        userNameLabel.setBounds(80,80,180,25);
        panel.add(userNameLabel);

        JButton button = new JButton("Buy/Sell");
        button.setBounds(100,120,150, 20);
        panel.add(button);

        JButton buttonUser = new JButton("Updates");
        buttonUser.setBounds(100,150,150, 20);
        panel.add(buttonUser);

        JButton buttonAnother = new JButton("Asset Graph");
        buttonAnother.setBounds(100,180,150, 20);
        panel.add(buttonAnother);

        JButton buttonother = new JButton("Change Password");
        buttonother.setBounds(100,210,150, 20);
        panel.add(buttonother);

        JButton buttonLogOut = new JButton("Log Out");
        buttonLogOut.setBounds(100,240,150, 20);
        panel.add(buttonLogOut);


        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String[] args){

        new userOptions();
    }
}
