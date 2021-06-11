package Views;

import common.Organisation;
import common.User;
import common.sql.AssetTypeData;
import common.sql.OrganisationData;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Initialises the admin interface for adding assets to an Organisations.
 * Listeners are included as sub-classes of this class
 *
 * @author Dylan Holmes-Brown
 */
public class addingOrgAssets extends JFrame implements Serializable{
    private static final long serialVersionUID = 78L;

    private OrganisationData orgData;
    private AssetTypeData assetTypeData;
    private User user;
    private List<String> assetList;
    private List<String> orgList;

    // JSwing Variables
//    private JList orgList;
    private JComboBox assetBox;
    private JComboBox orgBox;

    private JLabel orgLabel;
    private JTextField orgField;
    private JLabel orgCredits;
    private JTextField orgCreditsField;
    private JLabel assetQuantity;
    private JTextField assetQField;
    private JLabel assetLabel;
    private JLabel orgBoxLabel;

    private JRadioButton addCredits;
    private JRadioButton removeCredits;
    private JRadioButton addQuantity;
    private JRadioButton removeQuantity;
    private ButtonGroup radioGroup;
    private JButton applyButton;
    private JButton backButton;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param orgData the user data from the database
     * @param assetTypeData the user data from the database
     */
    public addingOrgAssets(User user, OrganisationData orgData, AssetTypeData assetTypeData) {
        this.orgData = orgData;
        this.assetTypeData = assetTypeData;
        this.user = user;
//        array = new String[assetTypeData.getSize()];
//        orgArray = new Object[orgData.getSize()][2];
        assetList = new ArrayList<>();
        orgList = new ArrayList<>();


        // Initialise the UI and listeners
        initUI();
        addButtonListeners(new ButtonListener());
        addRadioListeners(new RadioListener());
        addClosingListener(new ClosingListener());

        // asset quantity field listener to to make sure key pressed is number or backspace
        assetQField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Check the key pressed and set the field to editable
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == 8) {
                    assetQField.setEditable(true);
                }
                // Set the field to uneditable if the key pressed is outside the range
                else {
                    assetQField.setEditable(false);
                }
            }
        });

        orgCreditsField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // Check the key pressed and set the field to editable
                if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == 8) {
                    orgCreditsField.setEditable(true);
                }
                // Set the field to uneditable if the key pressed is outside the range
                else {
                    orgCreditsField.setEditable(false);
                }
            }
        });
        // Decorate the frame and make it visible
        setTitle("Asset Trading System - Add Assets to Organisation");
        setMinimumSize(new Dimension(650, 300));
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

        contentPane.add(Box.createVerticalStrut(5));
        contentPane.add(makeRadioPanel());

        contentPane.add(Box.createVerticalStrut(5));
        contentPane.add(makeDropDownPanel());

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
     * Create a JPanel of radio buttons for the Member and Admin account types
     *
     * @return the button panel created
     */
    private JPanel makeRadioPanel() {
        // Initialise the JPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Initialise the radio buttons
        addCredits = new JRadioButton("Add Credits");
        removeCredits = new JRadioButton("Remove Credits");
        addQuantity = new JRadioButton("Add Quantity");
        removeQuantity = new JRadioButton("Remove Quantity");
        radioGroup = new ButtonGroup();

        // Add buttons to the panel with padding
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(addCredits);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(removeCredits);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(addQuantity);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(removeQuantity);

        // Add buttons to a button group
        radioGroup.add(addCredits);
        radioGroup.add(removeCredits);
        radioGroup.add(addQuantity);
        radioGroup.add(removeQuantity);
        return buttonPanel;
    }

    /**
     * Create a JPanel holding the drop down box with all available assets
     *
     * @return the drop down box with all assets
     */
    private JPanel makeDropDownPanel() {
        // Initialise the JPanel
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        // Initialise the ListModel and array of all elements of the asset table
        ListModel assetModel = assetTypeData.getModel();
        for (int i = 0; i < assetTypeData.getSize(); i++) {
            assetList.add(assetModel.getElementAt(i).toString());
        }

        ListModel orgModel = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            orgList.add(orgModel.getElementAt(i).toString());
        }

        // Initialise the Drop down box and panel
        assetBox = new JComboBox(assetList.toArray());
        assetBox.setBackground(Color.white);
        assetBox.setPrototypeDisplayValue("Text Size");

        orgBox = new JComboBox(orgList.toArray());
        orgBox.setBackground(Color.white);
        orgBox.setPrototypeDisplayValue("Text Size");

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        assetLabel = new JLabel("Asset");
        orgLabel = new JLabel("Organisation");

        // Initialise Labels and Fields
        orgCredits = new JLabel("Credits");
        assetQuantity = new JLabel("Quantity");
        orgCreditsField = new JTextField(30);
        orgCreditsField.setPreferredSize(new Dimension(30, 20));
        assetQField = new JTextField(30);
        assetQField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Two parallel groups 1. contains labels and the other the fields
        hGroup.addGroup(layout.createParallelGroup().addComponent(orgLabel).addComponent(orgCredits).addComponent(assetLabel).addComponent(assetQuantity));
        hGroup.addGroup(layout.createParallelGroup().addComponent(orgBox).addComponent(orgCreditsField).addComponent(assetBox).addComponent(assetQField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(orgLabel).addComponent(orgBox));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(orgCredits).addComponent(orgCreditsField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetLabel).addComponent(assetBox));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetQuantity).addComponent(assetQField));
        layout.setVerticalGroup(vGroup);

        // Set Dimensions
        panel.setMinimumSize(new Dimension(250, 150));
        panel.setPreferredSize(new Dimension(300, 150));
        panel.setMaximumSize(new Dimension(300, 200));
        return panel;
    }

    /**
     * Adds the create button to the Frame
     *
     * @return a panel containing the create organisation button
     */
    private JPanel makeButtonsPanel() {
        // Initialise the JPanel and create button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        applyButton = new JButton("Apply");

        // Add Create button to panel
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(applyButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Sets the text of all fields to be an empty string
     */
    private void clearFields() {
        orgField.setText("");
        orgCreditsField.setText("");
        assetQField.setText("");
    }

    /**
     * Adds a listener to the radio buttons
     *
     * @param listener the listener for the radio buttons to use
     */
    private void addRadioListeners(ActionListener listener) {
        addCredits.addActionListener(listener);
        removeCredits.addActionListener(listener);
        addQuantity.addActionListener(listener);
        removeQuantity.addActionListener(listener);
    }

    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        applyButton.addActionListener(listener);
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

    private class RadioListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source == addCredits || source == removeCredits) {
                orgBox.setEnabled(true);
                orgCreditsField.setEditable(true);
                assetBox.setEnabled(false);
                assetQField.setEditable(false);
            }
            else if (source == addQuantity || source == removeQuantity) {
                orgBox.setEnabled(true);
                orgCreditsField.setEditable(false);
                assetBox.setEnabled(true);
                assetQField.setEditable(true);
            }
        }
    }

    /**
     * Handles events for the buttons on the UI
     *
     * @author Dylan Holmes-Brown
     */
    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // Get the button source and check which method to goto according to the source
            JButton source = (JButton) e.getSource();
            if (source == applyButton) {
                applyPressed();
            }
            else if (source == backButton) {
                orgData.persist();
                assetTypeData.persist();
                dispose();
                new adminOptions(user);
            }
        }

        /**
         * When the create user button is pressed, add the organisation information
         * to the database or display error
         */
        private void applyPressed() {
            String input;
            int inputInt;
            if (addCredits.isSelected() || removeCredits.isSelected() || addQuantity.isSelected() || removeQuantity.isSelected()) {
                if (addCredits.isSelected() || removeCredits.isSelected()) {
                    // Get drop box value
                    String selectedValue = orgBox.getSelectedItem().toString();
                    if (orgCreditsField != null && !orgCreditsField.getText().equals("")
                            && selectedValue != null) {
                        if (addCredits.isSelected()) {
                            input = orgCreditsField.getText();
                            inputInt = Integer.parseInt(input);
                            orgData.addCredits(orgBox.getSelectedItem(), inputInt);
                            JOptionPane.showMessageDialog(null, String.format("'%s' credits added to Organisation '%s' successfully", inputInt, orgBox.getSelectedItem().toString()));
                        }
                        else if (removeCredits.isSelected()) {
                            input = orgCreditsField.getText();
                            inputInt = Integer.parseInt(input);
                            orgData.removeCredits(orgBox.getSelectedItem(), inputInt);
                            JOptionPane.showMessageDialog(null, String.format("'%s' credits removed from Organisation '%s' successfully", inputInt, orgBox.getSelectedItem().toString()));
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (addQuantity.isSelected() || removeQuantity.isSelected()) {
                    // Get drop box value
                    String selectedValue = assetBox.getSelectedItem().toString();
                    if (assetQField != null && !assetQField.getText().equals("")
                            && selectedValue != null) {
                        if (addQuantity.isSelected()) {
                            input = assetQField.getText();
                            inputInt = Integer.parseInt(input);
                            orgData.addQuantity(orgBox.getSelectedItem(), assetBox.getSelectedItem().toString(), inputInt);
                            JOptionPane.showMessageDialog(null, String.format("'%s' quantity added to asset '%s' for Organisation '%s' successfully", inputInt, assetBox.getSelectedItem().toString(), orgBox.getSelectedItem().toString()));
                        }
                        else if (removeQuantity.isSelected()) {
                            input = assetQField.getText();
                            inputInt = Integer.parseInt(input);
                            orgData.removeQuantity(orgBox.getSelectedItem(), assetBox.getSelectedItem().toString(), inputInt);
                            JOptionPane.showMessageDialog(null, String.format("'%s' quantity removed from asset '%s' for Organisation '%s' successfully", inputInt, assetBox.getSelectedItem().toString(), orgBox.getSelectedItem().toString()));
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
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
            orgData.persist();
            assetTypeData.persist();
            System.exit(0);
        }
    }
}
