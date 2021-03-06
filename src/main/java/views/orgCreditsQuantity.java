package views;

import common.Organisation;
import common.User;
import common.sql.AssetTypeData;
import common.sql.OrganisationData;

import javax.swing.*;
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
public class orgCreditsQuantity extends JFrame implements Serializable{
    private static final long serialVersionUID = 78L;

    // Global Variables
    private OrganisationData orgData;
    private AssetTypeData assetTypeData;
    private User user;
    private Organisation org;
    private List<String> assetList;
    private List<String> orgList;
    private List<String> orgAssetList;
    private List<Organisation> organisationList;

    // JSwing Variables
    private JComboBox assetBox;
    private JComboBox orgBox;

    private JLabel orgLabel;
    private JLabel orgCredits;
    private JTextField orgCreditsField;
    private JLabel assetQuantity;
    private JTextField assetQField;
    private JLabel assetLabel;

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
     * @param orgData The organisation data from the database
     * @param assetTypeData The asset type data from the database
     * @param user The signed in user
     */
    public orgCreditsQuantity(User user, OrganisationData orgData, AssetTypeData assetTypeData) {
        // Initialise Data
        this.orgData = orgData;
        this.assetTypeData = assetTypeData;
        this.user = user;
        assetList = new ArrayList<>();
        orgList = new ArrayList<>();
        organisationList = new ArrayList<>();
        orgAssetList = new ArrayList<>();

        // Initialise the UI and listeners
        initUI();
        addButtonListeners(new ButtonListener());
        addRadioListeners(new RadioListener());
        addClosingListener(new ClosingListener());

        // Quantity field listener to to make sure key pressed is number or backspace
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
        // Credits field listener to to make sure key pressed is number or backspace
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
        setTitle("Asset Trading System - Add Credits and Quantity of Assets to Organisation");
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
        contentPane.add(makeDataPanel());

        contentPane.add(Box.createVerticalStrut(5));
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
    private JPanel makeDataPanel() {
        // Initialise the JPanel
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        // Initialise the asset ListModel and add to an asset name list
        ListModel assetModel = assetTypeData.getModel();
        for (int i = 0; i < assetTypeData.getSize(); i++) {
            assetList.add(assetModel.getElementAt(i).toString());
        }

        // Initialise the organisation model, add the organisation name to a list
        // and organisation object to a separate list
        ListModel model = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            Organisation org = orgData.get(model.getElementAt(i));
            if (!orgList.contains(org.getName())) {
                orgList.add(org.getName());
                organisationList.add(org);
            }
        }

        // Initialise the Drop down boxes and panel
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

    /**
     * Handles events for the radio buttons on the UI
     *
     * @author Dylan Holmes-Brown
     */
    private class RadioListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // Get the button pressed and set fields to editable or not
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
                clearFields();
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
            // Initialise Variables
            String selectedOrg = orgBox.getSelectedItem().toString();

            // Get all assets of an organisation
            for (int i = 0; i < organisationList.size(); i++) {
                if (organisationList.get(i).getName().equals(selectedOrg)) {
                    orgAssetList.add(organisationList.get(i).getAsset());
                    org = organisationList.get(i); // organisation equal the organisation that has that asset
                }
            }

            // Check if a radio button is selected
            if (addCredits.isSelected() || removeCredits.isSelected() || addQuantity.isSelected() || removeQuantity.isSelected()) {
                // Check if a credits related button is selected
                if (addCredits.isSelected() || removeCredits.isSelected()) {
                    String credits = orgCreditsField.getText();
                    int creditsInt = Integer.parseInt(credits);
                    // Check that credits has input
                    if (orgCreditsField != null && !orgCreditsField.getText().equals("")
                            && selectedOrg != null) {
                        // Check if add credits is selected
                        if (addCredits.isSelected()) {
                            // add credits to the org
                            orgData.addCredits(orgBox.getSelectedItem(), creditsInt);
                            JOptionPane.showMessageDialog(null, String.format("'%s' credits added to Organisation '%s' successfully", creditsInt, orgBox.getSelectedItem().toString()));
                        }
                        // Check if remove credits is selected
                        else if (removeCredits.isSelected()) {
                            if (creditsInt <= org.getCredits()) {
                                // remove credits from the org
                                orgData.removeCredits(orgBox.getSelectedItem(), creditsInt);
                                JOptionPane.showMessageDialog(null, String.format("'%s' credits removed from Organisation '%s' successfully", creditsInt, orgBox.getSelectedItem().toString()));
                            }
                            else {
                                JOptionPane.showMessageDialog(null, String.format("Cannot remove '%s' quantity from organisation '%s'!", creditsInt, orgBox.getSelectedItem().toString()), "Quantity Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    // Fields not filled in
                    else {
                        JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // Check if quantity related button is selected
                else if (addQuantity.isSelected() || removeQuantity.isSelected()) {
                    // Get drop box value
                    String selectedAsset = assetBox.getSelectedItem().toString();
                    String quantity = assetQField.getText();
                    int quantityInt = Integer.parseInt(quantity);

                    // Check that quantity has input
                    if (assetQField != null && !assetQField.getText().equals("")
                            && selectedAsset != null) {
                        // Check if add quantity is selected
                        if (addQuantity.isSelected()) {
                            // Check if organisation contains the selected asset
                            if (orgAssetList.contains(selectedAsset)) {
                                // add quantity to an asset for an organisation
                                orgData.addQuantity(orgBox.getSelectedItem(), assetBox.getSelectedItem().toString(), quantityInt);
                                JOptionPane.showMessageDialog(null,
                                        String.format("'%s' quantity added to asset '%s' for Organisation '%s' successfully",
                                                quantityInt, assetBox.getSelectedItem().toString(), orgBox.getSelectedItem().toString()));
                            }
                            // Organisation does not hold the asset
                            else{
                                JOptionPane.showMessageDialog(null, String.format("Organisation '%s' does not hold the asset '%s'", orgBox.getSelectedItem(), selectedAsset), "Asset Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        // Check if remove quantity is selected
                        else if (removeQuantity.isSelected()) {
                            if (quantityInt <= org.getQuantity()) {
                                // Check if organisation contains the selected asset
                                if (orgAssetList.contains(selectedAsset)) {
                                    // Get inputs and add quantity to an asset for an organisation
                                    orgData.removeQuantity(orgBox.getSelectedItem().toString(), assetBox.getSelectedItem().toString(), quantityInt);
                                    JOptionPane.showMessageDialog(null, String.format("'%s' quantity removed from asset '%s' for Organisation '%s' successfully", quantityInt, assetBox.getSelectedItem().toString(), orgBox.getSelectedItem().toString()));
                                }
                                // Organisation does not hold the asset
                                else {
                                    JOptionPane.showMessageDialog(null, String.format("Organisation '%s' does not hold the asset '%s'", orgBox.getSelectedItem(), selectedAsset), "Asset Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(null, String.format("Cannot remove '%s' quantity from asset '%s'!", quantityInt, assetBox.getSelectedItem().toString()), "Quantity Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    // All fields not filled in
                    else {
                        JOptionPane.showMessageDialog(null, "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            // No radio button was selected
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
