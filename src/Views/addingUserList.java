package Views;

import client.NetworkDataSource;
import common.HashPassword;
import common.User;
import common.sql.OrganisationData;
import common.sql.UserData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;


public class addingUserList extends JFrame implements Serializable {

    private static final long serialVersionUID = 62L;
    private JList userList;
    private JComboBox comboBox;

    private JLabel userLabel;
    private JTextField userField;
    private JLabel passLabel;
    private JTextField passField;

    private JLabel orgLabel;

    Object[] array;


    private JRadioButton memberButton;
    private JRadioButton adminButton;
    private ButtonGroup radioGroup;
    private JButton createButton;
    private JButton deleteButton;

    UserData userData;
    OrganisationData orgData;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param userData the user data from the database
     */
    public addingUserList(UserData userData, OrganisationData orgData) {
        this.userData = userData;
        this.orgData = orgData;
        array = new String[orgData.getSize()];

        initUI();
        checkListSize();

        // Add listeners to components
        addRadioListeners(new RadioListener());
        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());
        addClosingListener(new ClosingListener());


        // decorate the frame and make it visible
        setTitle("Create User");
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
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //list of users
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeNameListPane());

        //user inputs
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserFieldPanel());

        //dropdown for organisations
        contentPane.add(Box.createVerticalStrut(5));
        contentPane.add(makeDropDownPanel());

        //radio buttons
        contentPane.add(Box.createVerticalStrut(10));
        contentPane.add(makeRadioPanel());

        //buttons
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());

        contentPane.add(Box.createVerticalStrut(20));
    }

    private JScrollPane makeNameListPane() {
        userList = new JList(userData.getModel());
        //ListModel model = data.getModel();
        userList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(userList);
        scroller.setViewportView(userList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        return scroller;
    }

    private JPanel makeDropDownPanel() {
        ListModel model = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            array[i] = model.getElementAt(i).toString();

        }
        comboBox = new JComboBox(array);
        comboBox.setBackground(Color.white);
        JPanel panel = new JPanel();
        orgLabel = new JLabel("Organisation");
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
     * Make the two radio dials for account type fields
     *
     * @return a panel containing the account type fields
     */
    private JPanel makeRadioPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Label Buttons
        memberButton = new JRadioButton("Member");
        adminButton = new JRadioButton("Admin");
        radioGroup = new ButtonGroup();

        // Create and space Buttons
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(memberButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(adminButton);

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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

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
     * @param user
     */
    private void display(User user) {
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

    private void addRadioListeners(ActionListener listener) {
        memberButton.addActionListener(listener);
        adminButton.addActionListener(listener);
    }

    private void addButtonListeners(ActionListener listener) {
        createButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
    }

    private void addNameListListener(ListSelectionListener listener) {
        userList.addListSelectionListener(listener);
    }

    private void addClosingListener(WindowListener listener) {
        addWindowListener(listener);
    }

    public static void main(String[] args) {

        new addingUserList(new UserData(new NetworkDataSource()), new OrganisationData(new NetworkDataSource()));
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
     * Handles events for the buttons on the UI
     *
     * @author Dylan
     */
    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == createButton) {
                createPressed();
            }
            else if (source == deleteButton) {
                deletePressed();
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
                JOptionPane.showMessageDialog(new JFrame(), "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
            checkListSize();
        }

        private void deletePressed() {
            int index = userList.getSelectedIndex();
            String username = userField.getText();
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

    private class NameListListener implements ListSelectionListener {
        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            if (userList.getSelectedValue() != null
                    && !userList.getSelectedValue().equals("")) {
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
            userData.persist();
            System.exit(0);
        }
    }
}



