package Views;

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
 * Initialises the user interface for the Asset Trading System. Displays login GUI, all listeners are included
 * as sub-classes of this class
 *
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class loginGui extends JFrame implements Serializable {

    private static final long serialVersionUID = 100L;

    // JSwing Variables
    private static JLabel userLabel;
    private static JTextField userField;
    private static JLabel passLabel;
    private static JPasswordField passField;
    private JRadioButton memberButton;
    private JRadioButton adminButton;
    private ButtonGroup radioGroup;
    private JButton login;

    // Database accessor
    private UserData data;

    /**
     * Constructor sets up UI frame and adds button listeners
     *
     * @param data the user data from the database
     */
    public loginGui(UserData data) {
        // Connect to Database
        this.data = data;

        // Create admin user if it doesn't exist
        if (data.getSize() == 0) {
            User admin = new User("admin", HashPassword.hashPassword("1234"), "Admin");
            data.add(admin);
        }

        // Initialise the UI and listen for a Button press or window close
        initUI();
        addButtonListeners(new ButtonListener());
        addWindowListener(new ClosingListener());

        // Set up the frame
        setTitle("Asset Trading System - User Login");
        setMinimumSize(new Dimension(200, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initialises the UI placing the panels in a box layout with Y Axis
     * alignment spacing each panel.
     */
    private void initUI() {
        // Create a container for the panels and set the layout
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Add panels to container with padding
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserDetailsPanel());
        contentPane.add(Box.createVerticalStrut(5));
        contentPane.add(makeButtonsPanel());
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeLoginPanel());
        contentPane.add(Box.createVerticalStrut(20));
    }

    /**
     * Create a JPanel with fields and labels for username and password
     *
     * @return the JPanel created
     */
    private JPanel makeUserDetailsPanel() {
        // Initialise the JPanel
        JPanel userPanel = new JPanel();
        GroupLayout layout = new GroupLayout(userPanel);
        userPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Initialise field labels
        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");

        // Initialise text fields
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

    /**
     * Create a JPanel of radio buttons for the Member and Admin account types
     *
     * @return the button panel created
     */
    private JPanel makeButtonsPanel() {
        // Initialise the JPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Initialise the radio buttons
        memberButton = new JRadioButton("Member");
        adminButton = new JRadioButton("Admin");
        radioGroup = new ButtonGroup();

        // Add buttons to the panel with padding
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(memberButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(adminButton);

        // Add buttons to a button group
        radioGroup.add(memberButton);
        radioGroup.add(adminButton);
        return buttonPanel;
    }

    /**
     * Create a JPanel for the login button
     *
     * @return the button panel created
     */
    private JPanel makeLoginPanel() {
        // Initialise the JPanel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));

        // Initialise the button and add it to the panel
        login = new JButton("Login");
        loginPanel.add(Box.createHorizontalStrut(50));
        loginPanel.add(login);
        loginPanel.add(Box.createHorizontalStrut(50));
        return loginPanel;
    }

    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        login.addActionListener(listener);
    }

    /**
     * Handles events for the login button on the UI.
     *
     * @author Vipin Vijay
     * @author Dylan Holmes-Brown
     * @author Laku Jackson
     */
    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // Get the button pressed and go to corresponding method
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
            String accountType = null;

            // Check if all fields are filled in
            if (userField.getText() != null && !userField.getText().equals("")
                    && !passField.equals("") && (memberButton.isSelected() || adminButton.isSelected())) {

                // Depending on radio button selected, choose account type
                if (memberButton.isSelected()) {
                    accountType = "Member";
                }
                else if (adminButton.isSelected()) {
                    accountType = "Admin";
                }

                // Get the user based on the input and initialise a user object of all fields inputted
                User match = data.get(userField.getText());
                User input = new User(userField.getText(), HashPassword.hashPassword(String.valueOf(passField.getPassword())), accountType);

                // Check that a user was found
                if (match != null) {
                    // Check that all fields match the user grabbed from the database
                    if (match.getUsername().equals(input.getUsername()) && match.getPassword().equals(input.getPassword()) &&
                            match.getAccountType().equals(input.getAccountType())) {
                        // Check if the user is an Admin
                        if (match.getAccountType().equals("Admin")) {
                            // persist the data, close the window and continue to the admin options frame
                            data.persist();
                            dispose();
                            new adminOptions(match);
                        }

                        // Check if the user is a Member
                        else if (match.getAccountType().equals("Member")) {
                            // persist the data, close the window and continue to the member options frame
                            data.persist();
                            dispose();
                            new userOptions(match);
                        }
                    }
                    // Fields entered do not match a user in the database
                    else {
                        JOptionPane.showMessageDialog(null, "Incorrect Credentials, Please Try Again", "Credentials Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // Username entered cannot be found in the database
                else {
                    JOptionPane.showMessageDialog(null, String.format("User '%s' cannot be found, Please Try Again", userField.getText()), "Credentials Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Not all fields are filled in
            else {
                JOptionPane.showMessageDialog(null, "Please Complete all Fields", "Field Error", JOptionPane.ERROR_MESSAGE);
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
            // persist data and stop the application
            data.persist();
            System.exit(0);
        }
    }
}