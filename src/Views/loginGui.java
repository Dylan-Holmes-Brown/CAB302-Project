package Views;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginGui extends JFrame {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passLabel;
    private static JPasswordField passText;
    private static JButton button;
    private static JLabel success;

    public loginGui() {
        super("Action Time");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(400,400));

        JTabbedPane panel = new JTabbedPane();

        panel.setBorder(BorderFactory.createTitledBorder("Login"));

        JPanel buttonPanel = new JPanel();

        JLabel userTitle = new JLabel("User login");
        userTitle.setBounds(130,30,180,25);
        buttonPanel.add(userTitle);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(30,60,80,25);
        buttonPanel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(130,60,165,25);
        buttonPanel.add(userText);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(30,100,80,25);
        buttonPanel.add(passLabel);

        JPasswordField passText = new JPasswordField(20);
        passText.setBounds(130,100,165,25);
        buttonPanel.add(passText);

        JButton button = new JButton("Log-In");
        button.setBounds(150,150,100, 20);
        buttonPanel.add(button);

        buttonPanel.setLayout(null);

        panel.add("User", buttonPanel);


        JPanel adminButtonPanel = new JPanel();

        JLabel adminTitle = new JLabel("Admin login");
        adminTitle.setBounds(130,30,180,25);
        adminButtonPanel.add(adminTitle);

        JLabel adminUser = new JLabel("Username");
        adminUser.setBounds(30,60,80,25);
        adminButtonPanel.add(adminUser);

        JTextField adminText = new JTextField(20);
        adminText.setBounds(130,60,165,25);
        adminButtonPanel.add(adminText);

        JLabel adminPass = new JLabel("Password");
        adminPass.setBounds(30,100,80,25);
        adminButtonPanel.add(adminPass);

        JPasswordField adminPassText = new JPasswordField(20);
        adminPassText.setBounds(130,100,165,25);
        adminButtonPanel.add(adminPassText);

        JButton adminButton = new JButton("Log-In");
        adminButton.setBounds(150,150,100, 20);
        adminButtonPanel.add(adminButton);

        adminButtonPanel.setLayout(null);
        panel.add("Admin", adminButtonPanel);

        JPanel helpPanel = new JPanel();

        JLabel helpMsg = new JLabel("We will help you, we value you");
        helpMsg.setBounds(150,150,100, 20);
        helpPanel.add(helpMsg);

        panel.add("HELP",helpPanel);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }


    public static void main(String[] args){

        new loginGui();
    }

}