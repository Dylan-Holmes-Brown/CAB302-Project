package views;

import common.Trade;
import common.User;
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
 * Initialises the user interface for view trade history to the database.
 * Listeners are included as sub-classes of this class
 *
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class assetHistory extends JFrame implements Serializable {

    // Global Variables
    private static final long serialVersionUID = 555;
    private static User user;
    private TradeHistoryData pastData;

    // JSwing Variables
    private JList pastList;
    private List<String> tradeList;
    private List<Integer> tradeId;

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

    /**
     * Constructor sets up UI frame and adds listeners
     *
     * @param tradeData the trade data accessor to the database
     * @param user The signed in user
     */
    public assetHistory(User user, TradeHistoryData tradeData) {
        // Initialise data
        this.user = user;
        this.pastData = tradeData;
        tradeList = new ArrayList<>();
        tradeId = new ArrayList<>();

        // Initialise the ui and listeners
        initUI();
        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());
        addClosingListener(new ClosingListener());

        // Decorate the frame and make it visible
        setTitle("Asset Trading System - Asset History");
        setMinimumSize(new Dimension(400, 400));
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

        // Display the return button
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeReturnPane());

        //list of buy items
        contentPane.add(makeHistoryPane());

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

    private JPanel makeHistoryPane() {
        // Initialise Border
        Border empty = BorderFactory.createEmptyBorder();
        Border border = BorderFactory.createTitledBorder(empty, "Past Trades:");

        // Initialise the JPanel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));

        // Add trade list and fields to the panel
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
        // Get all the trades
        ListModel model = pastData.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            Trade trade = pastData.get(model.getElementAt(i));
            // Check that the trade against the user's organisation
            Date date = trade.getDate();
            tradeList.add(String.format("%s %s %s for $%s - %s", trade.getBuySell(), trade.getQuantity(), trade.getAsset(), trade.getPrice(), date));
            tradeId.add(trade.getID());
        }

        // Initialise the JList and scroller
        pastList = new JList(tradeList.toArray());
        pastList.setFixedCellWidth(200);
        JScrollPane scroller = new JScrollPane(pastList);
        scroller.setViewportView(pastList);
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
    private JPanel makeBuyFieldsPanel() {
        // Initialise the panel
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
    private void display(Trade trade) {
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
        pastList.addListSelectionListener(listener);
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
                pastData.persist();
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
            // Check to see the selected item is not null or empty
            if (pastList.getSelectedValue() != null
                    && !pastList.getSelectedValue().equals("")) {
                // Display the trade
                Trade trade = pastData.get(tradeId.get(pastList.getSelectedIndex()));
                display(trade);
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
            // Persist the data, stop the application
            pastData.persist();
            System.exit(0);
        }
    }
}
