package Views;

import client.Main;
import client.NetworkDataSource;
import common.HashPassword;
import common.User;
import common.sql.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

/**
 * Initialises the user interface for the adding UI. Button listeners are included
 * as sub-classes of this class
 *
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class loginGui extends JFrame implements Serializable {

    private static final long serialVersionUID = 100L;
    private static JLabel userLabel;
    private static JTextField userField;
    private static JLabel passLabel;
    private static JPasswordField passField;

    //user Object
    private User self = new User();
    //private loginGui uObj;

    JRadioButton memberButton;
    JRadioButton adminButton;
    JButton login;

    UserData data;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param data the user data from the database
     */
    public loginGui(UserData data) {
        // Connect to Database
        this.data = data;

        // Initialise the UI and listen for a button click
        initUI();
        login.addActionListener(new loginGui.ButtonListener());
        addWindowListener(new loginGui.ClosingListener());

        // Set up the frame
        setTitle("User Login");
        setMinimumSize(new Dimension(200, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initialises the UI placing the panels in a box layout with vertical
     * alignment spacing each panel.
     */
    private void initUI() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserDetailsPanel());
        contentPane.add(Box.createVerticalStrut(5));
        contentPane.add(makeButtonsPanel());
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeCreateUserPanel());
        contentPane.add(Box.createVerticalStrut(20));
    }

    private JPanel makeUserDetailsPanel() {

        JPanel userPanel = new JPanel();
        GroupLayout layout = new GroupLayout(userPanel);
        userPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Label of fields
        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");

        // Text Fields
        userField = new JTextField(30);
        userField.setPreferredSize(new Dimension(30, 20));
        passField = new JPasswordField(30);
        passField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Two parallel groups 1. contains labels and the other the fields
        hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel).addComponent(passLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(userField).addComponent(passField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(userLabel).addComponent(userField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passLabel).addComponent(passField));
        layout.setVerticalGroup(vGroup);
        return userPanel;

    }

    private Component makeButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Label Buttons
        memberButton = new JRadioButton("Member");
        adminButton = new JRadioButton("Admin");

        // Create and space Buttons
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(memberButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(adminButton);

        return buttonPanel;
    }

    private JPanel makeCreateUserPanel() {
        JPanel createUserPanel = new JPanel();
        createUserPanel.setLayout(new BoxLayout(createUserPanel, BoxLayout.X_AXIS));

        // Create Button, add to panel and add spacing
        login = new JButton("Login");
        createUserPanel.add(Box.createHorizontalStrut(50));
        createUserPanel.add(login);
        createUserPanel.add(Box.createHorizontalStrut(50));
        return createUserPanel;
    }

    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == login) {
                createPressed();
            }
        }

        /**
         * When the login button is pressed, check if the credentials match, if not
         * or display error
         */
        private void createPressed() {
            String accountType = "";

            // If all fields are filled in continue
            if (userField.getText() != null && !userField.getText().equals("")
                    && !passField.equals("") && (memberButton.isSelected() || adminButton.isSelected())) {

//                // Depending on radio button selected choose account type
                if (memberButton.isSelected()) {
                    accountType = "Member";
                }
                else if (adminButton.isSelected()) {
                    accountType = "Admin";
                }

                // ver
                User match = data.get(userField.getText());
                self = new User(userField.getText(), HashPassword.hashPassword(String.valueOf(passField.getPassword())), accountType);
                if (match.getUsername().equals(self.getUsername())){
                    JOptionPane.showMessageDialog(null, "Success");
                    dispose();
                    new userOptions(match);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Fail");
                }

            }

            // Not all fields are filled in
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Incorrect credentials!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Implements the windowClosing method from WindowAdapter to persist the contents of the
     * user table
     */
    private class ClosingListener extends WindowAdapter {
        /**
         * @see WindowAdapter#windowClosing(WindowEvent)
         */
        public void windowClosing(WindowEvent e) {
            data.persist();
            System.exit(0);
        }
    }

    public User getSelf() {
        return self;
    }

}