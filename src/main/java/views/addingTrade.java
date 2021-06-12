package views;

import javax.swing.*;
import java.io.Serializable;

import common.Organisation;
import common.Trade;
import common.User;
import common.sql.AssetTypeData;
import common.sql.CurrentData;
import common.sql.OrganisationData;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class addingTrade extends JFrame implements Serializable {

    private static final long serialVersionUID = 222L;
    // Global Variables
    private CurrentData tradeData;
    private OrganisationData orgData;
    private AssetTypeData assetData;
    private User user;
    private List<Organisation> organisationList;
    private List<String> orgList;
    private List<String> assetList;
    private List<String> tradeBuyList;
    private List<String> tradeList;

    // JSwing Variables
    private JList list;
    private JComboBox dropDownBox;
    private JComboBox assetBox;

    private JLabel traPrice;
    private JTextField traPriceField;
    private JLabel assetQuantity;
    private JTextField assetQField;
    private JLabel assetLabel;
    private JLabel orgAssetLabel;

    private JRadioButton buyButton;
    private JRadioButton sellButton;
    private ButtonGroup radioGroup;

    private JButton createButton;
    private JButton deleteButton;
    private JButton backButton;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param user the user signed in
     * @param tradeData the current trade data accessor to the database
     * @param orgData the organisation data accessor to the database
     * @param assetData the asset type data accessor to the database
     */
    public addingTrade(User user, CurrentData tradeData, OrganisationData orgData, AssetTypeData assetData) {
        // Initialise data
        this.tradeData = tradeData;
        this.orgData = orgData;
        this.assetData = assetData;
        this.user = user;
        organisationList = new ArrayList<>();
        orgList = new ArrayList<>();
        assetList = new ArrayList<>();
        tradeList = new ArrayList<>();

        // Initialise the UI and listen for a Button press or window close
        initUI();
        checkListSize();
        addRadioListeners(new addingTrade.RadioListener());
        addButtonListeners(new addingTrade.ButtonListener());
        addNameListListener(new addingTrade.NameListListener());
        addClosingListener(new addingTrade.ClosingListener());

        // Decorate the frame and make it visible
        setTitle("Asset Trading System - Create Trade");
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
        contentPane.add(makeTradeListPane());

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeTradeFieldPanel());

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
     * Makes a JScrollPane that holds a JList for the list of trades in the
     * current trade table.
     *
     * @return the scrolling name list panel
     */
    private JScrollPane makeTradeListPane() {
        // Get the trade data in a presentable format
        ListModel model = tradeData.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            Trade trade = tradeData.get(model.getElementAt(i));
            // Check that the trade against the user's organisation
            if (user.getOrganisationalUnit().equals(trade.getOrganisation())) {
                Date date = trade.getDate();
                tradeList.add(String.format("%s %s %s for %s - %s", trade.getBuySell(), trade.getQuantity(), trade.getAsset(), trade.getPrice(), date));
            }
        }
        // Initialise the JList and JScrollerPane
        list = new JList(tradeList.toArray());
        list.setFixedCellWidth(200);

        // Set the scroller to display the list, initialise the scrollbars
        JScrollPane scroller = new JScrollPane(list);
        scroller.setViewportView(list);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set Dimensions
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));
        return scroller;
    }

    /**
     * Makes a JPanel containing 2 ComboBoxes, 1 for all assets, and the other for all
     * assets owned by the user's organisation.
     *
     * @return a panel containing the ComboBoxes
     */
    private JPanel makeDropDownPanel() {
        // Initialise JPanel
        JPanel panel = new JPanel();

        // Get all assets in the database
        ListModel assetModel = assetData.getModel();
        for (int i = 0; i < assetData.getSize(); i++) {
            assetList.add(assetModel.getElementAt(i).toString());
        }
        // Initialise asset box and add to the panel
        assetBox = new JComboBox(assetList.toArray());
        assetBox.setBackground(Color.white);
        assetLabel = new JLabel("All Assets");
        panel.add(assetLabel);
        panel.add(assetBox);

        // Initialise the organisation model, add the organisation object to a list
        // and organisation assets to a separate list
        ListModel orgModel = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            Organisation orgGet = orgData.get(orgModel.getElementAt(i));
            organisationList.add(orgGet);
            // Check if the current organisation is the same as the organisation of the user
            if (organisationList.get(i).getName().equals(user.getOrganisationalUnit())) {
                orgList.add(organisationList.get(i).getAsset());
            }
        }

        // Initialise organisation asset box and add to the panel
        dropDownBox = new JComboBox(orgList.toArray());
        dropDownBox.setBackground(Color.white);
        orgAssetLabel = new JLabel("Organisation Assets");
        panel.add(orgAssetLabel);
        panel.add(dropDownBox);
        return panel;
    }


    /**
     * Makes a JPanel containing the Organization name, credits, asset and quantity fields to be
     * recorded.
     *
     * @return a panel containing the user fields
     */
    private JPanel makeTradeFieldPanel() {
        // Initialise the panel
        JPanel traPanel = new JPanel();
        GroupLayout layout = new GroupLayout(traPanel);
        traPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Label of fields
        traPrice = new JLabel("Price");
        assetQuantity = new JLabel("Quantity");

        // Text Fields
        traPriceField = new JTextField(30);
        traPriceField.setPreferredSize(new Dimension(30, 20));
        assetQField = new JTextField(30);
        assetQField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Two parallel groups 1. contains labels and the other the fields
        hGroup.addGroup(layout.createParallelGroup().addComponent(traPrice).addComponent(assetQuantity));
        hGroup.addGroup(layout.createParallelGroup().addComponent(traPriceField).addComponent(assetQField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(traPrice).addComponent(traPriceField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assetQuantity).addComponent(assetQField));
        layout.setVerticalGroup(vGroup);
        return traPanel;
    }

    /**
     * Make the two radio dials for account type fields
     *
     * @return a panel containing the account type fields
     */
    private JPanel makeRadioPanel() {
        // Initialise the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Initialise Buttons
        buyButton = new JRadioButton("Buy");
        sellButton = new JRadioButton("Sell");
        radioGroup = new ButtonGroup();

        // Add buttons to the panel and to the button group
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(buyButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(sellButton);
        radioGroup.add(buyButton);
        radioGroup.add(sellButton);
        return buttonPanel;
    }

    /**
     * Adds the buttons to the panel
     *
     * @return a panel containing the create organisation button
     */
    private JPanel makeButtonsPanel() {
        // Initialise Panel and buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");

        // Add buttons to the panel
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
        traPriceField.setText("");
        assetQField.setText("");
        radioGroup.clearSelection();
    }

    /**
     * Checks the size of the trade table determining the state of the delete button
     */
    private void checkListSize() {
        deleteButton.setEnabled(tradeData.getSize() != 0);
    }

    /**
     * Display the trade's details in the fields
     *
     * @param trade the selected trade to display
     */
    private void display(Trade trade) {
        // Check that the current trade is not null
        if (trade != null) {
            traPriceField.setText(String.valueOf(trade.getPrice()));
            assetQField.setText(String.valueOf(trade.getQuantity()));
            // Check the type of trade is 'Buy' and display information in correct fields
            if (trade.getBuySell().contains("Buy")) {
                buyButton.setSelected(true);
                assetBox.setSelectedItem(trade.getAsset());
                dropDownBox.setEnabled(false);
                assetBox.setEnabled(true);
            }
            // The trade type is 'Sell' and display information in the correct fields
            else {
                sellButton.setSelected(true);
                dropDownBox.setSelectedItem(trade.getAsset());
                dropDownBox.setEnabled(true);
                assetBox.setEnabled(false);
            }
        }
    }

    /**
     * Adds a listener to the radio buttons
     *
     * @param listener the listener for the radio buttons to use
     */
    private void addRadioListeners(ActionListener listener) {
        buyButton.addActionListener(listener);
        sellButton.addActionListener(listener);
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
        list.addListSelectionListener(listener);
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
            // Get the radio button selected and enable fields accordingly
            JRadioButton source = (JRadioButton) e.getSource();
            if (source == buyButton) {
                dropDownBox.setEnabled(false);
                assetBox.setEnabled(true);
            }
            else if (source == sellButton) {
                dropDownBox.setEnabled(true);
                assetBox.setEnabled(false);
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
            // Get the button pressed
            JButton source = (JButton) e.getSource();
            // Check the create button was pressed
            if (source == createButton) {
                createPressed();
                // persist the data dispose the window and return to the user options menu
                tradeData.persist();
                assetData.persist();
                orgData.persist();
                dispose();
                new userOptions(user);
            }
            // Check the delete button was pressed
            else if (source == deleteButton) {
                deletePressed();
                // persist the data dispose the window and return to the user options menu
                tradeData.persist();
                assetData.persist();
                orgData.persist();
                dispose();
                new userOptions(user);
            }
            // Check the back button was pressed
            else if (source == backButton) {
                // persist the data dispose the window and return to the user options menu
                tradeData.persist();
                assetData.persist();
                orgData.persist();
                dispose();
                new userOptions(user);
            }
        }

        /**
         * Get the current date
         *
         * @return the current date
         */
        public Date currDate(){
            long now = System.currentTimeMillis();
            Date dateobj = new Date(now );
            return dateobj;
        }

        /**
         * When the create button is pressed, add the trade information to the database
         * or display error
         */
        private void createPressed() {
            // Initialise data
            Trade t = new Trade();
            String selectedValue = dropDownBox.getSelectedItem().toString();
            String orderType = "Buy";
            int assetPrice = Integer.parseInt(assetQField.getText());
            int tradePrice = Integer.parseInt(traPriceField.getText());

            // If all fields are filled in continue
            if (traPriceField.getText() != null && !traPriceField.getText().equals("")
                    && !traPriceField.equals("") && !assetQField.equals("")&& (buyButton.isSelected() || sellButton.isSelected())) {
                // Check the buy button is selected an set the trade type
                if (buyButton.isSelected()) {
                    orderType = "Buy";
                }
                else if (sellButton.isSelected()) {
                    orderType = "Sell";
                }
                // Add trade to database and clear fields
                t = new Trade(orderType, user.getOrganisationalUnit(), selectedValue, assetPrice, tradePrice, currDate());
                tradeData.add(t);
                clearFields();
                JOptionPane.showMessageDialog(null, String.format("Trade '%s' successfully added", t.getAsset()));
            }
            // Not all fields are filled in
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
            checkListSize();
        }

        /**
         * When the delete button is pressed, delete the trade information from the database
         * or display error
         */
        private void deletePressed() {
            // Get selected trade
            int index = list.getSelectedIndex();
            String traFieldText = traPriceField.getText();

            // Remove trade from the database and clear the input fields
            ListModel model = tradeData.getModel();
            Trade trade = tradeData.get(model.getElementAt(index));

            if (user.getOrganisationalUnit().equals(trade.getOrganisation())) {
                tradeData.remove(trade.getID());
                clearFields();
                index--;
                if (index == -1) {
                    if (tradeData.getSize() != 0) {
                        index = 0;
                    }
                }
                list.setSelectedIndex(index);
                checkListSize();
                JOptionPane.showMessageDialog(null, String.format("Trade '%s - %s' successfully deleted", trade.getBuySell(), trade.getAsset()));
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Cannot delete trade from another organisation", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Implements a ListSelectionListener for making the UI respond when a
     * different element is selected from the list.
     *
     * @author Dylan Holmes-Brown
     */
    private class NameListListener implements ListSelectionListener {
        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            // Check to see the selected item is not null or empty
            if (list.getSelectedValue() != null
                    && !list.getSelectedValue().equals("")) {
                // Display the trade
                display(tradeData.get(list.getSelectedIndex() + 1));
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
            orgData.persist();
            assetData.persist();
            tradeData.persist();
            System.exit(0);
        }
    }

}
