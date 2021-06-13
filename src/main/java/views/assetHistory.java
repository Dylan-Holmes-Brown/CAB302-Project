package views;

import common.Organisation;
import common.Trade;
import common.User;
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
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class assetHistory extends JFrame implements Serializable {

    private static final long serialVersionUID = 555;
    private JList buyList;
    private List<String> tradeBuyList;
    private List<Integer> tradeId;
    private ArrayList<Organisation> orgList = new ArrayList<Organisation>();

    private JLabel historyName;
    private JTextField historyField;
    private JLabel historyQuantity;
    private JTextField historyQField;
    private JLabel historyPrice;
    private JTextField historyPField;
    private JLabel historyOrganisation;
    private JTextField historyOrgField;
    private JLabel historyType;
    private JTextField historyTField;



    private JButton backButton;
    private static User user;

    private Object[] array;


    private OrganisationData orgData;
    private TradeHistoryData tradeData;
    private Organisation org = new Organisation();



    /**
     * Constructor sets up UI frame and adds listeners
     *
     * @param tradeData the user data accessor to the database
     * @param orgData the organisation data accessor to the database
     */
    public assetHistory(User user, TradeHistoryData tradeData, OrganisationData orgData) {
        this.user = user;
        org = new Organisation();
        this.tradeData = tradeData;
        this.orgData = orgData;
        array = new String[orgData.getSize()];
        tradeBuyList = new ArrayList<>();
        tradeId = new ArrayList<>();

        ListModel model = orgData.getModel();
        for (int i = 0; i < orgData.getSize(); i++) {
            org = orgData.get(model.getElementAt(i));
            if (org.getName().equals(user.getOrganisationalUnit())) {
                orgList.add(org);
            }
        }

        initUI();

        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());
        addClosingListener(new ClosingListener());

        // decorate the frame and make it visible
        setTitle("Asset History");
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

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeReturnPane());

        //list of buy items
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeBuyPane());

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

    private JPanel makeBuyPane() {
        // Initialise Border
        Border empty = BorderFactory.createEmptyBorder();
        Border border = BorderFactory.createTitledBorder(empty, "Past Buys:");

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


//
//        ListModel model = tradeData.getType(type);
//        for (int i = 0; i < model.getSize(); i++) {
//            Trade trade = tradeData.get(model.getElementAt(i));
//            // Check that the trade against the user's organisation
//            if (!user.getOrganisationalUnit().equals(trade.getOrganisation())) {
//                Date date = trade.getDate();
//                tradeBuyList.add(String.format("%s %s %s for $%s - %s", trade.getBuySell(), trade.getQuantity(), trade.getAsset(), trade.getPrice(), date));
//                tradeId.add(trade.getID());
//            }
//        }

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
        historyType = new JLabel("Type");
        historyName = new JLabel("Asset ");
        historyQuantity = new JLabel("Quantity");
        historyPrice = new JLabel("Price");
        historyOrganisation = new JLabel("Organisation");

        // Text Fields
        historyTField = new JTextField(30);
        historyTField.setPreferredSize(new Dimension(30, 20));
        historyTField.setEditable(false);

        historyField = new JTextField(30);
        historyField.setPreferredSize(new Dimension(30, 20));
        historyField.setEditable(false);

        historyQField = new JTextField(30);
        historyQField.setPreferredSize(new Dimension(30, 20));
        historyQField.setEditable(false);

        historyPField = new JTextField(30);
        historyPField.setPreferredSize(new Dimension(30, 20));
        historyPField.setEditable(false);

        historyOrgField = new JTextField(30);
        historyOrgField.setPreferredSize(new Dimension(30, 20));
        historyOrgField.setEditable(false);

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Three parallel groups 1. contains labels and the other the fields\
        hGroup.addGroup(layout.createParallelGroup().addComponent(historyType).addComponent(historyName).addComponent(historyQuantity).addComponent(historyPrice).addComponent(historyOrganisation));
        hGroup.addGroup(layout.createParallelGroup().addComponent(historyTField).addComponent(historyField).addComponent(historyQField).addComponent(historyPField).addComponent(historyOrgField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(historyType).addComponent(historyTField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(historyName).addComponent(historyField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(historyQuantity).addComponent(historyQField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(historyPrice).addComponent(historyPField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(historyOrganisation).addComponent(historyOrgField));
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
            historyTField.setText(trade.getBuySell());
            historyField.setText(trade.getAsset());
            historyQField.setText(String.valueOf(trade.getQuantity()));
            historyPField.setText(String.valueOf(trade.getPrice()));
            historyOrgField.setText(trade.getOrganisation());
        }
    }


    /**
     * Clear all buy input fields
     */
    private void clearBuyFields() {
        historyTField.setText("");
        historyField.setText("");
        historyQField.setText("");
        historyPField.setText("");
        historyOrgField.setText("");
    }


    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the lists
     *
     * @param listener the listener for the lists
     */
    private void addNameListListener(ListSelectionListener listener) {
        buyList.addListSelectionListener(listener);
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
            JButton source = (JButton) e.getSource();
            if (source == backButton) {
                // persist the data dispose the window and return to the user options menu
                tradeData.persist();
                orgData.persist();
                dispose();
                new userOptions(user);
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
            JList source = (JList) e.getSource();
            if (source == buyList) {
                // Check to see the selected item is not null or empty
                if (buyList.getSelectedValue() != null
                        && !buyList.getSelectedValue().equals("")) {
                    // Display the trade
                    //Trade trade = tradeData.get(tradeId.get(buyList.getSelectedIndex()));
                    //displayBuy(trade);
                }
            }
            else {

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
            // Stop the application
            System.exit(0);
        }
    }
}
