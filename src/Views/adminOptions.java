package Views;

import javax.swing.*;
import java.awt.*;

public class adminOptions extends JFrame{
    private static JLabel userLabel;
    private static JButton button;
    private static JLabel success;

    public adminOptions() {
        JLabel label = new JLabel();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame.setTitle("Admin Options");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,400));

        JLabel userNameLabel = new JLabel("Select An Option:");
        userNameLabel.setBounds(80,80,180,25);
        panel.add(userNameLabel);

        JButton button = new JButton("Edit Bidding Items");
        button.setBounds(100,120,150, 20);
        panel.add(button);

        JButton buttonUser = new JButton("Edit User List");
        buttonUser.setBounds(100,150,150, 20);
        panel.add(buttonUser);

        JButton buttonOptions = new JButton("Option etc");
        buttonOptions.setBounds(100,180,150, 20);
        panel.add(buttonOptions);

        JButton buttonAnother = new JButton("Another Button");
        buttonAnother.setBounds(100,210,150, 20);
        panel.add(buttonAnother);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String[] args){

        new adminOptions();
    }
}
