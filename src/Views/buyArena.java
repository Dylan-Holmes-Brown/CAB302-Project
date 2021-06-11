package Views;

import client.NetworkDataSource;
import common.User;
import common.sql.CurrentData;
import common.sql.OrganisationData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * @author Vipin Vijay
 * @author Dylan Holmes-Brown
 * @author Laku Jackson
 */

public class buyArena extends JFrame implements Serializable {

    private static final long serialVersionUID = 62L;
    private JList tradeList;
    private JComboBox comboBox;

    private JLabel itemName;
    private JTextField itemField;
    private JLabel itemquality;
    private JTextField qualityField;
    private JLabel itemPrice;
    private JTextField priceField;

    private JLabel orgLabel;
    private static User user;

    Object[] array;

    private JButton sellButton;
    private JButton deleteButton;
    private JButton buyButton;

    //UserData userData;
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

        initUI();

        addButtonListeners(new ButtonListener());

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
        contentPane.add(buyListPane());

        //list of sell items
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(sellListPane());

        //user inputs
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeUserFieldPanel());

        //buttons
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());

        contentPane.add(Box.createVerticalStrut(20));
    }

    /**
     * Makes a JScrollPane that holds a JList for the list of names in the
     * user table.
     *
     * @return the scrolling name list panel
     */
    private JScrollPane buyListPane() {
        String type = "Buy";
        tradeList = new JList(tradeData.getType(type));
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

    /**
     * Makes a JScrollPane that holds a JList for the list of names in the
     * user table.
     *
     * @return the scrolling name list panel
     */
    private JScrollPane sellListPane() {
        String type = "Sell";
        tradeList = new JList(tradeData.getType(type));
        tradeList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(tradeList);

        scroller.setViewportView(tradeList);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMaximumSize(new Dimension(250, 250));

        return scroller;
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
        itemName = new JLabel("Item Name ");
        itemquality = new JLabel("Quantity");
        itemPrice= new JLabel("Price");

        // Text Fields
        itemField = new JTextField(30);
        itemField.setPreferredSize(new Dimension(30, 20));

        qualityField = new JTextField(30);
        qualityField.setPreferredSize(new Dimension(30, 20));

        priceField = new JTextField(30);
        priceField.setPreferredSize(new Dimension(30, 20));

        // Create a sequential group for the horizontal axis
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // Three parallel groups 1. contains labels and the other the fields\
        hGroup.addGroup(layout.createParallelGroup().addComponent(itemName).addComponent(itemquality).addComponent(itemPrice));
        hGroup.addGroup(layout.createParallelGroup().addComponent(itemField).addComponent(qualityField).addComponent(priceField));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemName).addComponent(itemField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemquality).addComponent(qualityField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemPrice).addComponent(priceField));
        layout.setVerticalGroup(vGroup);
        return userPanel;
    }

    /**
     * Adds the buttons to the panel
     *
     * @return a panel containing the create user button
     */
    private JPanel makeButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        sellButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        buyButton = new JButton("buy");
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(sellButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(buyButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Clear all input fields and radio buttons
     */
    private void clearFields() {
        itemField.setText("");
        qualityField.setText("");

    }

    /**
     * Checks the size of the user table determining the state of the delete button
     */
    private void checkListSize() {
        deleteButton.setEnabled(tradeData.getSize() != 0);
    }

    /**
     * Adds a listener to the buttons
     *
     * @param listener the listener for the buttons to use
     */
    private void addButtonListeners(ActionListener listener) {
        sellButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        buyButton.addActionListener(listener);
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
            else if (source == deleteButton) {
                deletePressed();
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
            String index = tradeList.getSelectedValue().toString();
            int quant = Integer.valueOf(qualityField.getText());
            int price = Integer.valueOf(priceField.getText());
            //variables
            if(itemField.getText() != null && !itemField.getText().equals("") &&
                    qualityField.getText() != null && !qualityField.getText().equals("") &&
                    priceField.getText() != null && !priceField.getText().equals("")){
                if (index == itemField.getText()){
                    if ()


                }

                //compare the org quantity to the quantity of a trade?


            }

        }


        /**
         * When the delete button is pressed, delete the trade information from the database
         * or display error
         */
        private void deletePressed() {
            int index = tradeList.getSelectedIndex();
            String itemName = itemField.getText();
            tradeData.remove(tradeList.getSelectedValue());
            clearFields();
            index--;
            if (index == -1) {
                if (tradeData.getSize() != 0) {
                    index = 0;
                }
            }
            tradeList.setSelectedIndex(index);
            checkListSize();
            JOptionPane.showMessageDialog(null, String.format("User '%s' successfully deleted", itemName));
        }

        /**
         * When the Buy button is pressed, item is sent to the users oragnisation database
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


            checkListSize();
            JOptionPane.showMessageDialog(null, String.format("You have bough the '%s' ", itemName));
        }
    }


    public static void main(String[] args) {

        new buyArena(user, new CurrentData(new NetworkDataSource()), new OrganisationData(new NetworkDataSource()));
    }

}
