package Views;

import javax.swing.*;
import java.io.Serializable;
import client.NetworkDataSource;
import common.Trade;
import common.User;
import common.sql.AssetTypeData;
import common.sql.CurrentData;
import common.sql.UserData;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class addingtrade extends JFrame implements Serializable {

    private static final long serialVersionUID = 62L;
    private JList tradeList;
    private JComboBox dropDownBox;

    private JLabel traPrice;
    private JTextField traPriceField;
    private JLabel assetQuantity;
    private JTextField assetQField;

    private JLabel assetLabel;

    Object[] array;

    private JRadioButton buyButton;
    private JRadioButton sellButton;
    private ButtonGroup radioGroup;

    private JButton createButton;
    private JButton deleteButton;

    //UserData data;

    CurrentData trades;
    User uObj;

    /**
     * Constructor sets up UI, adds button listeners and displays
     *
     * @param uObj the user data from the database
     */
    public addingtrade(User uObj, CurrentData trades) {
        this.trades = trades;
        this.uObj = uObj;
        array = new String[trades.getSize()];

        initUI();
        checkListSize();

        // Add listeners to components
        addRadioListeners(new Views.addingtrade.RadioListener());
        addButtonListeners(new Views.addingtrade.ButtonListener());
        addNameListListener(new Views.addingtrade.NameListListener());
        addClosingListener(new Views.addingtrade.ClosingListener());


        // decorate the frame and make it visible
        setTitle("Create Trade");
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

        //List of Organisation trades
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeNameListPane());

        //Input area
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserFieldPanel());

        //dropdown for assets
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
        tradeList = new JList(trades.getModel());
        tradeList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(tradeList);
        scroller.setViewportView(tradeList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        return scroller;
    }

    private JPanel makeDropDownPanel() {
        ListModel model = trades.getModel();
        for (int i = 0; i < trades.getSize(); i++) {
            array[i] = model.getElementAt(i).toString();

        }
        dropDownBox = new JComboBox(array);
        dropDownBox.setBackground(Color.white);
        JPanel panel = new JPanel();
        assetLabel = new JLabel("Asset");
        panel.add(assetLabel);
        panel.add(dropDownBox);

        return panel;
    }


    /**
     * Makes a JPanel containing the Organization name, credits, asset and quantity fields to be
     * recorded.
     *
     * @return a panel containing the user fields
     */
    private JPanel makeUserFieldPanel() {
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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        // Label Buttons
        buyButton = new JRadioButton("Buy");
        sellButton = new JRadioButton("Sell");
        radioGroup = new ButtonGroup();

        // Create and space Buttons
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
        traPriceField.setText("");
        assetQField.setText("");
        radioGroup.clearSelection();
    }

    /**
     * Checks the size of the organisation table determining the state of the delete button
     */
    private void checkListSize() {
        deleteButton.setEnabled(trades.getSize() != 0);
    }

    /**
     * Display the organisations details in the fields
     * int i = Integer.parseInt(s.trim());
     * @param tra
     */
    private void display(Trade tra) {
        if (tra != null) {
            traPriceField.setText(String.valueOf(tra.getPrice()));
            assetQField.setText(String.valueOf(tra.getQuantity()));
            if (tra.getBuySell().contains("Buy")) {
                buyButton.setSelected(true);
            }
            else {
                sellButton.setSelected(true);
            }
        }
        dropDownBox.setSelectedItem(tra.getAsset());
    }

    private void addRadioListeners(ActionListener listener) {
        buyButton.addActionListener(listener);
        sellButton.addActionListener(listener);
    }

    private void addButtonListeners(ActionListener listener) {
        createButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
    }

    private void addNameListListener(ListSelectionListener listener) {
        tradeList.addListSelectionListener(listener);
    }

    private void addClosingListener(WindowListener listener) {
        addWindowListener(listener);
    }

    public void main(String[] args) {
        new addingtrade(uObj, new CurrentData(new NetworkDataSource()));
    }


    private class RadioListener implements ActionListener {
        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source == buyButton) {
                dropDownBox.setEnabled(true);
            }
            else if (source == sellButton) {
                dropDownBox.setEnabled(false);
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
            Trade t = new Trade();
            String selectedValue = dropDownBox.getSelectedItem().toString();

            // If all fields are filled in continue
            if (traPriceField.getText() != null && !traPriceField.getText().equals("")
                    && !traPriceField.equals("") && !assetQField.equals("")&& (buyButton.isSelected() || sellButton.isSelected())) {

                if (buyButton.isSelected()) {
                    t = new Trade();
                }
                else if (sellButton.isSelected()) {
                    t = new Trade();
                }


                // Add user to database and clear fields
                trades.add(t);
                clearFields();
                JOptionPane.showMessageDialog(null, String.format("Trade '%s' successfully added", t.getAsset()));
            }

            // Not all fields are filled in
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Please Complete All Fields!", "Field Error", JOptionPane.ERROR_MESSAGE);
            }
            checkListSize();
        }

        private void deletePressed() {
            int index = tradeList.getSelectedIndex();
            String traFieldText = traPriceField.getText();
            trades.remove(tradeList.getSelectedValue());
            clearFields();
            index--;
            if (index == -1) {
                if (trades.getSize() != 0) {
                    index = 0;
                }
            }
            tradeList.setSelectedIndex(index);
            checkListSize();
            JOptionPane.showMessageDialog(null, String.format("Trade '%s' successfully deleted", traFieldText));
        }
    }

    private class NameListListener implements ListSelectionListener {
        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            if (tradeList.getSelectedValue() != null
                    && !tradeList.getSelectedValue().equals("")) {
                //display(trades..get(tradeList.getSelectedValue()));
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
            trades.persist();
            System.exit(0);
        }
    }

}
