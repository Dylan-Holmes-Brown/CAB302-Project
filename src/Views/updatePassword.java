package Views;

import client.NetworkDataSource;
import common.HashPassword;
import common.User;
import common.sql.AssetTypeData;
import common.sql.UserData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * Initialises the update password interface for the update password UI. Listeners are included
 * as sub-classes of this class
 *
 * @author Dylan Holmes-Brown
 */
public class updatePassword extends JFrame implements Serializable {
    private static final long serialVersionUID = 124L;
    private User user;
    private UserData userData;
    private Boolean changed = false;

    // JSwing Variables
    private JLabel passLabel;
    private JTextField passField;
    private JButton updateButton;
    private JButton backButton;

    /**
     * Constructor sets up the UI, adds listeners and displays
     *
     * @param user the user signed into the system
     * @param userData the userdata accessor to the database
     */
    public updatePassword(User user, UserData userData) {
        this.userData = userData;
        this.user = user;

        // Initialise the UI and listeners
        initUI();
        addButtonListeners(new ButtonListener());
        addClosingListener(new ClosingListener());

        // Set up the frame
        setTitle("Asset Trading System - Update Password");
        setMinimumSize(new Dimension(400, 400));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initialises the UI placing the panels in a container with Y Axis
     * alignment spacing each panel.
     */
    private void initUI() {
        // Create a container for the panels and set the layout
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Add panels to container with padding
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeReturnPane());
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeFieldPanel());
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonPanel());
        contentPane.add(Box.createVerticalStrut(20));
    }

    /**
     * Create a JPanel with return button in the top right corner
     *
     * @return the created JPanel
     */
    private JPanel makeReturnPane() {
        // Initialise the JPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Initialise the button and add to the panel
        backButton = new JButton("Return to Menu");
        buttonPanel.add(backButton);
        return buttonPanel;
    }

    /**
     * Makes a JPanel containing the password fields to be
     * recorded.
     *
     * @return a panel containing the user fields
     */
    private JPanel makeFieldPanel() {
        // Initialise the JPanel
        JPanel userPanel = new JPanel();
        FlowLayout layout = new FlowLayout();
        userPanel.setLayout(layout);

        // Initialise the label and field
        passLabel = new JLabel("New Password");
        passField = new JTextField(30);
        passField.setPreferredSize(new Dimension(30, 20));

        // Add label and field to the panel
        userPanel.add(passLabel);
        userPanel.add(passField);
        return userPanel;
    }

    /**
     * Adds the button to the panel
     *
     * @return a panel containing the create user button
     */
    private JPanel makeButtonPanel() {
        // Initialise the Panel and button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        updateButton = new JButton("Update Password");

        // Add button to the panel
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Adds a listener to the button
     *
     * @param listener the listener for the button to use
     */
    private void addButtonListeners(ActionListener listener) {
        updateButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the JFrame
     *
     * @param listener the listener for the JFrame to use
     */
    private void addClosingListener(WindowListener listener) {
        addWindowListener(listener);
    }

    /**
     * Handles events for the buttons on the UI.
     *
     * @author Dylan Holmes-Brown
     */
    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // Get the button pressed and go to corresponding method
            JButton source = (JButton) e.getSource();
            if (source == updateButton) {
                // Update the password
                updatePressed();

                // Check that the password has been changed
                if (changed) {
                    // Get user with new information and persist the data
                    User u = userData.get(user.getUsername());
                    userData.persist();

                    // Close the frame and open the user options frame
                    dispose();
                    new userOptions(u);
                }
            }
            else if (source == backButton) {
                // Persist the data, dispose the frame and open the user options frame
                userData.persist();
                dispose();
                new userOptions(user);
            }
        }

        /**
         * When the update password button is pressed, update the password of the user in
         * the database or display error
         */
        private void updatePressed() {
            // Check that the field has an input
            if (passField.getText() != null) {
                // Hash the password prior to sending data over the server
                String password = HashPassword.hashPassword(String.valueOf(passField.getText()));

                // Check that the password entered is different to the current password
                if (user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, "Password must be different from previous password, Please Try Again", "Field Error", JOptionPane.ERROR_MESSAGE);
                }
                // The password is different
                else {
                    // Update the password
                    userData.update(user.getUsername(), password);
                    changed = true;
                    JOptionPane.showMessageDialog(null,
                            String.format("User '%s' password successfully updated", user.getUsername()));
                }
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
            // Persist data and close
            userData.persist();
            System.exit(0);
        }
    }
}
