package Views;

import client.NetworkDataSource;
import common.HashPassword;
import common.User;
import common.sql.OrganisationData;
import common.sql.UserData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * Initialises the admin interface for the addingUserList UI. Button listeners are included
 * as sub-classes of this class
 *
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class addingUserList extends JFrame implements Serializable {

    private static final long serialVersionUID = 62L;
    private Object[] array;
    private UserData userData;
    private OrganisationData orgData;
    private User user;

    // JSwing Variables
    private JList userList;
    private JComboBox comboBox;
    private JLabel userLabel;
    private JTextField userField;
    private JLabel passLabel;
    private JTextField passField;
    private JLabel orgLabel;

    private JRadioButton memberButton;
    private JRadioButton adminButton;
    private ButtonGroup radioGroup;
    private JButton createButton;
    private JButton deleteButton;
    private JButton backButton;

    /**
     * Constructor sets up UI, adds listeners and displays
     *
     * @param user the user signed into the system
     * @param userData the user data accessor to the database
     * @param orgData the organisation data accessor to the database
     */
    public addingUserList(User user, UserData userData, OrganisationData orgData) {
        this.userData = userData;
        this.orgData = orgData;
        this.user = user;
        array = new String[orgData.getSize()];

        // Initialise the UI and listeners
        initUI();

        checkListSize();
        addRadioListeners(new RadioListener());
        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());
        addWindowListener(new ClosingListener());

        // Decorate the frame and make it visible
        setTitle("Asset Trading System - Create User");
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    /**
     * Initialises the UI placing the panels in a box layout with vertical
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
        contentPane.add(makeNameListPane());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserFieldPanel());

        contentPane.add(Box.createVerticalStrut(5));
        contentPane.add(makeDropDownPanel());

        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(makeRadioPanel());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());
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
     * Makes a JScrollPane that holds a JList for the list of names in the
     * user table.
     *
     * @return the scrolling name list panel
     */
    private JScrollPane makeNameListPane() {
        // Initialise the JList and JScrollerPane
        userList = new JList(userData.getModel());
        userList.setFixedCellWidth(200);
        JScrollPane scroller = new JScrollPane(userList);

        // Set the scroller to display the user list, initialise the scrollbars
        scroller.setViewportView(userList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set Dimensions
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));
        return scroller;
    }

    /**
     * Create a JPanel holding the drop down box with all available organisations
     *
     * @return the drop down box with all organisations
     */
    private JPanel makeDropDownPanel() {
        // Initialise the ListModel and array of all elements of the organisation table
        ListModel model = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            array[i] = model.getElementAt(i).toString();
        }
        // Initialise the Drop down box and panel
        comboBox = new JComboBox(array);
        comboBox.setBackground(Color.white);
        comboBox.setPrototypeDisplayValue("Text Size");
        JPanel panel = new JPanel();
        orgLabel = new JLabel("Organisation");

        // Add the label and drop down box to the panel
        panel.add(orgLabel);
        panel.add(comboBox);
        return panel;
    }

    /**
     * Makes a JPanel containing the username and password fields to be
     * recorded.
     *
     * @return a panel containing the user fields
     */
    private JPanel makeUserFieldPanel() {
        // Initialise the JPanel
        JPanel userPanel = new JPanel();
        GroupLayout layout = new GroupLayout(userPanel);
        userPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Initialise Labels and Fields
        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");
        userField = new JTextField(30);
        userField.setPreferredSize(new Dimension(30, 20));
        passField = new JTextField(30);
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
    private JPanel makeRadioPanel() {
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
     * Adds the buttons to the panel
     *
     * @return a panel containing the create user button
     */
    private JPanel makeButtonsPanel() {
        // Initialise the JPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Initialise the buttons and add them to the panel
        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Clear all input fields and radio buttons
     */
    private void clearFields() {
        userField.setText("");
        passField.setText("");
        radioGroup.clearSelection();
    }

    /**
     * Checks the size of the user table determining the state of the delete button
     */
    private void checkListSize() {
        deleteButton.setEnabled(userData.getSize() != 0);
    }

    /**
     * Display the user details in the fields
     * @param user the selected user to display
     */
    private void display(User user) {
        // Check that org is not null and set fields to org details
        if (user != null) {
            userField.setText(user.getUsername());
            passField.setText(user.getPassword());
            if (user.getAccountType().contains("Member")) {
                memberButton.setSelected(true);
            }
            else {
                adminButton.setSelected(true);
            }
            comboBox.setSelectedItem(user.getOrganisationalUnit());
        }
    }

    /**
     * Adds a listener to the radio buttons
     *
     * @param listener the listener for the radio buttons to use
     */
    private void addRadioListeners(ActionListener listener) {
        memberButton.addActionListener(listener);
        adminButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        createButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the name list
     *
     * @param listener the listener for the name list
     */
    private void addNameListListener(ListSelectionListener listener) {
        userList.addListSelectionListener(listener);
    }

    private class RadioListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source == memberButton) {
                comboBox.setEnabled(true);
            }
            else if (source == adminButton) {
                comboBox.setEnabled(false);
            }
        }
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
            if (source == createButton) {
                createPressed();
            }
            else if (source == deleteButton) {
                deletePressed();
            }
            else if (source == backButton) {
                userData.persist();
                orgData.persist();
                dispose();
                new adminOptions(user);
            }
        }

        /**
         * When the create user button is pressed, add the user information to the database
         * or display error
         */
        private void createPressed() {
            User u = new User();
            String accountType = "Member";
            String selectedOrg = String.valueOf(comboBox.getSelectedItem());

            // If all fields are filled in continue
            if (userField.getText() != null && !userField.getText().equals("")
                    && !passField.equals("") && (memberButton.isSelected() || adminButton.isSelected())) {
                // Depending on radio button selected choose account type
                if (memberButton.isSelected()) {
                    accountType = "Member";
                    u = new User(userField.getText(), HashPassword.hashPassword(String.valueOf(passField.getText())), accountType, selectedOrg);
                }
                else if (adminButton.isSelected()) {
                    accountType = "Admin";
                    u = new User(userField.getText(), HashPassword.hashPassword(String.valueOf(passField.getText())), accountType);
                }

                // Add user to database and clear fields
                userData.add(u);
                clearFields();
                JOptionPane.showMessageDialog(null, String.format("User '%s' successfully added", u.getUsername()));
            }

            // Not all fields are filled in
            else {
                JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
            checkListSize();
        }

        /**
         * When the delete button is pressed, delete the user information from the database
         * or display error
         */
        private void deletePressed() {
            // Get selected user
            int index = userList.getSelectedIndex();
            String username = userField.getText();

            // Check that the user to be deleted isn't the signed in user
            if (user.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(null, "Signed in user cannot be removed");
            }
            // The user is not the user signed in
            else {
                // Remove user from the database and clear input fields
                userData.remove(userList.getSelectedValue());
                clearFields();
                index--;
                if (index == -1) {
                    if (userData.getSize() != 0) {
                        index = 0;
                    }
                }
                userList.setSelectedIndex(index);
                checkListSize();
                JOptionPane.showMessageDialog(null, String.format("User '%s' successfully deleted", username));
            }
        }
    }

    /**
     * Implements a ListSelectionListener for making the UI respond when a
     * different name is selected from the list.
     *
     * @author Dylan Holmes-Brown
     */
    private class NameListListener implements ListSelectionListener {
        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            // Check to see that the selected item is not null,
            // and does not have an empty string
            if (userList.getSelectedValue() != null
                    && !userList.getSelectedValue().equals("")) {
                // Display the user
                display(userData.get(userList.getSelectedValue()));
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
            // Persist the data and stop the application
            userData.persist();
            System.exit(0);
        }
    }

}



