package views;

import common.Organisation;
import common.Trade;
import common.User;
import common.sql.CurrentData;
import common.sql.OrganisationData;
import common.sql.TradeHistoryData;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Initialises the user interface for completing a trade.
 * Listeners are included as sub-classes of this class
 *
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class buyArena extends JFrame implements Serializable {

    // Global Variables
    private static final long serialVersionUID = 555;
    private List<String> tradeBuyList;
    private List<Trade> buyId;
    private List<String> tradeSellList;
    private List<Trade> sellId;
    private List<Organisation> orgList;
    private static User user;
    private OrganisationData orgData;
    private CurrentData tradeData;
    private TradeHistoryData historyData;
    private Organisation org;

    // JSwing Variables
    private JList buyList;
    private JList sellList;

    private JLabel buyName;
    private JTextField buyField;
    private JLabel buyQuantity;
    private JTextField buyQuantityField;
    private JLabel buyPrice;
    private JTextField buyPriceField;
    private JLabel buyOrganisation;
    private JTextField buyOrganisationField;

    private JLabel sellName;
    private JTextField sellField;
    private JLabel sellQuantity;
    private JTextField sellQuantityField;
    private JLabel sellPrice;
    private JTextField sellPriceField;
    private JLabel sellOrganisation;
    private JTextField sellOrganisationField;

    private JButton backButton;
    private JButton sellButton;
    private JButton buyButton;

    /**
     * Constructor sets up UI frame and adds listeners
     *
     * @param tradeData The trade data accessor to the database
     * @param orgData The organisation data accessor to the database
     * @param user The user logged in
     */
    public buyArena(User user, CurrentData tradeData, OrganisationData orgData, TradeHistoryData historyData) {
        // Initialise data
        this.user = user;
        this.historyData = historyData;
        org = new Organisation();
        this.tradeData = tradeData;
        this.orgData = orgData;
        orgList = new ArrayList<>();
        tradeBuyList = new ArrayList<>();
        tradeSellList = new ArrayList<>();
        buyId = new ArrayList<>();
        sellId = new ArrayList<>();

        // Get all rows where organisation is the same as the user's organisation
        ListModel model = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            org = orgData.get(model.getElementAt(i));
            if (org.getName().equals(user.getOrganisationalUnit())) {
                orgList.add(org);
            }
        }

        // Initialise the UI and listeners
        initUI();
        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());
        addClosingListener(new ClosingListener());

        // Decorate the frame and make it visible
        setTitle("Buy/Sell");
        setMinimumSize(new Dimension(900, 700));
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

        //Make return button
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeReturnPane());

        //list of buy items
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeBuyPane());

        //buttons
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeBuyButton());

        //list of sell items
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeSellPane());

        //buttons
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeSellButton());

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
     * Makes a JPanel consisting of the list of buy trades and the trade information
     *
     * @return the make buy panel.
     */
    private JPanel makeBuyPane() {
        // Initialise Border
        Border empty = BorderFactory.createEmptyBorder();
        Border border = BorderFactory.createTitledBorder(empty, "Buy:");

        // Initialise the panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));

        // Add the buy list and field to the panel
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.add(buyListPane());
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.add(makeBuyFieldsPanel());
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.setBorder(border);
        return detailsPanel;
    }

    /**
     * Makes a JScrollPane that holds a JList for the list of names in the
     * user table.
     *
     * @return the scrolling name list panel
     */
    private JScrollPane buyListPane() {
        String type = "Sell";

        // Get a list of all buy trades and a corresponding ID list of the trades
        ListModel model = tradeData.getType(type);
        for (int i = 0; i < model.getSize(); i++) {
            Trade trade = tradeData.get(model.getElementAt(i + 1));
            // Check that the trade against the user's organisation
            if (!user.getOrganisationalUnit().equals(trade.getOrganisation())) {
                Date date = trade.getDate();
                tradeBuyList.add(String.format("%s %s %s for $%s - %s", trade.getBuySell(), trade.getQuantity(), trade.getAsset(), trade.getPrice(), date));
                buyId.add(trade);
            }
        }

        // Initialise the JList
        buyList = new JList(tradeBuyList.toArray());
        buyList.setFixedCellWidth(200);

        // Add the JList to the scroller pane
        JScrollPane scroller = new JScrollPane(buyList);
        scroller.setViewportView(buyList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set Dimensions
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));
        return scroller;
    }

    /**
     * Makes a JPanel of the buy button
     *
     * @return the Buy button panel
     */
    private JPanel makeBuyButton() {
        // Initialise the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Initialise the buy button and add it to the panel
        buyButton = new JButton("Buy");
        buttonPanel.add(buyButton);
        return buttonPanel;
    }

    /**
     * Adds the buttons to the panel
     *
     * @return a panel containing the create user button
     */
    private JPanel makeSellButton() {
        // Initialise the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Initialise the sell button and add to the panel
        sellButton = new JButton("Sell");
        buttonPanel.add(sellButton);
        return buttonPanel;
    }

    /**
     * Makes a JPanel containing the information of a selected trade
     *
     * @return a panel containing the user fields
     */
    private JPanel makeBuyFieldsPanel() {
        // Initialise the panel
        JPanel buyPanel = new JPanel();
        GroupLayout layout = new GroupLayout(buyPanel);
        buyPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Initialise labels
        buyName = new JLabel("Asset ");
        buyQuantity = new JLabel("Quantity");
        buyPrice= new JLabel("Price");
        buyOrganisation = new JLabel("Organisation");

        // Initialise text Fields
        buyField = new JTextField(30);
        buyField.setPreferredSize(new Dimension(30, 20));
        buyField.setEditable(false);

        buyQuantityField = new JTextField(30);
        buyQuantityField.setPreferredSize(new Dimension(30, 20));
        buyQuantityField.setEditable(false);

        buyPriceField = new JTextField(30);
        buyPriceField.setPreferredSize(new Dimension(30, 20));
        buyPriceField.setEditable(false);

        buyOrganisationField = new JTextField(30);
        buyOrganisationField.setPreferredSize(new Dimension(30, 20));
        buyOrganisationField.setEditable(false);

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Three parallel groups 1. contains labels and the other the fields\
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyName).addComponent(buyQuantity).addComponent(buyPrice).addComponent(buyOrganisation));
        hGroup.addGroup(layout.createParallelGroup().addComponent(buyField).addComponent(buyQuantityField).addComponent(buyPriceField).addComponent(buyOrganisationField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(buyName).addComponent(buyField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(buyQuantity).addComponent(buyQuantityField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(buyPrice).addComponent(buyPriceField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(buyOrganisation).addComponent(buyOrganisationField));
        layout.setVerticalGroup(vGroup);
        return buyPanel;
    }

    /**
     * Makes a JPanel consisting of the list of sell trades and the trade information
     *
     * @return the make sell panel.
     */
    private JPanel makeSellPane() {
        // Initialise Border
        Border empty = BorderFactory.createEmptyBorder();
        Border border = BorderFactory.createTitledBorder(empty, "Sell:");

        // Initialise the panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));

        // Add the sell list panel and fields panel to the panel
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.add(sellListPane());
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.add(makeSellFieldsPanel());
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.setBorder(border);
        return detailsPanel;
    }

    /**
     * Makes a JScrollPane that holds a JList for the list of names in the
     * user table.
     *
     * @return the scrolling name list panel
     */
    private JScrollPane sellListPane() {
        String type = "Buy";

        // Get a list of all sell trades and a corresponding ID list of the trades
        ListModel model = tradeData.getType(type);
        for (int i = 0; i < model.getSize(); i++) {
            Trade trade = tradeData.get(model.getElementAt(i));
            // Check that the trade against the user's organisation
            if (!user.getOrganisationalUnit().equals(trade.getOrganisation())) {
                Date date = trade.getDate();
                tradeSellList.add(String.format("%s %s %s for %s - %s", trade.getBuySell(), trade.getQuantity(), trade.getAsset(), trade.getPrice(), date));
                sellId.add(trade);
            }
        }

        // Initialise the JList and Scroller pane
        sellList = new JList(tradeSellList.toArray());
        sellList.setFixedCellWidth(200);
        JScrollPane scroller = new JScrollPane(sellList);
        scroller.setViewportView(sellList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set Dimensions
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));
        return scroller;
    }

    /**
     * Makes a JPanel containing the information of a selected trade
     *
     * @return a panel containing the user fields
     */
    private JPanel makeSellFieldsPanel() {
        // Initialise the JPanel
        JPanel buyPanel = new JPanel();
        GroupLayout layout = new GroupLayout(buyPanel);
        buyPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Initialise labels
        sellName = new JLabel("Asset ");
        sellQuantity = new JLabel("Quantity");
        sellPrice= new JLabel("Price");
        sellOrganisation = new JLabel("Organisation");

        // Initialise text Fields
        sellField = new JTextField(30);
        sellField.setPreferredSize(new Dimension(30, 20));
        sellField.setEditable(false);

        sellQuantityField = new JTextField(30);
        sellQuantityField.setPreferredSize(new Dimension(30, 20));
        sellQuantityField.setEditable(false);

        sellPriceField = new JTextField(30);
        sellPriceField.setPreferredSize(new Dimension(30, 20));
        sellPriceField.setEditable(false);

        sellOrganisationField = new JTextField(30);
        sellOrganisationField.setPreferredSize(new Dimension(30, 20));
        sellOrganisationField.setEditable(false);

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Three parallel groups 1. contains labels and the other the fields\
        hGroup.addGroup(layout.createParallelGroup().addComponent(sellName).addComponent(sellQuantity).addComponent(sellPrice).addComponent(sellOrganisation));
        hGroup.addGroup(layout.createParallelGroup().addComponent(sellField).addComponent(sellQuantityField).addComponent(sellPriceField).addComponent(sellOrganisationField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(sellName).addComponent(sellField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(sellQuantity).addComponent(sellQuantityField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(sellPrice).addComponent(sellPriceField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(sellOrganisation).addComponent(sellOrganisationField));
        layout.setVerticalGroup(vGroup);
        return buyPanel;
    }

    /**
     * Display the trade's details in the buy fields
     *
     * @param trade the selected trade to display
     */
    private void displayBuy(Trade trade) {
        // Check that the current trade is not null and set the fields to the selected trade
        if (trade != null) {
            buyField.setText(trade.getAsset());
            buyQuantityField.setText(String.valueOf(trade.getQuantity()));
            buyPriceField.setText(String.valueOf(trade.getPrice()));
            buyOrganisationField.setText(trade.getOrganisation());
        }
    }

    /**
     * Display the trade's details in the buy fields
     *
     * @param trade the selected trade to display
     */
    private void displaySell(Trade trade) {
        // Check that the current trade is not null and set the fields to the selected trade
        if (trade != null) {
            sellField.setText(trade.getAsset());
            sellQuantityField.setText(String.valueOf(trade.getQuantity()));
            sellPriceField.setText(String.valueOf(trade.getPrice()));
            sellOrganisationField.setText(trade.getOrganisation());
        }
    }

    /**
     * Clear all buy input fields
     */
    private void clearBuyFields() {
        // Clear all fields in the buy section
        buyField.setText("");
        buyQuantityField.setText("");
        buyPriceField.setText("");
        buyOrganisationField.setText("");
    }

    /**
     * Clear all sell input fields
     */
    private void clearSellFields() {
        // Clear all fields in the sell section
        sellField.setText("");
        sellQuantityField.setText("");
        sellPriceField.setText("");
        sellOrganisationField.setText("");
    }

    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        sellButton.addActionListener(listener);
        buyButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the lists
     *
     * @param listener the listener for the lists
     */
    private void addNameListListener(ListSelectionListener listener) {
        buyList.addListSelectionListener(listener);
        sellList.addListSelectionListener(listener);
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
     * @author Laku Jackson
     */
    private class ButtonListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // Get which button was pressed and go to the corresponding method
            JButton source = (JButton) e.getSource();
            if (source == sellButton) {
                sellPressed();

                // Persist data, close frame and open the user options
                historyData.persist();
                orgData.persist();
                tradeData.persist();
                dispose();
                new userOptions(user);
            }
            else if (source == buyButton) {
                buyPressed();

                // Persist data, close frame and open the user options
                historyData.persist();
                orgData.persist();
                tradeData.persist();
                dispose();
                new userOptions(user);
            }
            else if (source == backButton)
            {
                // Persist the data, close the frame and return to the user options
                historyData.persist();
                orgData.persist();
                tradeData.persist();
                dispose();
                new userOptions(user);
            }
        }
        /**
         * When the create user button is pressed, add the user information to the database
         * or display error
         */
        private void sellPressed() {
            // Initialise trade information
            int quantity = Integer.parseInt(sellQuantityField.getText());
            int price = Integer.parseInt(sellPriceField.getText());
            String asset = sellField.getText();

            // Get the organisation related to the asset
            for (int i = 0; i < orgList.size(); i++) {
                if (orgList.get(i).getAsset().contains(asset)) { // get asset with same name
                    org = orgList.get(i); // organisation equal the organisation that has that asset
                    break;
                }
            }
            // Check the quantity not higher than available quantity
            if (quantity <= org.getQuantity()) {
                // Remove quantity and credits from the organisation,
                orgData.removeQuantity(org.getName(), asset, quantity);
                orgData.removeCredits(org.getName(), price);

                // Add quantity and credits to the recieving organisation
                Trade trade = sellId.get(sellList.getSelectedIndex());
                orgData.addQuantity(trade.getOrganisation(), asset, quantity);
                orgData.addCredits(trade.getOrganisation(), price);

                // Remove trade from the current trade and add to the trad history
                tradeData.remove(sellId.get(sellList.getSelectedIndex()));
                historyData.add(trade);
                JOptionPane.showMessageDialog(null, String.format("You have successfully sold '%s %s for %s'", quantity, asset, price));
            }
            // Quantity exceeds company quantity
            else {
                JOptionPane.showMessageDialog(new JFrame(), String.format("Your organisation does not have the quantity of asset '%s' available to sell", asset), "Field Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * When the Buy button is pressed, item is sent to the users organisation database
         * or display error
         */
        private void buyPressed() {
            // Initialise the trade information
            int quantity = Integer.parseInt(buyQuantityField.getText());
            int price = Integer.parseInt(buyPriceField.getText());
            String asset = buyField.getText();

            // Get the
            for (int i = 0; i < orgList.size(); i++) {
                if (orgList.get(i).getAsset().contains(asset)) {
                    org = orgList.get(i);
                    break;
                }
            }
            // Check the amount of credits does not exceed the organisations credits
            if (price < org.getCredits()){
                // Add quantity and credits to the trade creater
                Trade trade = tradeData.get(buyId.get(buyList.getSelectedIndex()));
                orgData.addQuantity(trade.getOrganisation(), asset, quantity);
                orgData.addCredits(trade.getOrganisation(), price);

                // Remove quantity and credits from the users organisation,
                orgData.removeQuantity(org.getName(), asset, quantity);
                orgData.removeCredits(org.getName(), price);

                // Remove from current trades and add to the trade history
                tradeData.remove(buyId.get(buyList.getSelectedIndex()));
                historyData.add(trade);
                JOptionPane.showMessageDialog(null, String.format("You have successfully Bought '%s %s for %s'", quantity, asset, price));
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), String.format("Your organisation does not have enough credits to purchase '%s' ", asset), "Field Error", JOptionPane.ERROR_MESSAGE);
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
            // Get the source of the selected list
            JList source = (JList) e.getSource();
            if (source == buyList) {
                // Check to see the selected item is not null or empty
                if (buyList.getSelectedValue() != null
                        && !buyList.getSelectedValue().equals("")) {
                    // Display the trade
                    Trade trade = tradeData.get(buyId.get(buyList.getSelectedIndex()));
                    displayBuy(trade);
                    clearSellFields();
                }
            }
            else if (source == sellList) {
                // Check to see the selected item is not null or empty
                if (sellList.getSelectedValue() != null
                        && !sellList.getSelectedValue().equals("")) {
                    // Display the trade
                    Trade trade = tradeData.get(sellId.get(sellList.getSelectedIndex()));
                    displaySell(trade);
                    clearBuyFields();
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
            // Persist the data and stop the application
            historyData.persist();
            orgData.persist();
            tradeData.persist();
            System.exit(0);
        }
    }
}
