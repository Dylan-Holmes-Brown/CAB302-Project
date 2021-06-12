package views;

import common.Organisation;
import common.Trade;
import common.User;
import common.sql.CurrentData;
import common.sql.OrganisationData;

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
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class buyArena extends JFrame implements Serializable {

    private static final long serialVersionUID = 555;
    private JList buyList;
    private JList sellList;
    private List<String> tradeBuyList;
    private List<Integer> buyId;
    private List<String> tradeSellList;
    private List<Integer> sellId;

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


    private static User user;

    ArrayList<Organisation> organisationList = new ArrayList<Organisation>();
    ArrayList<Organisation> orgList = new ArrayList<Organisation>();

    Object[] array;

    private JButton sellButton;
    private JButton buyButton;

    OrganisationData orgData;
    CurrentData tradeData;



    /**
     * Constructor sets up UI frame and adds listeners
     *
     * @param tradeData the user data accessor to the database
     * @param orgData the organisation data accessor to the database
     */
    public buyArena(User user, CurrentData tradeData, OrganisationData orgData) {
        this.user = user;
        this.tradeData = tradeData;
        this.orgData = orgData;
        array = new String[orgData.getSize()];
        tradeBuyList = new ArrayList<>();
        tradeSellList = new ArrayList<>();
        buyId = new ArrayList<>();
        sellId = new ArrayList<>();

        initUI();

        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());

        // decorate the frame and make it visible
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

    private JPanel makeBuyPane() {
        // Initialise Border
        Border empty = BorderFactory.createEmptyBorder();
        Border border = BorderFactory.createTitledBorder(empty, "Buy:");

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));
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
        String type = "Buy";

        ListModel model = tradeData.getType(type);
        for (int i = 0; i < model.getSize(); i++) {
            Trade trade = tradeData.get(model.getElementAt(i));
            // Check that the trade against the user's organisation
            if (!user.getOrganisationalUnit().equals(trade.getOrganisation())) {
                Date date = trade.getDate();
                tradeBuyList.add(String.format("%s %s %s for %s - %s", trade.getBuySell(), trade.getQuantity(), trade.getAsset(), trade.getPrice(), date));
                buyId.add(trade.getID());
            }
        }

        buyList = new JList(tradeBuyList.toArray());
        buyList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(buyList);
        scroller.setViewportView(buyList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        return scroller;
    }

    private JPanel makeBuyButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
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
        JPanel buyPanel = new JPanel();
        GroupLayout layout = new GroupLayout(buyPanel);
        buyPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Label of fields
        buyName = new JLabel("Asset ");
        buyQuantity = new JLabel("Quantity");
        buyPrice= new JLabel("Price");
        buyOrganisation = new JLabel("Organisation");

        // Text Fields
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


    private JPanel makeSellPane() {
        // Initialise Border
        Border empty = BorderFactory.createEmptyBorder();
        Border border = BorderFactory.createTitledBorder(empty, "Sell:");

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));
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
        String type = "Sell";

        ListModel model = tradeData.getType(type);
        for (int i = 0; i < model.getSize(); i++) {
            Trade trade = tradeData.get(model.getElementAt(i));
            // Check that the trade against the user's organisation
            if (!user.getOrganisationalUnit().equals(trade.getOrganisation())) {
                Date date = trade.getDate();
                tradeSellList.add(String.format("%s %s %s for %s - %s", trade.getBuySell(), trade.getQuantity(), trade.getAsset(), trade.getPrice(), date));
                sellId.add(trade.getID());
            }
        }

        sellList = new JList(tradeSellList.toArray());
        sellList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(sellList);

        scroller.setViewportView(sellList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
        JPanel buyPanel = new JPanel();
        GroupLayout layout = new GroupLayout(buyPanel);
        buyPanel.setLayout(layout);

        // Enable auto gaps between each line
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Label of fields
        sellName = new JLabel("Asset ");
        sellQuantity = new JLabel("Quantity");
        sellPrice= new JLabel("Price");
        sellOrganisation = new JLabel("Organisation");

        // Text Fields
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
        // Check that the current trade is not null
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
        // Check that the current trade is not null
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
        buyField.setText("");
        buyQuantityField.setText("");
        buyPriceField.setText("");
        buyOrganisationField.setText("");
    }

    /**
     * Clear all sell input fields
     */
    private void clearSellFields() {
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
            JButton source = (JButton) e.getSource();
            if (source == sellButton) {
                sellPressed();
            }
            else if (source == buyButton) {
                buyPressed();
            }
        }
        /**
         * When the create user button is pressed, add the user information to the database
         * or display error
         */
        private void sellPressed() {
            Integer index = sellList.getSelectedIndex();
            Organisation org = new Organisation();
            int quantity = Integer.parseInt(sellQuantityField.getText());
            int price = Integer.parseInt(sellPriceField.getText());

            ListModel orgModel = orgData.getModel();
            for (int i = 0; i < orgData.getSize(); i++) {
                Organisation o = orgData.get(orgModel.getElementAt(i));
                organisationList.add(o);
            }
            for (int i = 0; i < organisationList.size(); i++) {
                if (organisationList.get(i).getName().equals(user.getOrganisationalUnit()) &&
                        organisationList.get(i).getAsset().equals(sellField)) {
                    org = orgData.get(i);
                }
            }
            if (quantity <= org.getQuantity()){
                JOptionPane.showMessageDialog(null, "Sell Success");
                //orgData.removeQuantity(organisationList.contains(user.getOrganisationalUnit()), String.valueOf(orgList.contains(index)), quant);
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), "", "Field Error", JOptionPane.ERROR_MESSAGE);
            }

                //organisationList = list of organisations
                //orgList  = list of assets from that organisation



                //compare the org quantity to the quantity of a trade?
                //so the first loop gets all organisations to a list, and the second gets all the assets of a given organisation.
        }

        /**
         * When the Buy button is pressed, item is sent to the users organisation database
         * or display error
         */
        private void buyPressed() {
//            String index = tradeList.getSelectedValue().toString();
//            int quant = Integer.valueOf(qualityField.getText());
//            int price = Integer.valueOf(priceField.getText());
//            //variables
//            if(itemField.getText() != null && !itemField.getText().equals("") &&
//                    qualityField.getText() != null && !qualityField.getText().equals("") &&
//                    priceField.getText() != null && !priceField.getText().equals("")){
//                if (index == itemField.getText() && quant <= user.getOrganisationalUnit(tradeData)){
//
//
//                }
//
//
//            }

//            JOptionPane.showMessageDialog(null, String.format("You have bough the '%s' ", itemName));
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
}
